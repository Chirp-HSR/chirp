package chirp.cli;

import java.io.IOException;

import chirp.frontend.FrontendResource;

public class ChirpFrontendService {

	public static void main(String[] args) throws IOException {
		ServiceUtils.executeServer(FrontendConfig.HOST_URI,
				new FrontendResource(FrontendConfig.REDIS_REPO, FrontendConfig.RENDERER));
	}
}
