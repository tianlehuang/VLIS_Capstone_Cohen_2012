package SVDSolver;

public class UserTuple implements Comparable{
	
	public double score;
	public String name;
	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		
		if(score > ((UserTuple)arg0).score){
			return 1;
		}else if(score < ((UserTuple)arg0).score){
			return -1;
		}else
			return 0;
	}

}
