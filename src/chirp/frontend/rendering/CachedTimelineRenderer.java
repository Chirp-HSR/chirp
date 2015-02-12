package chirp.frontend.rendering;

import java.util.concurrent.TimeUnit;

import chirp.api.Tweet;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class CachedTimelineRenderer extends TimelineRenderer {
	private final LoadingCache<Tweet, String> renderedTweets = CacheBuilder
			.newBuilder().maximumSize(1000)
			.expireAfterAccess(5, TimeUnit.MINUTES)
			.build(new CacheLoader<Tweet, String>() {
				@Override
				public String load(Tweet tweet) {
					return CachedTimelineRenderer.super.renderTweet(tweet);
				}
			});

	@Override
	public String renderTweet(Tweet tweet) {
		return renderedTweets.getUnchecked(tweet);
	}
}
