package chirp.backend;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import chirp.api.TweetRepository;

@Path("/followers")
@Produces(MediaType.APPLICATION_JSON)
public class FollowersResource {
	private final TweetRepository repo;

	public FollowersResource(TweetRepository repo) {
		this.repo = repo;
	}

	@GET
	public Iterable<Long> getFollowers(@QueryParam("userId") int userId) {
		return repo.getFollowers(userId);
	}
}
