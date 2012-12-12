package Twitter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import twitter4j.DirectMessage;
import twitter4j.IDs;
import twitter4j.Paging;
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

//		cb.setDebugEnabled(true)
//		.setOAuthConsumerKey("kszNJmvwgnLDWtCLgfF8w")
//		.setOAuthConsumerSecret(
//				"vHO5WWmxuw8xGK6z48iZxaRXoq59bfycmDU362uoow0")
//		.setOAuthAccessToken(
//				"128346877-F8Khjyk1bRQji65sCcnAvg9ciL4danAS7UCiRWfM")
//		.setOAuthAccessTokenSecret(
//				"paEM9nPyGMbQsVT18YMgl7kW4XtRdouz0WYsgHLuQ");


		cb.setDebugEnabled(true)
		.setOAuthConsumerKey("E44XlZ7mn5kGhm2nYjSnpA")
		.setOAuthConsumerSecret(
				"0DjAvaJ4vZFc5G8rrkZikG2IK8zcJGapXUWhMJBScgM")
		.setOAuthAccessToken(
				"128346877-8ODLjVVu5IrA4mRN0N5bIaXPNp7wLITQCxZ1grNX")
		.setOAuthAccessTokenSecret(
				"kweRalb329TJ1NT6vFFfsiU7wx9FzLS2PzVNvnj31D0");

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

//		recipientId = "sharqwy";
//
//		msg = "Hello, this is tianle huang";
		DirectMessage message = twitter.sendDirectMessage(recipientId, msg);
		System.out.println("Direct message successfully sent to "
				+ message.getRecipientScreenName());
	}

	public static void getDirect() throws TwitterException {

		// String senderId = "sharqwy";
		String dir = "Results/";
		Paging paging = new Paging(1, 300);


		List<DirectMessage> message = twitter.getDirectMessages(paging);
		
		//twitter.getDirectMessages();
		String toDate;
	    toDate = "12/7/2012";
	    DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
	    Date toDt = null;
	    try {
			toDt = df.parse(toDate);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		for (DirectMessage dm : message) {

			
			//String user = dm.getSender().getName();
			String user = dm.getSender().getScreenName();
			String content = dm.getText();
			Date d = dm.getCreatedAt();
			
			if(!d.after(toDt)){
				continue;
			}
			System.out
					.println("**********************************************");

			System.out.println("from user " + user  + "," + content + ", at " + dm.getCreatedAt());
			System.out
					.println("**********************************************");

			File f = new File(dir+user);
			
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(f));
				bw.append(content+"\n");
				bw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
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

	public static List<String> retrieveFromAUser(long id, String user) {
		System.out.println("Retrieving tweets...");
		List<String> tweets = new ArrayList<String>();
		try {
//			String dir = "Twitter_Id_UserName/";
//			String tent = dir + id;
//			File f = new File(tent);
//
//			String content = "";
//			String user = "";
//			if (f.exists()) {
//				// we already have it in the system
//				//System.out.println("For word: " + word + ", load from file");
//				try {
//					BufferedReader br = new BufferedReader(new FileReader(f));
//					content = br.readLine();
//					user = content;
//					br.close();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			} else {
//				user = instance.getName(id);
//				
//				try {
//					BufferedWriter bw = new BufferedWriter(new FileWriter(f));
//					bw.append(user);
//					bw.close();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//			
			//user = twitter.showUser(id).getScreenName();
			System.out.println("The current user is " + user);
			
			
			Query query = new Query("from:" + user);
			query.setRpp(500);
			query.setSince("2000-01-01");

			QueryResult result = twitter.search(query);

			List<Tweet> lts = result.getTweets();
			System.out.println("Count : " + lts.size());

			for (Tweet tweet : lts) {

				String c = tweet.getText();
				System.out.println("text : " + c);
				tweets.add(c);

			}

		} catch (TwitterException e) {

			e.printStackTrace();
			System.out.println("user " + user + " is bad");
			//e.printStackTrace();
			System.exit(1);
			

		}

		System.out.println("done! ");
		return tweets;
	}
	
	public static void followUser(String u) throws TwitterException{
		twitter.createFriendship(u);
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
	

	public static void getit() throws TwitterException{
		Paging paging = new Paging(1, 200);

		List<DirectMessage> message = twitter.getDirectMessages(paging);

		for (DirectMessage dm : message) {

			String user = dm.getSender().getName();
			
			dm.getSender().getScreenName();
			String content = dm.getText();
			System.out
					.println("**********************************************");

			System.out.println("from user " + user  + "," + content + ", at " + dm.getCreatedAt());
			System.out
					.println("**********************************************");
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
			
			//r.te();
			r.getit();
		

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
