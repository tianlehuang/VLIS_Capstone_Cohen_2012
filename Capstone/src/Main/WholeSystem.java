package Main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import Tools.Pair;
import User.NELLUser;

public class WholeSystem {

	public static void execute() {

		//TwitterAgent tagent = TwitterAgent.getInstance();

//		List<NELLQuery> querySet = NELLQuery.loadQuerySet();
//
//		NELLQuery.classify(querySet);

		int nuser = 100000;
		List<NELLUser> userSet = NELLUser.loadUsers(nuser);

		NELLUser.classify(userSet);
		
		//statitcs about types
		
		Map<String, Integer> count = new HashMap<String, Integer>();
		
		
		for(int i=0; i<userSet.size(); i++){
			
			//get its type set
			for(String t: userSet.get(i).getTopics()){
				if(count.containsKey(t)){
					count.put(t, count.get(t)+1);
					
				}else{
					count.put(t, 1);
				}
			}
			
		}
		List<Pair> p = new ArrayList<Pair>();

		for(Entry<String, Integer> e: count.entrySet()){
			
			Pair h = new Pair();
			h.word = e.getKey();
			h.count = e.getValue();
			
			p.add(h);
		}
		
		Collections.sort(p);
		
		for(int i=0; i<p.size(); i++){
			System.out.println("type " + p.get(i).word + " has count " + (p.get(i).count*1.0/userSet.size()));
		}
		

//		Matching.match(querySet, userSet);
//
//		try {
//			tagent.publish(querySet);
//
//			tagent.collect(userSet);
//		} catch (TwitterException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}

	public static void main(String[] args) {

		WholeSystem s = new WholeSystem();

		s.execute();

	}

}
