package Main;

import java.util.List;

import twitter4j.TwitterException;

import Matcher.Matching;
import Query.NELLQuery;
import Twitter.TwitterAgent;
import User.NELLUser;

public class WholeSystem {

	public static void execute() {

		TwitterAgent ta = new TwitterAgent();

		List<NELLQuery> querySet = NELLQuery.loadQuerySet();

		NELLQuery.classify(querySet);

		List<NELLUser> userSet = NELLUser.loadUsers();

		NELLUser.classify(userSet);

		Matching.match(querySet, userSet);

		try {
			ta.publish(querySet);

			ta.collect(userSet);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {

		WholeSystem s = new WholeSystem();

		s.execute();

	}

}
