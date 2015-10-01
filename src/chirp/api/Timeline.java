package chirp.api;

import java.util.List;

public class Timeline {
	private final int ownerId;
	private final List<Tweet> tweets;

	public Timeline(int ownerId, List<Tweet> tweets){
		this.ownerId = ownerId;
		this.tweets = tweets;
	}
	
	public Timeline(){
		this.ownerId = 0;
		this.tweets = null;
	}

	public Integer getOwnerId() {
		return ownerId;
	}

	public List<Tweet> getTweets() {
		return tweets;
	}
}
