package User;

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
import java.util.Set;
import java.util.TreeMap;

import Freebase.FreebaseAgent;
import Tagger.TaggerAgent;

import Tools.Pair;
import Twitter.TwitterAgent;

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

	public static List<Long> loadFollowIds(){
		List<Long> ids = new ArrayList<Long>();
		
		String content = "";
		String dir = "TestData/";
		String doc = "idsOfFollowers.txt";
		File f = new File(dir+doc);
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			while(true){
				
				content = br.readLine();
				if(content == null){
					break;
				}else{
					ids.add(Long.parseLong(content));
				}
				
			}
			
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ids;
		
	}

	public static List<NELLUser> loadUsers(int nuser) {

		System.out.println("********************************************");
		System.out.println("start load users from dummy data");

		List<NELLUser> userSet = new ArrayList<NELLUser>();

		TwitterAgent tagent = TwitterAgent.getInstance();

		//List<Long> followers = tagent.getFollowersIds();
		List<Long> followers = loadFollowIds();
		
		followers.clear();
		
		followers.add((long)158414847);
		
		followers.add((long)20416406);
		
		followers.add((long)759251);
		followers.add((long)1367531);
		
		//followers.add((long)47887649);
		
		followers.add((long)155659213);
		
		System.out.println("the total number of followers in nell is " + followers.size());

		int count = 0;
		for (Long id : followers) {
			count++;
			if (count > nuser) {
				break;
			}
			// for each user, find name, tweets
			NELLUser u = new NELLUser();
			u.setName(tagent.getName(id));
			u.setTweets(retrieveTweetsForUser(id, tagent.getName(id)));
			System.out.println("load tweets for user " + u.getName());
			userSet.add(u);
		}

		System.out.println("finish load users from dummy data");
		System.out.println("users are successfully loaded into the system");
		System.out.println("The number of users in the system is "
				+ userSet.size());

		// List<NELLUser> userSet = new ArrayList<NELLUser>();
		//
		// NELLUser u1 = new NELLUser();
		// u1.setName("Jim");
		// List<String> ts1 = new ArrayList<String>();
		// ts1.add("Google CEO is smart!");
		// ts1.add("Yahoo has a new CEO");
		// u1.setTweets(ts1);
		// userSet.add(u1);
		//
		// NELLUser u2 = new NELLUser();
		// u2.setName("Tom");
		// List<String> ts2 = new ArrayList<String>();
		// ts2.add("Cats and dogs are cute");
		// ts2.add("Discovery has a new program about wile animals");
		// u2.setTweets(ts2);
		// userSet.add(u2);
		//
		// NELLUser u3 = new NELLUser();
		// u3.setName("Kate");
		// List<String> ts3 = new ArrayList<String>();
		// ts3.add("Movies about cowboys are so exciting");
		// ts3.add("I love songs about cowboys");
		// u3.setTweets(ts3);
		// userSet.add(u3);
		//

		return userSet;
	}

	public static void classify(List<NELLUser> userSet) {

		for (int i = 0; i < userSet.size(); i++) {

			// now we are dealing user i

			NELLUser user = userSet.get(i);
			System.out
					.println("****************************************************");
			System.out.println("for user " + user.getName());

			List<String> t = findUserTopics(user);
			System.out
					.println("****************************************************");
			user.setTopics(t);

			// for(int j=0; j<t.size(); j++){
			// System.out.print(t.get(j)+",");
			// }

		}

		//
		// System.out.println("********************************************");
		//
		// System.out.println("start classify users");
		// List<String> t1 = new ArrayList<String>();
		// t1.add("CEO");
		// t1.add("Google");
		// t1.add("Yahoo");
		// userSet.get(0).setTopics(t1);
		//
		// List<String> t2 = new ArrayList<String>();
		// t2.add("animal");
		// t2.add("cat");
		// t2.add("dog");
		// userSet.get(1).setTopics(t2);
		//
		// List<String> t3 = new ArrayList<String>();
		// t3.add("cowboy");
		// t3.add("movie");
		// t3.add("song");
		// userSet.get(2).setTopics(t3);
		//
		// System.out.println("finish classify queries");
		//
	}

	public static List<String> findUserTopics(NELLUser user) {

		List<String> topics = new ArrayList<String>();

		HashMap<String, Integer> nouns = new HashMap<String, Integer>();

		TaggerAgent tagent = TaggerAgent.getInstance();

		for (String tw : user.getTweets()) {

			// String tagged = TaggerAgent.tagLine(tw);
			// tw = tw.replace("@", "");
			tw = tw.replace("#", "");
			tw = tw.replace(",", " ");

			String tagged = tagent.tagLine(tw);

			for (String word : tagged.split(" ")) {
				if (word.toLowerCase().startsWith("http")) {
					continue;
				}
				if (word.toLowerCase().startsWith("@")) {
					continue;
				}

				if (word.endsWith("_NN")) {
					String act = word.replace("_NN", "");

					if (nouns.containsKey(act)) {

						nouns.put(act, nouns.get(act) + 1);

					} else {
						nouns.put(act, 1);
					}

				}

			}

		}

		// now we have all the nouns of one user

		// we want to find the count of each type

		Map<String, Integer> typeCount = new HashMap<String, Integer>();	
		
		for (Entry<String, Integer> e : nouns.entrySet()) {

			// System.out.println(e.getKey() + ", " + e.getValue());
			String word = e.getKey();
			Integer count = e.getValue();

			// for word, we want to know its type set.
			// we can check the local file system first
			// if it is there, we just load the json string from the file
			// if it is not there, we can query from the web and store the
			// string to file

			Set<String> types = retrieveTypeForWord(word);

			List<String> commonTopics = Arrays.asList("/freebase/query",
					"/common/topic", "/type/type", "freebase", "common", "type", "m");

			if (types != null) {
				// System.out.println("The total number of types for word " +
				// word + ", is " + types.size());
				Iterator itr = types.iterator();
				while (itr.hasNext()) {
					String p = (String) itr.next();
					if (commonTopics.contains(p)) {
						continue;
					}
					if (typeCount.containsKey(p)) {
						typeCount.put(p, typeCount.get(p) + count);
					} else {
						typeCount.put(p, count);
					}
				}
				
				String pp = word;
				if (typeCount.containsKey(pp)) {
					typeCount.put(pp, typeCount.get(pp) + 200);
				} else {
					typeCount.put(pp, 200);
				}

			}

		}
		
		System.out.println("the total number of types are " + typeCount.size());


		int numOfTypesWant = 10;
		System.out.println("start compute top " + numOfTypesWant + " types");

		if (typeCount.size() <= numOfTypesWant) {
			for (String tt : typeCount.keySet()) {
				topics.add(tt);
				System.out.println("for topic " + tt + ",its count is "
						+ typeCount.get(tt));
			}

		} else {

			List<Pair> results = new ArrayList<Pair>();

			int count = 0;
			int min = -1;
			int max = -1;

			int indi = 0;

			for (Entry<String, Integer> e : typeCount.entrySet()) {
				count++;
				// System.out.println("at pair " + count);
				String cKey = e.getKey();
				Integer cValue = e.getValue();

				if (count <= numOfTypesWant) {
					Pair p = new Pair();
					p.word = cKey;
					p.count = cValue;
					results.add(p);

					if (count == numOfTypesWant) {
						Collections.sort(results);
						min = results.get(0).count;
						max = results.get(numOfTypesWant - 1).count;

					}

				} else {

					if (cValue > min) {

						// System.out.println("update the array at time " +
						// (indi++));
						Pair n = new Pair();
						n.word = cKey;
						n.count = cValue;
						int j = 1;

						while (j < numOfTypesWant
								&& n.compareTo(results.get(j)) > 0) {

							results.set(j - 1, results.get(j));
							j++;
						}

						results.set(j - 1, n);
					}

				}

			}

			for (int i = 0; i < numOfTypesWant; i++) {
				topics.add(results.get(i).word);
//				System.out
//						.println("for topic " + results.get(i).word
//								+ ",its count is "
//								+ typeCount.get(results.get(i).word));
				System.out
				.println("for topic " + results.get(i).word);

			}

		}
		System.out.println("finish compute top10 types");

		return topics;
	}

	public static List<String> retrieveTweetsForUser(long id, String name) {

		String dir = "Tweets/";
		String tent = dir + name;

		List<String> tweets = new ArrayList<String>();
		TwitterAgent tagent = TwitterAgent.getInstance();
		File f = new File(tent);

		String content = "";
		if (f.exists()) {
			// we already have it in the system
			System.out.println("For user: " + name + ", load from file");
			try {
				BufferedReader br = new BufferedReader(new FileReader(f));
				while (true) {

					content = br.readLine();
					if (content == null) {
						break;
					} else {
						tweets.add(content);
					}

				}

				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			// we have to query from the web
			System.out.println("For user: " + name + ", query from web");
			tweets = tagent.retrieveFromAUser(id);
			// we also have to store it to the folder
			System.out.println("For user: " + name
					+ ", store the result to file");
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(f));
				for (String o : tweets) {
					bw.append(o + "\n");
				}

				bw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// now we have the string of content replied from the freebase server
		return tweets;
	}

	public static Set<String> retrieveTypeForWord(String word) {

		String dir = "FreeBase_Knowledge/";
		String tent = dir + word;
		FreebaseAgent fagent = FreebaseAgent.getInstance();
		File f = new File(tent);

		String content = "";
		if (f.exists()) {
			// we already have it in the system
			//System.out.println("For word: " + word + ", load from file");
			try {
				BufferedReader br = new BufferedReader(new FileReader(f));
				content = br.readLine();
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			// we have to query from the web
			//System.out.println("For word: " + word + ", query from web");
			content = fagent.getTypesString(word);
			// we also have to store it to the folder
//			System.out.println("For word: " + word
//					+ ", store the result to file");
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(f));
				bw.append(content);
				bw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// now we have the string of content replied from the freebase server
		return fagent.getAllTypes(content);
	}

}
