package Matcher;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import Query.NELLQuery;
import SVDSolver.QueryTuple;
import SVDSolver.Result;
import SVDSolver.UserTuple;
import User.NELLUser;

public class Matching {

	public static void match(List<NELLQuery> querySet, List<NELLUser> userSet,
			Result r) {
		System.out.println("********************************************");
		System.out.println("start match query with user");

		Map<NELLQuery, List<UserTuple>> qrs = new HashMap<NELLQuery, List<UserTuple>>();
		Map<String, List<QueryTuple>> urs = new HashMap<String, List<QueryTuple>>();
		for (int i = 0; i < querySet.size(); i++) {
			matchPerQuery(querySet.get(i), userSet, r, qrs, urs);
		}

		/*
		 * List<NELLUser> us1 = new ArrayList<NELLUser>();
		 * us1.add(userSet.get(0)); querySet.get(0).setUsers(us1);
		 * 
		 * List<NELLUser> us2 = new ArrayList<NELLUser>();
		 * us2.add(userSet.get(1)); querySet.get(1).setUsers(us2);
		 * 
		 * List<NELLUser> us3 = new ArrayList<NELLUser>();
		 * us3.add(userSet.get(2)); querySet.get(2).setUsers(us3);
		 */
		System.out.println("finish match query with user");
		System.out.println("start assign query with user");

		Set<NELLQuery> assignedQuery = new HashSet<NELLQuery>();
		
		
		
		//String dir = "Assignments/";
		String dir = "Assignments2/";
		for(Entry<String, List<QueryTuple>> e: urs.entrySet()){
			

			
			String userName = e.getKey();
			List<QueryTuple> qc = e.getValue();

			String tent = dir + userName;			
			File f = new File(tent);

			Collections.sort(qc, Collections.reverseOrder());
			boolean find = false;
			NELLQuery nq = null;
			for(int i=0; i<qc.size(); i++){
				if(!assignedQuery.contains(qc.get(i).q)){
					find = true;
					nq = qc.get(i).q;
					assignedQuery.add(nq);
					break;
				}	
			}
			//System.out.println("**********************************************");


			if(find){
//
//				System.out.println("User " + userName + " is assgined ");
//				System.out.println("query " + nq.entity + " is "
//						+ nq.types.get(0));
				
				try {
					BufferedWriter bw = new BufferedWriter(new FileWriter(f));
					bw.append(nq.entity+" refers to " + nq.types.get(0) + "\n");

					bw.close();
				} catch (Exception ee) {
					ee.printStackTrace();
				}

			}else{
				System.out.println("User " + userName + " is not assgined ");
			}
			
			
		}
		
		
		System.out.println("finish assign query with user");
		
	}

	public static void matchPerQuery(NELLQuery query, List<NELLUser> userSet,
			Result r, Map<NELLQuery, List<UserTuple>> qrs,
			Map<String, List<QueryTuple>> urs) {

		Set<String> typeOfNELLType = NELLUser.retrieveTypeForWord(query.types
				.get(0));
		System.out.println("the current query type is " + query.types.get(0));
		if (typeOfNELLType == null) {
			//System.out.println("are you kidding");
			return;
		}

		String[] topics = r.topics;
		double[] targetVector = new double[topics.length];

		for (int i = 0; i < targetVector.length; i++) {
			if (typeOfNELLType.contains(topics[i])) {
				targetVector[i] = 1;
			} else {
				targetVector[i] = 0;
			}
		}

		//
		double[][] topicVectors = r.topicVectors;
		double[][] userVectors = r.userVectors;

		double[] embeddedVector = new double[topicVectors[0].length];

		Arrays.fill(embeddedVector, 0);

		for (int i = 0; i < targetVector.length; i++) {

			for (int j = 0; j < embeddedVector.length; j++) {
				embeddedVector[j] += topicVectors[i][j] * targetVector[i];

			}
		}

		UserTuple[] scoreForUsers = new UserTuple[userVectors.length];

		// Arrays.fill(scoreForUsers, 0);

		System.out.println("**********************************************");

		System.out.println("for query " + query.entity + " is "
				+ query.types.get(0));

		for (int i = 0; i < userVectors.length; i++) {
			UserTuple u = new UserTuple();
			u.name = r.userIdToName.get(i);
			u.score = 0;
			for (int j = 0; j < userVectors[i].length; j++) {

				u.score += userVectors[i][j] * embeddedVector[j];

			}
			scoreForUsers[i] = u;

			// System.out.println("User " + i + " gets score " +
			// scoreForUsers[i]);
		}

		Arrays.sort(scoreForUsers, Collections.reverseOrder());

		//int top = 100;	//for exp1
		int top = 187;	//for exp2

		List<UserTuple> ut = new ArrayList<UserTuple>();
		
		for (int i = 0; i < top && i < scoreForUsers.length; i++) {
			System.out.println(scoreForUsers[i].name + ","
					+ scoreForUsers[i].score + "#");
			
			ut.add(scoreForUsers[i]);
			
			QueryTuple qt = new QueryTuple();
			qt.q = query;
			qt.score = scoreForUsers[i].score;
			if(urs.containsKey(scoreForUsers[i].name)){
				
				urs.get(scoreForUsers[i].name).add(qt);
				
			}else{
				
				List<QueryTuple> ql = new ArrayList<QueryTuple>();
				ql.add(qt);
				urs.put(scoreForUsers[i].name, ql);
				
			}
			
		}
		qrs.put(query, ut);

	}

	public static void matchTest(List<NELLQuery> querySet,
			List<NELLUser> userSet) {
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
