package chirp.cli;

import chirp.api.TweetRepository;
import chirp.backend.InMemoryTweetRepository;
import chirp.frontend.rendering.TimelineRenderer;

public class FrontendConfig {
	/**
	 * The URI the frontend service is exposed at.
	 */
	public static final String HOST_URI = "http://localhost:9000/";
	
	/**
	 * The URI of the backend service.
	 */
	public static final String BACKEND_URI = "http://localhost:9090/";
	
	/**
	 * URI of the Redis instance that stores the tweets.
	 * (Only required if REPO is a RedisTweetRepository)
	 */
	public static final String REDIS_URI = "http://localhost:6379";
	
	/**
	 * URI of the Redis instance that caches rendered timelines.
	 * (Only required if RENDERER is a RedisCachedTimelineRenderer)
	 */
	public static final String REDIS_CACHE_URI = "http://localhost:6380";
	
	/**
	 * The implementation of the tweets repository. Use the following:
	 * - `new InMemoryTweetRepository()` tweets are stored in-memory
	 * - `new RedisTweetRepository(REDIS_URI)` tweets are stored on a Redis instance
	 * - `new HttpTweetRepositoryClient(BACKEND_URI)` persistency is delegated to the backend service 
	 */
	public static final TweetRepository REPO = new InMemoryTweetRepository();
	
	/**
	 * The class used for rendering timelines. Use one of the following:
	 * 
	 * - `new TimelineRenderer()` for non-cached rendering
	 * - `new CachedTimelineRenderer()` for in-memory caching
	 * - `new RedisCachedTimelineRenderer(REDIS_CACHE_URI)` for distributed caching on a Redis cache
	 */
	public static final TimelineRenderer RENDERER = new TimelineRenderer();
}
