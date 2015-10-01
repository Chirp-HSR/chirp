package chirp.api;

import java.util.List;

public interface TweetRepository {
	public void propagateTweet(Tweet tweet);

	public Timeline getTimeline(int userId);
	
	public List<Integer> getFollowers(int userId);
}
