package chirp.cli;

import java.io.IOException;

import chirp.api.TweetRepository;
import chirp.backend.FollowersResource;
import chirp.backend.TweetsResource;

public class ChirpBackendService {

	public static void main(String[] args) throws IOException {
		final TweetRepository repo = BackendConfig.REDIS_REPO;
		
		ServiceUtils.executeServer(BackendConfig.HOST_URI,
				new TweetsResource(repo),
				new FollowersResource(repo));
	}
}
