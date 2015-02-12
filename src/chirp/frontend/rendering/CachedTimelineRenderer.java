package chirp.frontend.rendering;

import chirp.api.Tweet;

public class CachedTimelineRenderer extends TimelineRenderer {
	@Override
	public String renderTweet(Tweet tweet) {
		// TODO Exercise 13: Implement in-memory caching
		return super.renderTweet(tweet);
	}
}
