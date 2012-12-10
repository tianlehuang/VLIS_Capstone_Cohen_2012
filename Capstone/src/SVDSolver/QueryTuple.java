package SVDSolver;

import Query.NELLQuery;

public class QueryTuple implements Comparable{
	public double score;
	public NELLQuery q;
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		
		if(score > ((QueryTuple)arg0).score){
			return 1;
		}else if(score < ((QueryTuple)arg0).score){
			return -1;
		}else
			return 0;
	}

}
