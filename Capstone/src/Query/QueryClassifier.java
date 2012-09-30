package Query;

import java.util.ArrayList;
import java.util.List;

public class QueryClassifier {
	
	public static List<NELLQuery> loadQuerySet(){
		
		//
		
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
		System.out.println("The number of queries in the system is " + querySet.size());
		
		return querySet;
	}
	
	public static void classify(List<NELLQuery> querySet){
		System.out.println("start classify queries");
		List<String> types1 = new ArrayList<String>();
		types1.add("CEO");
		types1.add("sprint");
		types1.add("daniel_hesse");
		querySet.get(0).setTypes(types1);
		
		List<String> types2 = new ArrayList<String>();
		types1.add("");
		types1.add("sprint");
		types1.add("daniel_hesse");
		querySet.get(1).setTypes(types2);
		
		System.out.println("finish classify queries");		

	}
	

}
