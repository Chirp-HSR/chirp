package chirp.cli.distributed;

import java.io.IOException;
import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import chirp.api.TweetRepository;
import chirp.backend.FollowersResource;
import chirp.backend.RedisTweetRepository;
import chirp.backend.TweetsResource;
import chirp.cli.utils.ServiceUtils;

public class ChirpBackendService {

	public static void main(String[] args) throws IOException {
		TweetRepository repo = new RedisTweetRepository(
				BackendConfig.REDIS_HOST, BackendConfig.REDIS_PORT);

		try {
			MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
			ObjectName name = new ObjectName(
					"chirp.backend:type=RedisTweetRepository");
			mbs.registerMBean(repo, name);
		} catch (Exception e) {
		}

		ServiceUtils.executeServer(BackendConfig.HOST_URI, new TweetsResource(
				repo), new FollowersResource(repo));
	}
}
