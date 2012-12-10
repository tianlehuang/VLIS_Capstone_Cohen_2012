package SVDSolver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import User.NELLUser;

import com.aliasi.matrix.SvdMatrix;

public class SVDSolver {

	private static SVDSolver instance = new SVDSolver();

	private SVDSolver() {

		System.out.println("start a SVDSolver");
	}

	public SVDSolver getInstance() {
		return instance;
	}

	public static Result analyze(String[] topics) {
		List<String> nullUsers = new ArrayList<String>();
		File a = new File("NULLUsers/nulluserlist");
		try {
			BufferedReader br = new BufferedReader(new FileReader(a));
			while(true){

				String c = br.readLine();
				if(c == null){
					break;
				}else{
					nullUsers.add(c);
				}
				
			}
			
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		System.out.println("the number of null user is " + nullUsers.size());

		Result r = new Result();
		Map<String, List<String>> userSet = new HashMap<String, List<String>>();
		//key is the name of the user and value is a set of topics the user is interested in 
		String dir = "UserTopics/";
		File folder = new File(dir);
		String content = "";

		for (File f : folder.listFiles()) {
			// System.out.println(f.getName()+",");
			List<String> ts = new ArrayList<String>();

			
			if(nullUsers.contains(f.getName())){
				continue;
			}

			try {
				BufferedReader br = new BufferedReader(new FileReader(f));
				while (true) {
					content = br.readLine();
					if (content == null) {
						break;
					} else {
						ts.add(content);
					}

				}

				br.close();
				userSet.put(f.getName(), ts);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		int numOfTopic = topics.length;
		int numOfUser = userSet.size();

		double[][] topicUserMatrix = new double[numOfTopic][numOfUser];

		String[] users = new String[numOfUser];
		
		Map<Integer, String> userIdToName = new HashMap<Integer, String>();
		Map<Integer, String> typeIdToName = new HashMap<Integer, String>();
		int i = 0;
		for (Entry<String, List<String>> e : userSet.entrySet()) {
			
			users[i] = e.getKey();	//name of the user
			
			
			userIdToName.put(i, users[i]);
			
			for (int j = 0; j < numOfTopic; j++) {
				if(!typeIdToName.containsKey(j)){
					typeIdToName.put(j, topics[j]);
				}
				
				if (e.getValue().contains(topics[j])) {
					topicUserMatrix[j][i] = 1;
				} else {
					topicUserMatrix[j][i] = 0;
				}
			}
			i++;
			
		}
		
		r.userIdToName = userIdToName;
		r.typeIdToName = typeIdToName;

//		for (i = 0; i < numOfUser; i++) {
//			NELLUser cu = null;// userSet.get(i);
//			users[i] = cu.getName();
//
//			for (int j = 0; j < numOfTopic; j++) {
//				if (cu.getTopics().contains(topics[i])) {
//					topicUserMatrix[j][i] = 1;
//				} else {
//					topicUserMatrix[j][i] = 0;
//				}
//			}
//		}
		int NUM_FACTORS = 20;

		double featureInit = 0.01;
		double initialLearningRate = 0.005;
		int annealingRate = 1000;
		double regularization = 0.00;
		double minImprovement = 0.0000;
		int minEpochs = 10;
		//int maxEpochs = 50000;
		int maxEpochs = 1000;

		System.out.println("  Computing SVD");
		System.out.println("    maxFactors=" + NUM_FACTORS);
		System.out.println("    featureInit=" + featureInit);
		System.out.println("    initialLearningRate=" + initialLearningRate);
		System.out.println("    annealingRate=" + annealingRate);
		System.out.println("    regularization" + regularization);
		System.out.println("    minImprovement=" + minImprovement);
		System.out.println("    minEpochs=" + minEpochs);
		System.out.println("    maxEpochs=" + maxEpochs);

		
		System.out.println("# of topics is " + topicUserMatrix.length);
		System.out.println("# of users is " + topicUserMatrix[0].length);
		
		SvdMatrix matrix = SvdMatrix.svd(topicUserMatrix, NUM_FACTORS,
				featureInit, initialLearningRate, annealingRate,
				regularization, null, minImprovement, minEpochs, maxEpochs);

		double[] scales = matrix.singularValues();
		double[][] termVectors = matrix.leftSingularVectors();
		double[][] docVectors = matrix.rightSingularVectors();

		System.out.println("\nSCALES");
		for (int k = 0; k < NUM_FACTORS; ++k)
			System.out.printf("%d  %4.2f\n", k, scales[k]);

		System.out.println("\ntopic VECTORS");
		for (i = 0; i < termVectors.length; ++i) {
			System.out.print("(");
			for (int k = 0; k < NUM_FACTORS; ++k) {
				if (k > 0)
					System.out.print(", ");
				System.out.printf("% 5.2f", termVectors[i][k]);
			}
			System.out.print(")  ");
			System.out.println(topics[i]);
		}

		System.out.println("\nuser VECTORS");
		for (int j = 0; j < docVectors.length; ++j) {
			System.out.print("(");
			for (int k = 0; k < NUM_FACTORS; ++k) {
				if (k > 0)
					System.out.print(", ");
				System.out.printf("% 5.2f", docVectors[j][k]);
			}
			System.out.print(")  ");
			System.out.println(users[j]);
		}
		
		r.topicVectors = termVectors;
		r.userVectors = docVectors;
		r.topics = topics;
		return r;

	}
	
	public static Result analyze_exp2(String[] topics, List<NELLUser> biguserSet) {
		List<String> nullUsers = new ArrayList<String>();
		File a = new File("NULLUsers/nulluserlist");
		try {
			BufferedReader br = new BufferedReader(new FileReader(a));
			while(true){

				String c = br.readLine();
				if(c == null){
					break;
				}else{
					nullUsers.add(c);
				}
				
			}
			
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		System.out.println("the number of null user is " + nullUsers.size());
		
		List<String> e1aUsers = new ArrayList<String>();
		String b = "Exp1A/";
		File fb = new File(b);
		for (File f : fb.listFiles()) {
			e1aUsers.add(f.getName());
		}
		System.out.println("the total number of user in e1a is " + e1aUsers.size());

		List<String> e1bUsers = new ArrayList<String>();
		String c = "Exp1B/";
		File fc = new File(c);
		for (File f : fc.listFiles()) {
			e1bUsers.add(f.getName());
		}
		System.out.println("the total number of user in e1b is " + e1bUsers.size());


		Result r = new Result();
		Map<String, List<String>> userSet = new HashMap<String, List<String>>();
		//key is the name of the user and value is a set of topics the user is interested in 
		String dir = "UserTopics/";
		File folder = new File(dir);
		String content = "";

		//for (File f : folder.listFiles()) {
		for(int kk=0; kk<biguserSet.size(); kk++){
			String name = biguserSet.get(kk).name;
			// System.out.println(f.getName()+",");
			List<String> ts = new ArrayList<String>();

			
//			if(nullUsers.contains(name) || e1bUsers.contains(name) || e1aUsers.contains(f.getName())){
//				continue;
//			}

			try {
				BufferedReader br = new BufferedReader(new FileReader(new File(dir+name)));
				while (true) {
					content = br.readLine();
					if (content == null) {
						break;
					} else {
						ts.add(content);
					}

				}

				br.close();
				userSet.put(name, ts);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		int numOfTopic = topics.length;
		int numOfUser = userSet.size();

		double[][] topicUserMatrix = new double[numOfTopic][numOfUser];

		String[] users = new String[numOfUser];
		
		Map<Integer, String> userIdToName = new HashMap<Integer, String>();
		Map<Integer, String> typeIdToName = new HashMap<Integer, String>();
		int i = 0;
		for (Entry<String, List<String>> e : userSet.entrySet()) {
			
			users[i] = e.getKey();	//name of the user
			
			
			userIdToName.put(i, users[i]);
			
			for (int j = 0; j < numOfTopic; j++) {
				if(!typeIdToName.containsKey(j)){
					typeIdToName.put(j, topics[j]);
				}
				
				if (e.getValue().contains(topics[j])) {
					topicUserMatrix[j][i] = 1;
				} else {
					topicUserMatrix[j][i] = 0;
				}
			}
			i++;
			
		}
		
		r.userIdToName = userIdToName;
		r.typeIdToName = typeIdToName;

//		for (i = 0; i < numOfUser; i++) {
//			NELLUser cu = null;// userSet.get(i);
//			users[i] = cu.getName();
//
//			for (int j = 0; j < numOfTopic; j++) {
//				if (cu.getTopics().contains(topics[i])) {
//					topicUserMatrix[j][i] = 1;
//				} else {
//					topicUserMatrix[j][i] = 0;
//				}
//			}
//		}
		int NUM_FACTORS = 20;

		double featureInit = 0.01;
		double initialLearningRate = 0.005;
		int annealingRate = 1000;
		double regularization = 0.00;
		double minImprovement = 0.0000;
		int minEpochs = 10;
		//int maxEpochs = 50000;
		int maxEpochs = 5000;

		System.out.println("  Computing SVD");
		System.out.println("    maxFactors=" + NUM_FACTORS);
		System.out.println("    featureInit=" + featureInit);
		System.out.println("    initialLearningRate=" + initialLearningRate);
		System.out.println("    annealingRate=" + annealingRate);
		System.out.println("    regularization" + regularization);
		System.out.println("    minImprovement=" + minImprovement);
		System.out.println("    minEpochs=" + minEpochs);
		System.out.println("    maxEpochs=" + maxEpochs);

		
		System.out.println("# of topics is " + topicUserMatrix.length);
		System.out.println("# of users is " + topicUserMatrix[0].length);
		
		SvdMatrix matrix = SvdMatrix.svd(topicUserMatrix, NUM_FACTORS,
				featureInit, initialLearningRate, annealingRate,
				regularization, null, minImprovement, minEpochs, maxEpochs);

		double[] scales = matrix.singularValues();
		double[][] termVectors = matrix.leftSingularVectors();
		double[][] docVectors = matrix.rightSingularVectors();

		System.out.println("\nSCALES");
		for (int k = 0; k < NUM_FACTORS; ++k)
			System.out.printf("%d  %4.2f\n", k, scales[k]);

		System.out.println("\ntopic VECTORS");
		for (i = 0; i < termVectors.length; ++i) {
			System.out.print("(");
			for (int k = 0; k < NUM_FACTORS; ++k) {
				if (k > 0)
					System.out.print(", ");
				System.out.printf("% 5.2f", termVectors[i][k]);
			}
			System.out.print(")  ");
			System.out.println(topics[i]);
		}

		System.out.println("\nuser VECTORS");
		for (int j = 0; j < docVectors.length; ++j) {
			System.out.print("(");
			for (int k = 0; k < NUM_FACTORS; ++k) {
				if (k > 0)
					System.out.print(", ");
				System.out.printf("% 5.2f", docVectors[j][k]);
			}
			System.out.print(")  ");
			System.out.println(users[j]);
		}
		
		r.topicVectors = termVectors;
		r.userVectors = docVectors;
		r.topics = topics;
		return r;

	}

	public static void analyze1(List<NELLUser> userSet, String[] topics) {

		String[] TERMS = new String[] { "human", "interface", "computer",
				"user", "system", "response", "time", "EPS", "survey", "trees",
				"graph", "minors" };

		String[] DOCS = new String[] {
				"Human machine interface for Lab ABC computer applications",
				"A survey of user opinion of computer system response time",
				"The EPS user interface management system",
				"System and human system engineering testing of EPS",
				"Relation of user-perceived response time to error measurement",
				"The generation of random, binary, unordered trees",
				"The intersection graph of paths in trees",
				"Graph minors IV: Widths of trees and well-quasi-ordering",
				"Graph minors: A survey" };

		double[][] TERM_DOCUMENT_MATRIX = new double[][] {
				{ 1, 0, 0, 1, 0, 0, 0, 0, 0 }, { 1, 0, 1, 0, 0, 0, 0, 0, 0 },
				{ 1, 1, 0, 0, 0, 0, 0, 0, 0 }, { 0, 1, 1, 0, 1, 0, 0, 0, 0 },
				{ 0, 1, 1, 2, 0, 0, 0, 0, 0 }, { 0, 1, 0, 0, 1, 0, 0, 0, 0 },
				{ 0, 1, 0, 0, 1, 0, 0, 0, 0 }, { 0, 0, 1, 1, 0, 0, 0, 0, 0 },
				{ 0, 1, 0, 0, 0, 0, 0, 0, 1 }, { 0, 0, 0, 0, 0, 1, 1, 1, 0 },
				{ 0, 0, 0, 0, 0, 0, 1, 1, 1 }, { 0, 0, 0, 0, 0, 0, 0, 1, 1 } };

		int NUM_FACTORS = 2;

		double featureInit = 0.01;
		double initialLearningRate = 0.005;
		int annealingRate = 1000;
		double regularization = 0.00;
		double minImprovement = 0.0000;
		int minEpochs = 10;
		int maxEpochs = 50000;

		System.out.println("  Computing SVD");
		System.out.println("    maxFactors=" + NUM_FACTORS);
		System.out.println("    featureInit=" + featureInit);
		System.out.println("    initialLearningRate=" + initialLearningRate);
		System.out.println("    annealingRate=" + annealingRate);
		System.out.println("    regularization" + regularization);
		System.out.println("    minImprovement=" + minImprovement);
		System.out.println("    minEpochs=" + minEpochs);
		System.out.println("    maxEpochs=" + maxEpochs);

		SvdMatrix matrix = SvdMatrix.svd(TERM_DOCUMENT_MATRIX, NUM_FACTORS,
				featureInit, initialLearningRate, annealingRate,
				regularization, null, minImprovement, minEpochs, maxEpochs);

		double[] scales = matrix.singularValues();
		double[][] termVectors = matrix.leftSingularVectors();
		double[][] docVectors = matrix.rightSingularVectors();

		System.out.println("\nSCALES");
		for (int k = 0; k < NUM_FACTORS; ++k)
			System.out.printf("%d  %4.2f\n", k, scales[k]);

		System.out.println("\nTERM VECTORS");
		for (int i = 0; i < termVectors.length; ++i) {
			System.out.print("(");
			for (int k = 0; k < NUM_FACTORS; ++k) {
				if (k > 0)
					System.out.print(", ");
				System.out.printf("% 5.2f", termVectors[i][k]);
			}
			System.out.print(")  ");
			System.out.println(TERMS[i]);
		}

		System.out.println("\nDOC VECTORS");
		for (int j = 0; j < docVectors.length; ++j) {
			System.out.print("(");
			for (int k = 0; k < NUM_FACTORS; ++k) {
				if (k > 0)
					System.out.print(", ");
				System.out.printf("% 5.2f", docVectors[j][k]);
			}
			System.out.print(")  ");
			System.out.println(DOCS[j]);
		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		SVDSolver.analyze(null);
	}

}
