package Query;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import User.NELLUser;

public class NELLQuery {

	public String query;

	public String entity;
	
	public List<String> types;
	
	public List<NELLUser> users;

	public String getQuery() {

		return query;
	}

	public List<String> getTypes() {

		return types;
	}

	public List<NELLUser> getUsers() {

		return users;
	}

	public void setUsers(List<NELLUser> us) {

		users = us;
	}

	public void setQuery(String q) {
		query = q;
	}

	public void setTypes(List<String> ts) {
		types = ts;
	}

	public static List<NELLQuery> loadQuerySet() {

		//
		System.out.println("********************************************");
		System.out.println("start load queries from data file");

		List<NELLQuery> querySet = new ArrayList<NELLQuery>();

		String content = "";
		String dir = "NELLQueryData/";
		String doc = "queries.txt";
		File f = new File(dir+doc);
		
		int count = 0;
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			while(true){
				
				count++;
				if(count<3000){
					continue;
				}
				if(count>3500){
					break;
				}
				
				content = br.readLine();
				if(content == null){
					break;
				}else{
					if(content.isEmpty()){
						continue;
					}
					
					NELLQuery q = new NELLQuery();
					//System.out.println("content is " + content);
					String[] tokens = content.split("	");
					
					System.out.println("1st is " + tokens[0]+", 2nd is " + tokens[1] + 
							", 3rd is " + tokens[2] + ", 4th is " + tokens[3]);
					//System.out.println("number of tokens is " + tokens.length);
					//System.out.println("tokens[3] is " + tokens[3]);
					q.entity = tokens[3].split("\\|")[0];
					//System.out.println("entity is " + q.entity);
					
					List<String> ts = new ArrayList<String>();
					ts.add(tokens[1]);
					q.types = ts;
					querySet.add(q);
					//System.out.println(content);
				}
				
			}
			
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//
//		q1.setQuery("daniel_hesse is the CEO of sprint");
//		querySet.add(q1);
//
//		NELLQuery q2 = new NELLQuery();
//		q2.setQuery("predators is an animal that preys on stoats");
//		querySet.add(q2);
//
//		NELLQuery q3 = new NELLQuery();
//		q3.setQuery("cowboys is an athlete also known as bills");
//		querySet.add(q3);

		System.out.println("finish load queries from dummy data");
		System.out.println("queries are successfully loaded into the system");
		System.out.println("The number of queries in the system is "
				+ querySet.size());
		
		//for exp2, execute the following
		
		Collections.shuffle(querySet);
		int t = 187;
		List<NELLQuery> querySet_2 = new ArrayList<NELLQuery>();

		for(int i=0; i<querySet.size() && i<t; i++){
			querySet_2.add(querySet.get(i));
			
		}

		//return querySet;
		return querySet_2;
	}

	public static List<NELLQuery> loadQuerySetTest() {

		//
		System.out.println("********************************************");
		System.out.println("start load queries from dummy data");
		List<NELLQuery> querySet = new ArrayList<NELLQuery>();

		NELLQuery q1 = new NELLQuery();
		q1.setQuery("daniel_hesse is the CEO of sprint");
		querySet.add(q1);

		NELLQuery q2 = new NELLQuery();
		q2.setQuery("predators is an animal that preys on stoats");
		querySet.add(q2);

		NELLQuery q3 = new NELLQuery();
		q3.setQuery("cowboys is an athlete also known as bills");
		querySet.add(q3);

		System.out.println("finish load queries from dummy data");
		System.out.println("queries are successfully loaded into the system");
		System.out.println("The number of queries in the system is "
				+ querySet.size());

		return querySet;
	}

	public static void classifyTest(List<NELLQuery> querySet) {
		System.out.println("********************************************");

		System.out.println("start classify queries");
		List<String> types1 = new ArrayList<String>();
		types1.add("CEO");
		types1.add("sprint");
		types1.add("daniel_hesse");
		querySet.get(0).setTypes(types1);

		List<String> types2 = new ArrayList<String>();
		types1.add("animal");
		types1.add("predator");
		types1.add("stoat");
		querySet.get(1).setTypes(types2);

		List<String> types3 = new ArrayList<String>();
		types1.add("cowboy");
		types1.add("athlete");
		types1.add("bill");
		querySet.get(2).setTypes(types3);

		System.out.println("finish classify queries");

	}
	
	public static void main(String[] args){
		
		NELLQuery.loadQuerySet();
		
		
	}

}
