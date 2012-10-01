package Twitter;

import java.util.List;

import twitter4j.DirectMessage;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Tweet;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;
import Query.NELLQuery;
import User.NELLUser;

public class TwitterAgent {

	public static Twitter twitter;

	public void authorize() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
				.setOAuthConsumerKey("pU7fedemNqtJLbFI5DUXxQ")
				.setOAuthConsumerSecret(
						"IFCGEYvGTfVrnPqZ9U13BpQiyOXVxXJQZEHq5ww9E")
				.setOAuthAccessToken(
						"92151054-E6bDzKb6JxhM881kiHTx2sQGOc5NQT02e0ffpHR98")
				.setOAuthAccessTokenSecret(
						"eQB2QeIRBLs0GmEE8AVwWq9Cp1kASppqziN3HX7aR58");
		TwitterFactory tf = new TwitterFactory(cb.build());
		twitter = tf.getInstance();

	}

	public void update() throws TwitterException {

		String latestStatus = "Send this from Twitter API";

		Status status = twitter.updateStatus(latestStatus);
		System.out.println("Successfully updated the status to ["
				+ status.getText() + "].");

	}

	public void timeline() throws TwitterException {

		User user = twitter.verifyCredentials();
		List<Status> statuses = twitter.getHomeTimeline();
		System.out.println("Showing @" + user.getScreenName()
				+ "'s home timeline.");
		for (Status status : statuses) {
			System.out.println("@" + status.getUser().getScreenName() + " - "
					+ status.getText());
		}

	}

	public void directMessage(String recipientId, String msg)
			throws TwitterException {

		recipientId = "sharqwy";

		msg = "Hello, this is tianle huang";
		DirectMessage message = twitter.sendDirectMessage(recipientId, msg);
		System.out.println("Direct message successfully sent to "
				+ message.getRecipientScreenName());
	}

	public void getDirect() throws TwitterException {

		// String senderId = "sharqwy";

		List<DirectMessage> message = twitter.getDirectMessages();

		for (DirectMessage dm : message) {

			System.out
					.println("**********************************************");

			System.out.println(dm.getText() + "," + dm.getSender().getName());
			System.out
					.println("**********************************************");

		}

	}

	public void search() throws TwitterException {

		// Query query = new Query("source:twitter4j stuff");
		Query query = new Query("stuff");
		QueryResult result = twitter.search(query);
		for (Tweet tweet : result.getTweets()) {
			System.out
					.println("**********************************************");

			System.out.println(tweet.getFromUser() + ":" + tweet.getText());

			System.out
					.println("**********************************************");
		}

	}

	public void publish(List<NELLQuery> querySet) throws TwitterException {

		System.out.println("********************************************");

		System.out.println("start publish queries");
		for (NELLQuery q : querySet) {

			for (NELLUser u : q.getUsers()) {
				String recipientId = u.getName();
				String msg = q.getQuery();

				// directMessage(recipientId, msg);
			}

		}
		System.out.println("finish publish queries");
	}

	public void collect(List<NELLUser> userSet) throws TwitterException {
		System.out.println("********************************************");

		System.out.println("start collect replies");
		System.out.println("finish collect replies");


	}

	public static void main(String[] args) {

		TwitterAgent r = new TwitterAgent();

		r.authorize();

		try {
			// r.update();

			// r.timeline();

			// r.directMessage();

			// r.search();

			r.getDirect();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
