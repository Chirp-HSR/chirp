package chirp.backend;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import chirp.api.TweetRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api()
@Path("/followers")
@Produces(MediaType.APPLICATION_JSON)
public class FollowersResource {
	private final TweetRepository repo;

	public FollowersResource(TweetRepository repo) {
		this.repo = repo;
	}

	@ApiOperation(value = "The followers of `userId`")
	@GET
	public int[] getFollowers(
			@ApiParam(required = true) @QueryParam("userId") int userId) {
		// swagger currently has some issues with operations returning generic lists;
		// works fine with arrays
		return repo.getFollowers(userId).stream().mapToInt(i -> i).toArray();
	}
}
