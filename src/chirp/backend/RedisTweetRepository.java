package chirp.backend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import chirp.api.Timeline;
import chirp.api.Tweet;
import chirp.api.TweetRepository;

/**
 * Provides a concrete instance of TweetRepository using Redis as persistent
 * storage.
 */
public class RedisTweetRepository implements TweetRepository {
	private final JedisPool pool;
	private final ObjectMapper mapper;

	private final static Logger LOGGER = LoggerFactory
			.getLogger(RedisTweetRepository.class);

	public RedisTweetRepository(String redisHost, int redisPort) {
		this.pool = new JedisPool(redisHost, redisPort);
		this.mapper = new ObjectMapper();
	}

	@Override
	public void propagateTweet(final Tweet tweet) {
		Jedis jedis = pool.getResource();
		List<Long> followers = getFollowers(tweet.getOriginatorId());
		LOGGER.debug(
				"Propagate tweet {} to followers of user {} (total {} followers)",
				tweet.hashCode(), tweet.getOriginatorId(), followers.size());
		for (Long followerId : followers) {
			String timelineKey = timelineKey(followerId);
			try {
				String json = mapper.writeValueAsString(tweet);
				LOGGER.trace("Push tweet {} to timeline {}", json, timelineKey);
				jedis.lpush(timelineKey, json);
				jedis.ltrim(timelineKey, 0, 99);
			} catch (IOException e) {
				LOGGER.error("Could not serialize tweet {}", tweet, e);
			}
		}
		LOGGER.debug("{} tweets written", followers.size());
		pool.returnResource(jedis);
	}

	@Override
	public Timeline getTimeline(long userId) {
		Jedis jedis = pool.getResource();
		LOGGER.debug("Fecth timeline of user {}", userId);
		List<String> tweetData = jedis.lrange(timelineKey(userId), 0, 99);
		LOGGER.debug("Received {} tweets", tweetData.size());
		pool.returnResource(jedis);

		List<Tweet> tweets = new ArrayList<Tweet>();
		for (String tweetJson : tweetData) {
			try {
				tweets.add(mapper.readValue(tweetJson, Tweet.class));
			} catch (IOException e) {
				LOGGER.error("Could not deserialize tweet {}", tweetJson, e);
			}
		}
		return new Timeline(userId, tweets);
	}

	@Override
	public List<Long> getFollowers(long userId) {
		LOGGER.debug("Generate followers of user {}", userId);
		// simulate workload for db access
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
		}

		List<Long> followers = new ArrayList<Long>();
		for (long i = 1; i <= Math.min(userId, 1000); i++) {
			followers.add(i);
		}
		LOGGER.debug("User {} has {} followers", userId, followers.size());
		return followers;
	}

	public static String timelineKey(long userId) {
		return String.format("timeline:%d", userId);
	}
}
