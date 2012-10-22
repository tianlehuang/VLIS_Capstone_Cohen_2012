package Twitter;

import java.util.ArrayList;
import java.util.List;

import twitter4j.DirectMessage;
import twitter4j.IDs;
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

	private static TwitterAgent instance = new TwitterAgent();

	private static Twitter twitter;

	private static User user;

	private TwitterAgent() {

		twitter = authorize();

		try {
			user = twitter.verifyCredentials();

			// System.out.println("**********" + user.getStatus());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static TwitterAgent getInstance() {
		return instance;
	}

	public static Twitter authorize() {

		System.out.println("authorize!");
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
		return tf.getInstance();

	}

	public void update() throws TwitterException {

		String latestStatus = "Send this from Twitter API";

		Status status = twitter.updateStatus(latestStatus);
		System.out.println("Successfully updated the status to ["
				+ status.getText() + "].");

	}

	public void timeline() throws TwitterException {

		// User user = twitter.verifyCredentials();
		List<Status> statuses = twitter.getHomeTimeline();
		System.out.println("Showing @" + user.getScreenName()
				+ "'s home timeline.");
		for (Status status : statuses) {
			System.out.println("@" + status.getUser().getScreenName() + " - "
					+ status.getText());
		}

	}

	public static void directMessage(String recipientId, String msg)
			throws TwitterException {

		recipientId = "sharqwy";

		msg = "Hello, this is tianle huang";
		DirectMessage message = twitter.sendDirectMessage(recipientId, msg);
		System.out.println("Direct message successfully sent to "
				+ message.getRecipientScreenName());
	}

	public static void getDirect() throws TwitterException {

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

	public static void publish(List<NELLQuery> querySet)
			throws TwitterException {

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

	public static void collect(List<NELLUser> userSet) throws TwitterException {
		System.out.println("********************************************");

		System.out.println("start collect replies");
		System.out.println("finish collect replies");

	}

	public static List<Long> getFollowersIds() {

		int count = 0;
		List<Long> follows = new ArrayList<Long>();
		try {
			long cursor = -1;
			IDs ids;
			System.out.println("Listing followers's ids.");
			do {

				ids = twitter.getFollowersIDs("cmunell", cursor);
				//ids = twitter.getFollowersIDs(cursor);

				for (long id : ids.getIDs()) {
					count++;
					System.out.println(id);
					follows.add(id);
				}
			} while ((cursor = ids.getNextCursor()) != 0);
			// System.exit(0);
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get followers' ids: "
					+ te.getMessage());
			// System.exit(-1);
		}

		System.out.println("the total number of followers is " + count);
		return follows;
	}
	
	public static String getName(long id){
		String name = "";
		try{
			name = twitter.showUser(id).getScreenName();
		}catch(Exception e){
			e.printStackTrace();
		}
		return name;
	}

	public static List<String> retrieveFromAUser(long id) {
		System.out.println("Retrieving tweets...");
		List<String> tweets = new ArrayList<String>();
		try {
			String user = twitter.showUser(id).getScreenName();
			System.out.println("The current user is " + user);
			
			
			Query query = new Query("from:" + user);
			query.setRpp(500);
			query.setSince("2000-01-01");

			QueryResult result = twitter.search(query);

			System.out.println("Count : " + result.getTweets().size());

			for (Tweet tweet : result.getTweets()) {

				System.out.println("text : " + tweet.getText());
				tweets.add(tweet.getText());

			}

		} catch (TwitterException e) {

			e.printStackTrace();

		}

		System.out.println("done! ");
		return tweets;
	}

	public static void getFollowersTest() {
		try {

			System.out
					.println("It has so many followers "
							+ twitter.getFollowersIDs(user.getScreenName(), 0)
									.getIDs().length);
			String out = "";

			// "#clt20"
			Query query2 = new Query("SmiffSr").since("2010-10-01")
					.until("2012-10-14").rpp(100);
			QueryResult result2 = twitter.search(query2);
			int i = 1;
			for (Tweet tweet : result2.getTweets()) {
				System.out.println("<img src='" + tweet.getProfileImageUrl()
						+ "'>" + "<b>(" + i + ") " + tweet.getFromUser() + "("
						+ tweet.getCreatedAt() + ")" + "</b>" + " : "
						+ tweet.getText() + "<br/>");
				i++;
			}

			System.out.println("*********************************");
			// twitter.getUserTimeline();
			// Getting user id through his screen name
			User user = twitter.showUser("SmiffSr");
			

			// Getting another user timeline
			List<Status> statuses = twitter.getUserTimeline(user.getId());
			System.out.println("<h2>Showing another user timeline...."
					+ "</h2><br/><br/>");
			System.out.println("<h3>Name of the User :<i>"
					+ user.getName().toString() + "</i></h3><br/><br/>");
			for (Status status2 : statuses) {
				System.out.println("<b>" + status2.getUser().getName()
						+ "</b>:" + status2.getText() + "<br/>");
			}

			System.out.println("show:  " + out);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void te(){
		try{
		//User user = twitter.showUser("TheDailyShow");
		//User user = twitter.showUser("SteveNash");
		//User user = twitter.showUser("CNN");
		//User user = twitter.showUser("FoxNews");
		//User user = twitter.showUser("KobeBryantNews");
		User user = twitter.showUser("Cristiano");
		//User user = twitter.showUser("CNN");
		System.out.println(user.getId());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	

	public static void main(String[] args) {

		TwitterAgent r = TwitterAgent.getInstance();

		// r.authorize();

		try {
			// r.update();

			// r.timeline();

			// r.directMessage();

			// r.search();

			// r.getDirect();
			// r.timeline();
			// r.getFollowers();
			//r.getFollowersIds();
			
			//r.retrieveFromAUser(242922283);
			
			r.te();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
