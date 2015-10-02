package chirp.frontend.rendering;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import chirp.api.Tweet;
import chirp.frontend.rendering.TimelineRenderer;

public class RedisCachedTimelineRenderer extends TimelineRenderer {

	private final JedisPool pool;

	public RedisCachedTimelineRenderer(String redisCacheHost, int redisCachePort) {
		pool = new JedisPool(redisCacheHost, redisCachePort);
	}

	@Override
	public String renderTweet(Tweet tweet) {
		try(final Jedis jedis = pool.getResource()) {
			final String tweetKey = String.format("tweet:%d", tweet.hashCode());
			String renderedTweet = jedis.get(tweetKey);
	
			if (renderedTweet == null) {
				renderedTweet = super.renderTweet(tweet);
				jedis.set(tweetKey, renderedTweet);
			}
	
			return renderedTweet;
		}
	}

}
