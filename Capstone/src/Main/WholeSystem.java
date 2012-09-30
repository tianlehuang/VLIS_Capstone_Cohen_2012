package Main;

import java.util.List;

import Matcher.Matching;
import Query.NELLQuery;
import Query.QueryClassifier;
import Twitter.TwitterAgent;
import User.NELLUser;
import User.UserClassify;

public class WholeSystem {
	
	
	
	public static void execute(){
		
		TwitterAgent ta = new TwitterAgent();
		
		List<NELLQuery> querySet = QueryClassifier.loadQuerySet();
		
		QueryClassifier.classify(querySet);
		
		List<NELLUser> userSet = UserClassify.loadUsers();
		
		UserClassify.classify(userSet);
		
		Matching.match(querySet, userSet);
		
		ta.publish(querySet);
		
		ta.collect(userSet);
		
		
	}
	
	public static void main(String[] args){
		
		WholeSystem s = new WholeSystem();
		
		s.execute();
		
		
		
	}

}
