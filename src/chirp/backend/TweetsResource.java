package chirp.backend;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import chirp.api.Timeline;
import chirp.api.Tweet;
import chirp.api.TweetRepository;

@Path("/tweets")
@Produces(MediaType.APPLICATION_JSON)
public class TweetsResource {
	private final TweetRepository repo;

	public TweetsResource(TweetRepository repo) {
		this.repo = repo;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void postTweet(Tweet tweet) {
		repo.propagateTweet(tweet);
	}
	
	@GET
	public Timeline getTimeline(@QueryParam("userId") int userId){
		return repo.getTimeline(userId);
	}
}
