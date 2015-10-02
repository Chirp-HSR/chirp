package chirp.backend;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import chirp.api.Timeline;
import chirp.api.Tweet;
import chirp.api.TweetRepository;

public class InMemoryTweetRepository implements TweetRepository {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(InMemoryTweetRepository.class);
	
	private final Map<Integer, Queue<Tweet>> timelines = new HashMap<>();

	@Override
	public void propagateTweet(Tweet tweet) {
		final List<Integer> followers = getFollowers(tweet.getOriginatorId());
		LOGGER.debug("Propagate tweet {} to followers of user {} (total {} followers)", tweet.hashCode(),
				tweet.getOriginatorId(), followers.size());
		
		followers.forEach(followerId -> {
			LOGGER.trace("Push tweet {} to timeline {}", tweet, followerId);
			final Queue<Tweet> timeline = timelines.getOrDefault(followerId, new CircularFifoQueue<>(100));
			
			timeline.offer(tweet);
			
			timelines.put(followerId, timeline);
		});
		
		LOGGER.debug("{} tweets written", followers.size());
	}

	@Override
	public Timeline getTimeline(int userId) {
		LOGGER.debug("Fetch timeline of user {}", userId);
		
		final Queue<Tweet> timeline = timelines.getOrDefault(userId, new CircularFifoQueue<>(100));
		
		final List<Tweet> tweets = new LinkedList<>(timeline);
		Collections.reverse(tweets);
		
		LOGGER.debug("Received {} tweets", tweets.size());
		
		return new Timeline(userId, tweets);
	}

}
