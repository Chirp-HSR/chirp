package chirp.cli.distributed;

import chirp.api.TweetRepository;
import chirp.backend.InMemoryTweetRepository;

public class BackendConfig {
	/**
	 * The URI the backend service is exposed at.
	 */
	public static final String HOST_URI = "http://localhost:9090/";
	
	/**
	 * The implementation of the tweets repository. Use the following:
	 * - new InMemoryTweetRepository()
	 * - new RedisTweetRepository(REDIS_HOST)
	 */
	public static final TweetRepository REPO = new InMemoryTweetRepository();
}
