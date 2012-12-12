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

import Experiments.Exp;
import Matcher.Matching;
import Query.NELLQuery;
import SVDSolver.Result;
import SVDSolver.SVDSolver;
import Tools.Pair;
import Twitter.TwitterAgent;
import User.NELLUser;

//driver for the whole system
public class WholeSystem {

	//it starts the pipeline
	public static void execute() {

		int nuser = 300;
		//load users into system
		List<NELLUser> userSet = NELLUser.loadUsers(nuser);
		//List<NELLUser> userSet = NELLUser.loadUsers_exp2(nuser);
		//classify users by figuring out their interested topics
		NELLUser.classify(userSet);

		//union of all the top10 topics
		String[] topics = new String[NELLUser.allTopics.size()];

		Iterator itrt = NELLUser.allTopics.iterator();
		int index = 0;
		while (itrt.hasNext()) {
			topics[index] = (String) itrt.next();
			index++;
		}

		long start = System.currentTimeMillis() / 1000000;
		//start svd solver to decompose matrix
		Result r = SVDSolver.analyze(topics);
		//Result r = SVDSolver.analyze_exp2(topics, userSet);
		long end = System.currentTimeMillis() / 1000000;
		System.out.println("it takes " + (end - start));
		
		//load query set into the system
		List<NELLQuery> querySet = NELLQuery.loadQuerySet();

		//match users with queries
		Matching.match(querySet, userSet, r);
		printStat(userSet);
	
	}
	
	public static void printStat(List<NELLUser> userSet){
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

		
	}

	public static void main(String[] args) {

		WholeSystem s = new WholeSystem();

		Exp e = new Exp();

		//e.collect();

		e.analyze2();
		// s.execute();

		// s.broadCast("Exp2B");
		// s.collect();
		// s.execute();
		// s.stat();

		// s.exp1();
		// s.exp2();
		// s.exp2();
		// s.follow();
	}

}
