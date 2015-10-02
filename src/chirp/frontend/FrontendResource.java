package chirp.frontend;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.grizzly.http.server.Request;

import com.codahale.metrics.annotation.Timed;

import chirp.api.Timeline;
import chirp.api.Tweet;
import chirp.api.TweetRepository;
import chirp.frontend.rendering.TimelineRenderer;

@Path("/")
@Produces(MediaType.TEXT_HTML)
public class FrontendResource {
	private final TweetRepository repo;

	private final TimelineRenderer renderer;

	public FrontendResource(TweetRepository repo, TimelineRenderer renderer) {
		this.repo = repo;
		this.renderer = renderer;
	}

	@GET
	public Response getMainPage(@Context Request request) {
		return userId(request)
			.map(userId -> Response.seeOther(uri("/tweets")).build())
			.orElseGet(() -> Response.ok(renderLoginForm()).build());
	}

	protected String renderLoginForm() {
		return "<form action=\"/login\" method=\"post\">"
				+ "<span>Please enter user ID (int > 0)</span>"
				+ "<input type=\"integer\" name=\"userId\" />"
				+ "<input type=\"submit\" value=\"Login\" />" + "</form>";
	}

	@GET
	@Path("tweets")
	@Timed
	public Response getTimeline(@Context Request request) throws IOException {
		return userId(request)
			.map(userId -> {
				final Timeline timeline = repo.getTimeline(userId);
				return Response.ok(renderer.renderTimeline(timeline)).build();
			})
			.orElseGet(() -> Response.seeOther(uri("/")).build());
	}

	@POST
	@Path("tweets")
	public Response postTweet(
			@Context Request request,
			@FormParam("content") String content) {
		return userId(request)
			.map(userId -> {
				final Tweet tweet = new Tweet(userId, content);
				repo.propagateTweet(tweet);
				return Response.seeOther(uri("/tweets")).build();
			})
			.orElseGet(() -> Response.status(Status.UNAUTHORIZED).build());
	}

	@POST
	@Path("login")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response doLogin(
			@Context Request request,
			@FormParam("userId") int userId) {
		request.getSession().setAttribute("userId", userId);
		return Response.seeOther(uri("/tweets")).build();
	}

	@GET
	@Path("logout")
	public Response doLogout(@Context Request request) {
		request.getSession().removeAttribute("userId");
		return Response.seeOther(uri("/")).build();
	}
	
	private Optional<Integer> userId(Request request){
		return Optional.ofNullable(
				(Integer) request.getSession().getAttribute("userId"));
	}

	private static URI uri(String uri) {
		try {
			return new URI(uri);
		} catch (URISyntaxException e) {
			return null;
		}
	}
}
