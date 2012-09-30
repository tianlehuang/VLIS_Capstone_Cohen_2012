package Query;

import java.util.List;


import User.NELLUser;

public class NELLQuery {

	public String query;

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

}
