package chirp.cli.nonDistributed;

import java.io.IOException;

import chirp.api.TweetRepository;
import chirp.backend.RedisTweetRepository;
import chirp.cli.utils.ServiceUtils;
import chirp.frontend.FrontendResource;

public class ChirpService {
	public static void main(String[] args) throws IOException {
		TweetRepository repo = new RedisTweetRepository(Config.REDIS_HOST);

		ServiceUtils.executeServer(Config.HOST_URI,
				new FrontendResource(repo, Config.RENDERER));
	}
}
