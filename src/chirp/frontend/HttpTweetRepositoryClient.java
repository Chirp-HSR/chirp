package chirp.frontend;

import java.util.List;
import java.util.function.Supplier;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.fasterxml.jackson.core.type.TypeReference;

import chirp.api.Serialization;
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
	private final Serialization serialization;

	private final static Logger LOGGER = LoggerFactory.getLogger(HttpTweetRepositoryClient.class);

	public HttpTweetRepositoryClient(String repoUrl) {
		client = ClientBuilder.newClient();
		final WebTarget repo = client.target(repoUrl);
		tweets = repo.path("tweets");
		followers = repo.path("followers");
		serialization = new Serialization();
	}

	@Override
	public void propagateTweet(final Tweet tweet) {
		final Entity<String> entity = Entity.json(
				serialization.serialize(tweet));
		
		final Response resp = tweets
				.request(MediaType.APPLICATION_JSON)
				.header("X-Request-ID", MDC.get("requestId"))
				.post(entity);

		successOrThrow(resp, () -> {
			LOGGER.info("Tweet {} propagated", tweet.hashCode());
			return 0;
		});
	}

	@Override
	public Timeline getTimeline(int userId) {
		final Response resp = tweets
				.queryParam("userId", userId)
				.request(MediaType.APPLICATION_JSON)
				.header("X-Request-ID", MDC.get("requestId"))
				.get();

		return successOrThrow(resp, () -> {
			String json = resp.readEntity(String.class);
			return serialization.deserializer(Timeline.class).apply(json);
		});
	}

	@Override
	public List<Integer> getFollowers(int userId) {
		final Response resp = followers
				.queryParam("userId", userId)
				.request(MediaType.APPLICATION_JSON)
				.header("X-Request-ID", MDC.get("requestId"))
				.get();

		return successOrThrow(resp, () -> {
			String json = resp.readEntity(String.class);
			return serialization.deserializer(new TypeReference<List<Integer>>() {}).apply(json);
		});
	}
	
	private <T> T successOrThrow(Response resp, Supplier<T> handler){
		if (resp.getStatusInfo().getFamily() == Family.SUCCESSFUL) {
			return handler.get();
		} else {
			LOGGER.error("Request failed with status {}", resp.getStatus());
			throw new WebApplicationException(resp.getStatus());
		}
	}
}
