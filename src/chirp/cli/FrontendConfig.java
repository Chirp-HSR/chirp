package chirp.cli;

import chirp.api.TweetRepository;
import chirp.backend.InMemoryTweetRepository;
import chirp.backend.RedisTweetRepository;
import chirp.frontend.HttpTweetRepositoryClient;
import chirp.frontend.rendering.CachedTimelineRenderer;
import chirp.frontend.rendering.RedisCachedTimelineRenderer;
import chirp.frontend.rendering.TimelineRenderer;

public class FrontendConfig {
	/**
	 * The URI the frontend service is exposed at.
	 */
	public static final String HOST_URI = "http://0.0.0.0:9000/";
	
	/**
	 * The URI of the backend service.
	 */
	public static final String BACKEND_URI = BackendConfig.HOST_URI;
	
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
	 * In-memory implementation of the tweet repository.
	 */
	public static final TweetRepository IN_MEMORY_REPO = new InMemoryTweetRepository();

	/**
	 * Tweets are stored on a Redis instance.
	 */
	public static final TweetRepository REDIS_REPO = new RedisTweetRepository(REDIS_URI);
	
	/**
	 * Persistency is delegated to the backend service.
	 */
	public static final TweetRepository BACKEND_REPO = new HttpTweetRepositoryClient(BACKEND_URI);
	
	/**
	 * Default class for rendering timelines.
	 */
	public static final TimelineRenderer RENDERER = new TimelineRenderer();
	
	/**
	 * Caches rendered tweets in-memory.
	 */
	public static final TimelineRenderer CACHED_RENDERER = new CachedTimelineRenderer();
	
	/**
	 * Uses Redis to cache rendered tweets.
	 */
	public static final TimelineRenderer REDIS_CACHED_RENDERER = new RedisCachedTimelineRenderer(REDIS_CACHE_URI);
}
