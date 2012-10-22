package Tools;

public class Pair implements Comparable {

	public String word;

	public int count;

	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		Pair p = (Pair) arg0;

		if (count < p.count) {
			return -1;
		} else if (count > p.count) {
			return 1;
		} else {
			return 0;
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
