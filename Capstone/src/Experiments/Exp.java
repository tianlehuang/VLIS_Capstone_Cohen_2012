package Experiments;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import twitter4j.TwitterException;
import Twitter.TwitterAgent;

public class Exp {
	
	public void stat() {

		String dir = "Tweets/";
		File folder = new File(dir);
		String content = "";

		List<String> nuUser = new ArrayList<String>();
		Map<Integer, Integer> co = new HashMap<Integer, Integer>();
		int max = -1;
		// List<String> ts = new ArrayList<String>();
		int total = 0;
		for (File f : folder.listFiles()) {
			// System.out.println(f.getName()+",");

			total++;
			int time = 0;

			try {
				BufferedReader br = new BufferedReader(new FileReader(f));
				while (true) {
					content = br.readLine();
					if (content == null) {
						break;
					} else {
						// ts.add(content);
						time++;
					}
				}
				br.close();

				if (co.containsKey(time)) {
					co.put(time, co.get(time) + 1);
				} else {
					co.put(time, 1);
				}
				if (time > max) {
					max = time;
				}

				if (time == 0) {
					nuUser.add(f.getName());
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		System.out.println("the total number of followers is " + total);
		int has = 0;
		int[] ss = new int[16];

		Arrays.fill(ss, 0);
		for (int i = max; i > 0; i--) {
			if (co.containsKey(i)) {
				System.out.println(co.get(i) + " users have " + i + " tweets");
				has += co.get(i);

				ss[i / 10] += co.get(i);

			}
		}

		for (int i = 0; i < ss.length; i++) {
			System.out.println(ss[i]);
		}

		System.out.println(has + " users has at least 1 tweet ");
		System.out.println((total - has) + " users has no tweet ");

		System.out.println("nu user is " + nuUser.size());

		// File f = new File("NULLUsers/nulluserlist");
		// try {
		// BufferedWriter bw = new BufferedWriter(new FileWriter(f));
		// for (int i = 0; i < nuUser.size(); i++) {
		// bw.append(nuUser.get(i) + "\n");
		// }
		//
		// bw.close();
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

	}

	public void exp1() {

		List<String> nullUsers = new ArrayList<String>();
		File a = new File("NULLUsers/nulluserlist");
		try {
			BufferedReader br = new BufferedReader(new FileReader(a));
			while (true) {

				String c = br.readLine();
				if (c == null) {
					break;
				} else {
					nullUsers.add(c);
				}

			}

			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("the number of null user is " + nullUsers.size());

		List<String> allUsers = new ArrayList<String>();
		String b = "Tweets/";
		File fb = new File(b);
		for (File f : fb.listFiles()) {
			allUsers.add(f.getName());
		}
		System.out.println("the total number of user is " + allUsers.size());

		List<String> allOutput = new ArrayList<String>();
		String c = "Assignments/";
		File fc = new File(c);
		for (File f : fc.listFiles()) {
			allOutput.add(f.getName());
		}
		System.out.println("the total number of output user is "
				+ allOutput.size());

		// permutate allOutput
		Collections.shuffle(allOutput);
		List<String> usersIne1A = new ArrayList<String>();
		List<String> queriesIne1A = new ArrayList<String>();
		int t = 113;
		int ac = 0;

		String u1 = "Exp1A/";
		for (int i = 0; i < allOutput.size(); i++) {
			if (ac >= t) {
				break;
			}
			String u = allOutput.get(i);
			usersIne1A.add(u);
			String q = "";
			// read its content
			try {
				BufferedReader br = new BufferedReader(new FileReader(new File(
						c + u)));
				q = br.readLine();
				queriesIne1A.add(q);
				br.close();

			} catch (Exception e) {
				e.printStackTrace();
			}

			// write the query to exp1a
			try {

				BufferedWriter bw = new BufferedWriter(new FileWriter(u1 + u));
				bw.append(q);
				bw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			ac++;

		}

		// now we have put all the users and queries in exp1a
		// next we are dealing those in exp1b
		List<String> usersIne1B = new ArrayList<String>();
		Collections.shuffle(allUsers);
		Collections.shuffle(queriesIne1A);
		int ab = 0;
		String u2 = "Exp1B/";

		for (int i = 0; i < allUsers.size(); i++) {
			if (ab >= t) {
				break;
			}

			String u = allUsers.get(i);
			if (nullUsers.contains(u) || usersIne1A.contains(u)) {
				continue;
			}
			String q = queriesIne1A.get(ab);
			ab++;

			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(u2 + u));
				bw.append(q);
				bw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	public void exp2() {
		List<String> nullUsers = new ArrayList<String>();
		File a = new File("NULLUsers/nulluserlist");
		try {
			BufferedReader br = new BufferedReader(new FileReader(a));
			while (true) {

				String c = br.readLine();
				if (c == null) {
					break;
				} else {
					nullUsers.add(c);
				}

			}

			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("the number of null user is " + nullUsers.size());

		List<String> allUsers = new ArrayList<String>();
		String b = "Tweets/";
		File fb = new File(b);
		for (File f : fb.listFiles()) {
			allUsers.add(f.getName());
		}
		System.out.println("the total number of user is " + allUsers.size());

		List<String> e1aUsers = new ArrayList<String>();
		String c = "Exp1A/";
		File fc = new File(c);
		for (File f : fc.listFiles()) {
			e1aUsers.add(f.getName());
		}
		System.out.println("the total number of user in ea1 is "
				+ e1aUsers.size());

		List<String> e1bUsers = new ArrayList<String>();
		String d = "Exp1B/";
		File fd = new File(d);
		for (File f : fd.listFiles()) {
			e1bUsers.add(f.getName());
		}
		System.out.println("the total number of user in eb1 is "
				+ e1bUsers.size());

		// load all info in Assignments2
		List<String> allOutput = new ArrayList<String>();
		String e = "Assignments2/";
		File fe = new File(e);

		String g = "Exp2A/";

		List<String> queries = new ArrayList<String>();
		for (File f : fe.listFiles()) {
			String u = f.getName();
			String q = "";
			try {
				BufferedReader br = new BufferedReader(new FileReader(f));
				q = br.readLine();
				queries.add(q);
				br.close();

			} catch (Exception ee) {
				ee.printStackTrace();
			}

			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(g + u));
				bw.append(q);
				bw.close();
			} catch (Exception ee) {
				ee.printStackTrace();
			}

			allOutput.add(u);
		}
		System.out.println("the total number of output user is "
				+ allOutput.size());

		// next we will redistribute these queries to exp2b
		// find 187 users first
		int t = 113;
		int cou = 0;
		Collections.shuffle(queries);
		String h = "Exp2B/";

		for (int i = 0; i < allUsers.size() && cou < queries.size(); i++) {
			if (cou >= t) {
				break;
			}
			String u = allUsers.get(i);
			if (nullUsers.contains(u) || allOutput.contains(u)
					|| e1bUsers.contains(u) || e1aUsers.contains(u)) {
				continue;
			}

			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(h + u));
				bw.append(queries.get(cou));
				cou++;
				bw.close();
			} catch (Exception ee) {
				ee.printStackTrace();
			}

		}

	}

	public void broadCast(String dir) {

		int amount = 28;
		TwitterAgent ta = TwitterAgent.getInstance();

		File folder = new File(dir);
		String content = "";

		int count = 0;
		for (File f : folder.listFiles()) {

			System.out.println("this is count " + count);
			if (count < amount) {
				count++;
				continue;
			}

			if (count >= 2 * amount) {
				break;
			}
			count++;

			String name = f.getName();

			String q = "";
			try {
				BufferedReader br = new BufferedReader(new FileReader(f));

				q = br.readLine();

				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

			String msg = "Hi this is NELL 4.Can you answer a question for me: does "
					+ q + "?Yes or no?";
			try {
				ta.directMessage(name, msg);
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("user " + name + " gets " + msg);

		}

	}

	public void collect() {

		TwitterAgent ta = TwitterAgent.getInstance();

		try {
			// ta.directMessage(name, msg);
			ta.getDirect();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println("user " + name + " gets " + msg);

	}

	public void follow() {

		String e = "Exp2A";
		File fe = new File(e);

		TwitterAgent ta = TwitterAgent.getInstance();
		int count = 0;
		int amount = 28;
		for (File f : fe.listFiles()) {
			if (count < amount) {
				count++;
				continue;
			}
			// if(count>=amount){
			// break;
			// }
			String u = f.getName();
			String q = "";
			System.out.println("this is " + count + ", follow user " + u);
			count++;
			try {
				ta.followUser(u);
			} catch (TwitterException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

	}

	public void analyze1() {

		int amount = 28;
		List<String> usersIn1A = new ArrayList<String>();

		String a = "Exp1A";
		// take first 28 users in this folder
		File af = new File(a);
		int count = 0;
		for (File f : af.listFiles()) {
			if (count >= amount) {
				break;
			}
			usersIn1A.add(f.getName());
			count++;
		}

		List<String> usersIn1B = new ArrayList<String>();
		a = "Exp1B";
		// take first 28 users in this folder
		af = new File(a);
		count = 0;
		for (File f : af.listFiles()) {
			if (count >= amount) {
				break;
			}
			usersIn1B.add(f.getName());
			count++;
		}

		List<String> usersIn2A = new ArrayList<String>();
		a = "Exp2A";
		// take first 28 users in this folder
		af = new File(a);
		count = 0;
		for (File f : af.listFiles()) {
			if (count >= amount) {
				break;
			}
			usersIn2A.add(f.getName());
			count++;
		}

		List<String> usersIn2B = new ArrayList<String>();
		a = "Exp2B";
		// take first 28 users in this folder
		af = new File(a);
		count = 0;
		for (File f : af.listFiles()) {
			if (count >= amount) {
				break;
			}
			usersIn2B.add(f.getName());
			count++;
		}

		String reDir = "Results/";

		File reFolder = new File(reDir);

		int p = 0;
		int q = 0;
		int r = 0;
		int s = 0;

		for (File f : reFolder.listFiles()) {
			String user = f.getName();

			if (usersIn1A.contains(user)) {
				p++;
			}else if (usersIn1B.contains(user)) {
				q++;
			}else if (usersIn2A.contains(user)) {
				r++;
			}else if (usersIn2B.contains(user)) {
				s++;
			}

		}

		System.out.println("For exp1A, " + (p * 100.0 / amount)
				+ "% of users replied ");
		System.out.println("For exp1B, " + (q * 100.0 / amount)
				+ "% of users replied ");
		System.out.println("For exp2A, " + (r * 100.0 / amount)
				+ "% of users replied ");
		System.out.println("For exp2B, " + (s * 100.0 / amount)
				+ "% of users replied ");

	}

	public void analyze2() {

		int amount = 28;
		List<String> usersIn1A = new ArrayList<String>();

		String a = "Exp1A";
		// take first 28 users in this folder
		File af = new File(a);
		int count = 0;
		for (File f : af.listFiles()) {
			if(count < amount){
				count++;
				continue;
			}
			if (count >= 2*amount) {
				break;
			}
			usersIn1A.add(f.getName());
			count++;
		}

		List<String> usersIn1B = new ArrayList<String>();
		a = "Exp1B";
		// take first 28 users in this folder
		af = new File(a);
		count = 0;
		for (File f : af.listFiles()) {
			if(count < amount){
				count++;
				continue;
			}
			if (count >= 2*amount) {
				break;
			}
			usersIn1B.add(f.getName());
			count++;
		}

		List<String> usersIn2A = new ArrayList<String>();
		a = "Exp2A";
		// take first 28 users in this folder
		af = new File(a);
		count = 0;
		for (File f : af.listFiles()) {
			if(count < amount){
				count++;
				continue;
			}
			if (count >= 2*amount) {
				break;
			}
			usersIn2A.add(f.getName());
			count++;
		}

		List<String> usersIn2B = new ArrayList<String>();
		a = "Exp2B";
		// take first 28 users in this folder
		af = new File(a);
		count = 0;
		for (File f : af.listFiles()) {
			if(count < amount){
				count++;
				continue;
			}
			if (count >= 2*amount) {
				break;
			}
			usersIn2B.add(f.getName());
			count++;
		}

		String reDir = "Results/";

		File reFolder = new File(reDir);

		int p = 0;
		int q = 0;
		int r = 0;
		int s = 0;

		for (File f : reFolder.listFiles()) {
			String user = f.getName();

			if (usersIn1A.contains(user)) {
				p++;
			}
			if (usersIn1B.contains(user)) {
				q++;
			}
			if (usersIn2A.contains(user)) {
				r++;
			}
			if (usersIn2B.contains(user)) {
				s++;
			}

		}

		System.out.println("For exp1A, " + (p * 100.0 / amount)
				+ "% of users replied ");
		System.out.println("For exp1B, " + (q * 100.0 / amount)
				+ "% of users replied ");
		System.out.println("For exp2A, " + (r * 100.0 / amount)
				+ "% of users replied ");
		System.out.println("For exp2B, " + (s * 100.0 / amount)
				+ "% of users replied ");

	}


}
