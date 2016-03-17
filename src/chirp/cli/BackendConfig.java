package chirp.cli;

import chirp.api.TweetRepository;
import chirp.backend.InMemoryTweetRepository;
import chirp.backend.RedisTweetRepository;

public class BackendConfig {
	/**
	 * The URI the backend service is exposed at.
	 */
	public static final String HOST_URI = "http://localhost:9090/";
	
	/**
	 * URI of the Redis instance that stores the tweets.
	 * (Only required if REPO is a RedisTweetRepository)
	 */
	public static final String REDIS_URI = "http://localhost:6379";
	
	/**
	 * In-memory implementation of the tweet repository.
	 */
	public static final TweetRepository IN_MEMORY_REPO = new InMemoryTweetRepository();

	/**
	 * Tweets are stored on a Redis instance.
	 */
	public static final TweetRepository REDIS_REPO = new RedisTweetRepository(REDIS_URI);
}
