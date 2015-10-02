package chirp.cli.distributed;

import java.io.IOException;

import chirp.backend.FollowersResource;
import chirp.backend.TweetsResource;
import chirp.cli.utils.ServiceUtils;

public class ChirpBackendService {

	public static void main(String[] args) throws IOException {
		ServiceUtils.executeServer(BackendConfig.HOST_URI,
				new TweetsResource(BackendConfig.REPO),
				new FollowersResource(BackendConfig.REPO));
	}
}
