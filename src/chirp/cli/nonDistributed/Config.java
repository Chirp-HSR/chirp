package chirp.cli.nonDistributed;

import chirp.api.TweetRepository;
import chirp.backend.InMemoryTweetRepository;
import chirp.frontend.rendering.TimelineRenderer;

public class Config {
	/**
	 * The URI the frontend service is exposed at.
	 */
	public static final String HOST_URI = "http://localhost:9000/";
	
	/**
	 * The class used for rendering timelines. Use one of the following:
	 * 
	 * - `new TimelineRenderer()` for non-cached rendering
	 * - `new CachedTimelineRenderer()` for in-memory caching
	 * - `new RedisCachedTimelineRenderer(CACHE_HOSTNAME, CACHE_PORT)` for distributed caching on a Redis cache
	 */
	public static final TimelineRenderer RENDERER = new TimelineRenderer();
	
	/**
	 * The implementation of the tweets repository. Use the following:
	 * - new InMemoryTweetRepository()
	 * - new RedisTweetRepository(REDIS_HOST)
	 */
	public static final TweetRepository REPO = new InMemoryTweetRepository();
}
