package chirp.backend;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chirp.api.Serialization;
import chirp.api.Timeline;
import chirp.api.Tweet;
import chirp.api.TweetRepository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * Provides a concrete instance of TweetRepository using Redis as persistent
 * storage.
 */
public class RedisTweetRepository implements TweetRepository, RedisTweetRepositoryMBean {
	private JedisPool pool;
	
	private String redisHost;

	private final Serialization serialization = new Serialization();

	private final static Logger LOGGER = LoggerFactory.getLogger(RedisTweetRepository.class);

	public RedisTweetRepository(String redisHost) {
		this.redisHost = redisHost;
		this.pool = new JedisPool(redisHost);
	}

	@Override
	public void propagateTweet(final Tweet tweet) {
		withResource(jedis -> {
			final List<Integer> followers = getFollowers(tweet.getOriginatorId());
			LOGGER.debug("Propagate tweet {} to followers of user {} (total {} followers)", tweet.hashCode(),
					tweet.getOriginatorId(), followers.size());
			
			final String json = serialization.serialize(tweet);
			
			followers.forEach(followerId -> {
				final String timelineKey = timelineKey(followerId);
				LOGGER.trace("Push tweet {} to timeline {}", json, timelineKey);
				jedis.lpush(timelineKey, json);
				jedis.ltrim(timelineKey, 0, TIMELINE_SIZE - 1);
			});
			
			LOGGER.debug("{} tweets written", followers.size());
			return 0;
		});
	}

	@Override
	public Timeline getTimeline(int userId) {
		return withResource(jedis -> {
			LOGGER.debug("Fecth timeline of user {}", userId);
			final List<String> tweetData = jedis.lrange(timelineKey(userId), 0, TIMELINE_SIZE - 1);
			LOGGER.debug("Received {} tweets", tweetData.size());

			final List<Tweet> tweets = tweetData.stream()
					.map(serialization.deserializer(Tweet.class))
					.collect(Collectors.toList());
			
			return new Timeline(userId, tweets);
		});
	}

	public static String timelineKey(long userId) {
		return String.format("timeline:%d", userId);
	}

	private <T> T withResource(Function<Jedis, T> fn) {
		try {
			try(final Jedis jedis = pool.getResource()) {
				return fn.apply(jedis);
			}
		} catch (JedisConnectionException e) {
			LOGGER.error("Unable to connect to Redis at {}", redisHost, e);
			throw new WebApplicationException(e, Status.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public String getRedisHost() {
		LOGGER.debug("Get redis host via MBean API");
		return redisHost;
	}

	@Override
	public void setRedisHost(String host) {
		LOGGER.info("Change redis host from {} to {}", redisHost, host);
		this.redisHost = host;
		this.pool = new JedisPool(host);
	}
}
