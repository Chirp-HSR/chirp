package chirp.cli.nonDistributed;

import java.io.IOException;

import chirp.cli.utils.ServiceUtils;
import chirp.frontend.FrontendResource;

public class ChirpService {
	public static void main(String[] args) throws IOException {
		ServiceUtils.executeServer(Config.HOST_URI,
				new FrontendResource(Config.REPO, Config.RENDERER));
	}
}
