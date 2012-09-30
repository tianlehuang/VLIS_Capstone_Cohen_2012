package User;

import java.util.List;

public class NELLUser {

	public String name;

	public List<String> tweets;

	public List<String> topics;
	
	public List<String> replies;

	public String getName() {

		return name;
	}

	public void setName(String n) {
		name = n;

	}

	public List<String> getTweets() {

		return tweets;
	}

	public void setTweets(List<String> ts) {

		tweets = ts;
	}

	public List<String> getTopics() {

		return topics;
	}

	public void setTopics(List<String> ts) {

		topics = ts;
	}
	
	public List<String> getReplies() {

		return replies;
	}

	public void setReplies(List<String> rs) {
		replies = rs;

	}
}
