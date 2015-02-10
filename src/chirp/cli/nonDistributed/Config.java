package chirp.cli.nonDistributed;

import chirp.frontend.rendering.TimelineRenderer;

public class Config {
	/**
	 * The URI the frontend service is exposed at.
	 */
	public static final String HOST_URI = "http://localhost:9000/";
	
	/**
	 * The hostname of the Redis server.
	 */
	public static final String REDIS_HOST = "localhost";
	
	/**
	 * The class used for rendering timelines. Use one of the following:
	 * 
	 * - `new TimelineRenderer()` for non-cached rendering
	 * - `new CachedTimelineRenderer()` for in-memory caching
	 * - `new RedisCachedTimelineRenderer(CACHE_HOSTNAME, CACHE_PORT)` for distributed caching on a Redis cache
	 */
	public static final TimelineRenderer RENDERER = new TimelineRenderer();
}
