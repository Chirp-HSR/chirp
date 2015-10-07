package chirp.cli;

import chirp.api.TweetRepository;
import chirp.backend.InMemoryTweetRepository;

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
	 * The implementation of the tweets repository. Use the following:
	 * - new InMemoryTweetRepository()
	 * - new RedisTweetRepository(REDIS_URI)
	 */
	public static final TweetRepository REPO = new InMemoryTweetRepository();
}
