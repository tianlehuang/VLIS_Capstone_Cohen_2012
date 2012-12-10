package Main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import twitter4j.TwitterException;

import Matcher.Matching;
import Query.NELLQuery;
import SVDSolver.Result;
import SVDSolver.SVDSolver;
import Tools.Pair;
import Twitter.TwitterAgent;
import User.NELLUser;

public class WholeSystem {

	public static void execute() {

		// TwitterAgent tagent = TwitterAgent.getInstance();

		// List<NELLQuery> querySet = NELLQuery.loadQuerySet();
		//
		// NELLQuery.classify(querySet);

		int nuser = 300;
		// List<NELLUser> userSet = NELLUser.loadUsers(nuser);
		List<NELLUser> userSet = NELLUser.loadUsers_exp2(nuser);
		try {
			Thread.currentThread().sleep(10 * 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		NELLUser.classify(userSet);

		// String[] topics = new String[] { "human", "interface", "computer",
		// "user", "system", "response", "time", "EPS", "survey", "trees",
		// "graph", "minors" };
		// String[] topics = new String[]{"education", "computer", "book",
		// "music", "tv", "story", "business"};
		// String[] topics = (String[])NELLUser.allTopics.toArray();

		String[] topics = new String[NELLUser.allTopics.size()];

		Iterator itrt = NELLUser.allTopics.iterator();
		int index = 0;
		while (itrt.hasNext()) {

			topics[index] = (String) itrt.next();
			index++;
		}

		long start = System.currentTimeMillis() / 1000000;
		// Result r = SVDSolver.analyze(topics);
		Result r = SVDSolver.analyze_exp2(topics, userSet);
		long end = System.currentTimeMillis() / 1000000;
		System.out.println("it takes " + (end - start));
		List<NELLQuery> querySet = NELLQuery.loadQuerySet();

		Matching.match(querySet, userSet, r);

		// statitcs about types

		Map<String, Integer> count = new HashMap<String, Integer>();

		for (int i = 0; i < userSet.size(); i++) {

			// get its type set
			for (String t : userSet.get(i).getTopics()) {
				if (count.containsKey(t)) {
					count.put(t, count.get(t) + 1);

				} else {
					count.put(t, 1);
				}
			}

		}
		List<Pair> p = new ArrayList<Pair>();

		for (Entry<String, Integer> e : count.entrySet()) {

			Pair h = new Pair();
			h.word = e.getKey();
			h.count = e.getValue();

			p.add(h);
		}

		Collections.sort(p);

		// for (int i = 0; i < p.size(); i++) {
		// System.out.println("type " + p.get(i).word + " has count "
		// + (p.get(i).count * 1.0 / userSet.size()));
		// }

		// Matching.match(querySet, userSet);
		//
		// try {
		// tagent.publish(querySet);
		//
		// tagent.collect(userSet);
		// } catch (TwitterException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

	}

	
	public static void main(String[] args) {

		WholeSystem s = new WholeSystem();

		// s.execute();

		// s.broadCast("Exp2B");
		//s.collect();
		// s.execute();
		// s.stat();

		// s.exp1();
		// s.exp2();
		// s.exp2();
		// s.follow();
	}

}
