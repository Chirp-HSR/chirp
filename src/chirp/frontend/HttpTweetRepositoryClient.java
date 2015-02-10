package chirp.frontend;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.MDC;

import chirp.api.Timeline;
import chirp.api.Tweet;
import chirp.api.TweetRepository;

/**
 * Provides the TweetRepository interface by mapping calls to the according HTTP
 * requests to the backend service.
 */
public class HttpTweetRepositoryClient implements TweetRepository {

	private final Client client;
	private final WebTarget tweets;
	private final WebTarget followers;
	private final ObjectMapper mapper;

	public HttpTweetRepositoryClient(String repoUrl) {
		client = ClientBuilder.newClient();
		WebTarget repo = client.target(repoUrl);
		tweets = repo.path("tweets");
		followers = repo.path("followers");
		mapper = new ObjectMapper();
	}

	@Override
	public void propagateTweet(final Tweet tweet) {
		try {
			String v = mapper.writeValueAsString(tweet);
			Entity<String> entity = Entity.json(v);
			
			tweets.request(MediaType.APPLICATION_JSON)
					.header("X-Request-ID", MDC.get("requestId")).post(entity);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Timeline getTimeline(long userId) {
		Response resp = tweets.queryParam("userId", userId)
				.request(MediaType.APPLICATION_JSON)
				.header("X-Request-ID", MDC.get("requestId")).get();
		
		try {
			return mapper.readValue(resp.readEntity(String.class),
					Timeline.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}

	@Override
	public List<Long> getFollowers(long userId) {
		Response resp = followers.queryParam("userId", userId)
				.request(MediaType.APPLICATION_JSON)
				.header("X-Request-ID", MDC.get("requestId")).get();
		
		try {
			return mapper.readValue(resp.readEntity(String.class),
					new TypeReference<List<Long>>(){});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
}
