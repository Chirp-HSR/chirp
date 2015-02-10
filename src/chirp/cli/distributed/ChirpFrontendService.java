package chirp.cli.distributed;

import java.io.IOException;
import java.util.Arrays;

import chirp.api.TweetRepository;
import chirp.cli.utils.ServiceUtils;
import chirp.frontend.FrontendResource;
import chirp.frontend.HttpTweetRepositoryClient;

public class ChirpFrontendService {

	public static void main(String[] args) throws IOException {
		TweetRepository repo = new HttpTweetRepositoryClient(FrontendConfig.BACKEND_URI);

		ServiceUtils.executeServer(FrontendConfig.HOST_URI, Arrays.<Object> asList(
				new FrontendResource(repo, FrontendConfig.RENDERER)));
	}

}
