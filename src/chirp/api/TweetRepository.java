package chirp.api;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface TweetRepository {
	public final static int TIMELINE_SIZE = 100;
	
	public void propagateTweet(Tweet tweet);

	public Timeline getTimeline(int userId);
	
	public default List<Integer> getFollowers(int userId) {
		LOGGER.debug("Generate followers of user {}", userId);
		// simulate i/o for db access
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
		}

		final List<Integer> followers = IntStream
				.range(0, Math.min(userId, 1000) + 1)
				.boxed()
				.collect(Collectors.toList());
		
		LOGGER.debug("User {} has {} followers", userId, followers.size());
		return followers;
	}

	final static Logger LOGGER = LoggerFactory.getLogger(TweetRepository.class);
}
