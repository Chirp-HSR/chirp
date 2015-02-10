package chirp.cli.distributed;

import java.io.IOException;
import java.util.Arrays;

import chirp.api.TweetRepository;
import chirp.backend.FollowersResource;
import chirp.backend.RedisTweetRepository;
import chirp.backend.TweetsResource;
import chirp.cli.utils.ServiceUtils;

public class ChirpBackendService {

	public static void main(String[] args) throws IOException {
		TweetRepository repo = new RedisTweetRepository(
				BackendConfig.REDIS_HOST);

		ServiceUtils.executeServer(BackendConfig.HOST_URI, Arrays.<Object> asList(
				new TweetsResource(repo), new FollowersResource(repo)));
	}
}
