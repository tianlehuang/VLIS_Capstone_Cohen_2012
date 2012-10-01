package Matcher;

import java.util.ArrayList;
import java.util.List;

import Query.NELLQuery;
import User.NELLUser;

public class Matching {
	
	public static void match(List<NELLQuery> querySet, List<NELLUser> userSet){
		System.out.println("********************************************");
		System.out.println("start match query with user");
	
		List<NELLUser> us1 = new ArrayList<NELLUser>();
		us1.add(userSet.get(0));
		querySet.get(0).setUsers(us1);
		
		List<NELLUser> us2 = new ArrayList<NELLUser>();
		us2.add(userSet.get(1));
		querySet.get(1).setUsers(us2);
		
		List<NELLUser> us3 = new ArrayList<NELLUser>();
		us3.add(userSet.get(2));
		querySet.get(2).setUsers(us3);
		
		System.out.println("finish match query with user");
		
	}

}
