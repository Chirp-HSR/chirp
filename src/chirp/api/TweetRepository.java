package chirp.api;

import java.util.List;

public interface TweetRepository {
	public void propagateTweet(Tweet tweet);

	public Timeline getTimeline(long userId);
	
	public List<Long> getFollowers(long userId);
}
