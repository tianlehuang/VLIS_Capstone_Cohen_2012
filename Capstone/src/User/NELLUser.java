package User;

import java.util.ArrayList;
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

	public static List<NELLUser> loadUsers() {

		System.out.println("********************************************");
		System.out.println("start load users from dummy data");

		List<NELLUser> userSet = new ArrayList<NELLUser>();

		NELLUser u1 = new NELLUser();
		u1.setName("Jim");
		List<String> ts1 = new ArrayList<String>();
		ts1.add("Google CEO is smart!");
		ts1.add("Yahoo has a new CEO");
		u1.setTweets(ts1);
		userSet.add(u1);

		NELLUser u2 = new NELLUser();
		u2.setName("Tom");
		List<String> ts2 = new ArrayList<String>();
		ts2.add("Cats and dogs are cute");
		ts2.add("Discovery has a new program about wile animals");
		u2.setTweets(ts2);
		userSet.add(u2);

		NELLUser u3 = new NELLUser();
		u3.setName("Kate");
		List<String> ts3 = new ArrayList<String>();
		ts3.add("Movies about cowboys are so exciting");
		ts3.add("I love songs about cowboys");
		u3.setTweets(ts3);
		userSet.add(u3);

		System.out.println("finish load users from dummy data");
		System.out.println("users are successfully loaded into the system");
		System.out.println("The number of users in the system is "
				+ userSet.size());

		return userSet;
	}

	public static void classify(List<NELLUser> userSet) {

		System.out.println("********************************************");

		System.out.println("start classify users");
		List<String> t1 = new ArrayList<String>();
		t1.add("CEO");
		t1.add("Google");
		t1.add("Yahoo");
		userSet.get(0).setTopics(t1);

		List<String> t2 = new ArrayList<String>();
		t2.add("animal");
		t2.add("cat");
		t2.add("dog");
		userSet.get(1).setTopics(t2);

		List<String> t3 = new ArrayList<String>();
		t3.add("cowboy");
		t3.add("movie");
		t3.add("song");
		userSet.get(2).setTopics(t3);

		System.out.println("finish classify queries");

	}

}
