package chirp.frontend.rendering;

import java.util.Random;
import java.util.stream.IntStream;

import chirp.api.Timeline;
import chirp.api.Tweet;

public class TimelineRenderer {

	public String renderTimeline(Timeline timeline) {
		final String content = 
				"<h1>Chirp</h1>" + "<a href=\"/logout\">Logout</a>"
				+ "<h2>Post Tweet</h2>"
				+ "<form action=\"tweets\" method=\"post\">"
				+ "<textarea name=\"content\"></textarea>"
				+ "<input type=\"submit\" value=\"Submit Tweet\" />"
				+ "</form>" + "<h2>Timeline</h2>";

		return content + 
				timeline.getTweets().stream()
			.map(this::renderTweet)
			.reduce("", String::concat);
	}

	public String renderTweet(Tweet tweet) {
		// rendering real tweets is expensive, we would have to
		// parse hashtags and URLs and probably include third party
		// content (e.g. from image hosters or news sites)
		final long n = calc();
		return String.format("<p><span style=\"color: red\">%d</span><span>%s</span><small>%d</small>",
				tweet.getOriginatorId(), tweet.getContent(), n);
	}

	private long calc() {
		// simulate workload by adding some numbers
		final Random r = new Random();
		return IntStream.generate(() -> r.nextInt(10))
			.limit(40000)
			.reduce(0, (a, b) -> a + b);
	}
}
