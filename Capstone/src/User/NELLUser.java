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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import Freebase.FreebaseAgent;
import Tagger.TaggerAgent;
import Tools.Pair;
import Twitter.TwitterAgent;

public class NELLUser {

	public String name;

	public List<String> tweets;

	public List<String> topics;

	public List<String> replies;
	
	public static Set<String> allTopics = new HashSet<String>();

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

	//Each Tweeter user has a unique ID
	public static List<Long> loadFollowIds(){
		List<Long> ids = new ArrayList<Long>();
			
		String content = "";
		String dir = "TestData/";
		String doc = "idsOfFollowers.txt";
		File f = new File(dir+doc);
		int count = 0;
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			while(true){
//				count++;
//				if(count >30){
//					break;
//				}
//				
				content = br.readLine();
				if(content == null){
					break;
				}else{
					Long cid = Long.parseLong(content);
					//we need to check whether it is empty or not
					
					ids.add(cid);
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
		System.out.println("start load users from real data");
		
		//load the null user list first
		
		List<String> nullUsers = new ArrayList<String>();
		File a = new File("NULLUsers/nulluserlist");
		try {
			BufferedReader br = new BufferedReader(new FileReader(a));
			while(true){

				String c = br.readLine();
				if(c == null){
					break;
				}else{
					nullUsers.add(c);
				}
				
			}
			
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		System.out.println("the number of null user is " + nullUsers.size());

		List<NELLUser> userSet = new ArrayList<NELLUser>();

		TwitterAgent tagent = TwitterAgent.getInstance();

		//List<Long> followers = tagent.getFollowersIds();
		List<Long> followers = loadFollowIds();
//
//		followers.clear();
//		
//		followers.add((long)158414847);
//		
//		followers.add((long)20416406);
//		
//		followers.add((long)759251);
//		followers.add((long)1367531);
//
//		followers.add((long)155659213);
		
		System.out.println("the total number of followers in nell is " + followers.size());
		//followers.add((long)47887649);
		String dir = "Twitter_Id_UserName/";
		//String tent = dir + word;
		//FreebaseAgent fagent = FreebaseAgent.getInstance();
		//File f = new File(tent);
		
		int count = 0;
		for (Long id : followers) {
			count++;
			if(count<=1700){
				//continue;
			}
			
			if (count > 500) {
				//break;
			}
			// for each user, find name, tweets
			NELLUser u = new NELLUser();
			
			String tent = dir + id;
			File f = new File(tent);

			String content = "";
			String name = "";
			if (f.exists()) {
				// we already have it in the system
				//System.out.println("For word: " + word + ", load from file");
				try {
					BufferedReader br = new BufferedReader(new FileReader(f));
					content = br.readLine();
					name = content;
					br.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				name = tagent.getName(id);
				
				try {
					BufferedWriter bw = new BufferedWriter(new FileWriter(f));
					bw.append(name);
					bw.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			//name = tagent.getName(id); 

			if(name == null || name.equals("") || name.trim().length() == 0){
				continue;
			}
			
			if(nullUsers.contains(name)){
				continue;
			}
			
			u.setName(name);
			u.setTweets(retrieveTweetsForUser(id, name));
			System.out.println("load tweets for user " + u.getName());
			userSet.add(u);
		}

		System.out.println("finish load users from real data");
		System.out.println("users are successfully loaded into the system");
		System.out.println("The number of users in the system is "
				+ userSet.size());

	

		return userSet;
	}
	public static List<NELLUser> loadUsers_exp2(int nuser) {

		System.out.println("********************************************");
		System.out.println("start load users from dummy data");
		
		//load the null user list first
		
		List<String> nullUsers = new ArrayList<String>();
		File a = new File("NULLUsers/nulluserlist");
		try {
			BufferedReader br = new BufferedReader(new FileReader(a));
			while(true){

				String c = br.readLine();
				if(c == null){
					break;
				}else{
					nullUsers.add(c);
				}
				
			}
			
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		System.out.println("the number of null user is " + nullUsers.size());
		
		List<String> e1aUsers = new ArrayList<String>();
		String b = "Exp1A/";
		File fb = new File(b);
		for (File f : fb.listFiles()) {
			e1aUsers.add(f.getName());
		}
		System.out.println("the total number of user in e1a is " + e1aUsers.size());

		List<String> e1bUsers = new ArrayList<String>();
		String c = "Exp1B/";
		File fc = new File(c);
		for (File f : fc.listFiles()) {
			e1bUsers.add(f.getName());
		}
		System.out.println("the total number of user in e1b is " + e1bUsers.size());
	
		List<NELLUser> userSet = new ArrayList<NELLUser>();

		TwitterAgent tagent = TwitterAgent.getInstance();

		//List<Long> followers = tagent.getFollowersIds();
		List<Long> followers = loadFollowIds();
		
		System.out.println("the total number of followers in nell is " + followers.size());
		//followers.add((long)47887649);
		String dir = "Twitter_Id_UserName/";
		//String tent = dir + word;
		//FreebaseAgent fagent = FreebaseAgent.getInstance();
		//File f = new File(tent);
		Collections.shuffle(followers);
		int t = 113;
		
		int count = 0;
		for (Long id : followers) {
			if(count >= t){
				break;
			}
			
			// for each user, find name, tweets
			NELLUser u = new NELLUser();
			
			String tent = dir + id;
			File f = new File(tent);

			String content = "";
			String name = "";
			if (f.exists()) {
				// we already have it in the system
				//System.out.println("For word: " + word + ", load from file");
				try {
					BufferedReader br = new BufferedReader(new FileReader(f));
					content = br.readLine();
					name = content;
					br.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				name = tagent.getName(id);
				
				try {
					BufferedWriter bw = new BufferedWriter(new FileWriter(f));
					bw.append(name);
					bw.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			//name = tagent.getName(id); 

			if(name == null || name.equals("") || name.trim().length() == 0){
				continue;
			}
			
			if(nullUsers.contains(name) || e1aUsers.contains(name) || e1bUsers.contains(name)){
				continue;
			}
			
			u.setName(name);
			u.setTweets(retrieveTweetsForUser(id, name));
			System.out.println("load tweets for user " + u.getName());
			userSet.add(u);
			count++;
		}

		System.out.println("finish load users from dummy data");
		System.out.println("users are successfully loaded into the system");
		System.out.println("The number of users in the system is "
				+ userSet.size());

	
		return userSet;
	}

	
	//find the topN topics for each user
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



		}

	
	}

	public static List<String> findUserTopics(NELLUser user) {	
		
		List<String> topics = new ArrayList<String>();

		String dir = "UserTopics/";
		String tent = dir + user.getName();
		String name = user.getName();
		
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
						topics.add(content);
						allTopics.add(content);
					}

				}

				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return topics;
		}


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
					//allTopics.add(p);
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
		System.out.println("finish compute top " + numOfTypesWant + " types");

		//write back to file
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			for (String o : topics) {
				bw.append(o + "\n");
				
				if(o.length() == 1 || o.contains("1") || o.contains("?")){
					continue;
				}
				allTopics.add(o);
			}

			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			tweets = tagent.retrieveFromAUser(id, name);
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
				System.out.println("user " + name + " is bad");
				e.printStackTrace();
				System.exit(1);
				
				
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

	
	
	public HashMap<String, List<String>> summaryUsers(){
		
		
		return null;
		
	}
}
