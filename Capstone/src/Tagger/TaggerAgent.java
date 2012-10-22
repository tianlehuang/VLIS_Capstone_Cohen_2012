package Tagger;

import java.io.IOException;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class TaggerAgent {

	private static MaxentTagger tagger;
	
	private static final TaggerAgent instance = new TaggerAgent(); 
	
	private TaggerAgent(){
		
		try{
			if (tagger == null)
				tagger = new MaxentTagger(
						"Jars/stanford-postagger-2012-07-09/models/english-left3words-distsim.tagger");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static TaggerAgent getInstance(){
		
		return instance;
		
	} 
	
	public void test() throws IOException, ClassNotFoundException {

		// Initialize the tagger


		// The sample string

		// String sample =
		// "I want to be a student in carnegie mellon universtiy";

		String sample = "Am i a student?";
		// The tagged string

		String tagged = tagger.tagString(sample);

		// Output the result

		System.out.println(tagged);

	}

	public static String tagLine(String a) {

		String b = tagger.tagString(a);;

//		try {
//			tagger = new MaxentTagger(
//					"Jars/stanford-postagger-2012-07-09/models/english-left3words-distsim.tagger");
//
//			b = tagger.tagString(a);
//
//		} catch (Exception e) {
//
//			e.printStackTrace();
//		}

		return b;

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TaggerAgent ta = new TaggerAgent();

		try {
			ta.test();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
