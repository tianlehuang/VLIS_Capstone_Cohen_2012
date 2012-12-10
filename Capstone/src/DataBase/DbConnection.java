package DataBase;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class DbConnection {
	private Connection dbConnection = null;
	private Statement selectPro = null;
	private Statement updatePro = null;
	private ResultSet dbResultSet = null;

	private String driverName;
	private String dbHost;
	private String dbPort;
	private String dbName;
	private String dbUserName;
	private String dbPassword;
	private String enCoding;

	public DbConnection(String host, String port, String dName, String uName,
			String password) {
		driverName = "com.mysql.jdbc.Driver";
		dbHost = host;
		dbPort = port;
		dbName = dName;
		dbUserName = uName;
		dbPassword = password;
		// enCoding =
		// "?useUnicode=true&characterEncoding=gb2312&autoReconnect=true";
	}// end DbConnection(...)

	public boolean dbConnection() {
		StringBuilder urlTem = new StringBuilder();
		urlTem.append("jdbc:mysql://");
		urlTem.append(dbHost);
		urlTem.append(":");
		urlTem.append(dbPort);
		urlTem.append("/");
		urlTem.append(dbName);
		// urlTem.append(enCoding);
		String url = urlTem.toString();
		try {
			Class.forName(driverName).newInstance();
			dbConnection = (Connection) DriverManager.getConnection(url,
					dbUserName, dbPassword);
			return true;
		} catch (Exception e) {

			System.err.println("connection failed!");
			System.out.println("url = " + url);
			e.printStackTrace();
			return false;
		}
	}// end dbConnection()

	public ArrayList dbSelect(String selectSql, ArrayList fields) {
		ArrayList<Map> selectResult = new ArrayList<Map>();
		Map<String, String> recordInfo;
		try {
			selectPro = (Statement) dbConnection.createStatement();
			dbResultSet = selectPro.executeQuery(selectSql);
			while (dbResultSet.next()) {
				recordInfo = new HashMap<String, String>();
				for (int i = 0; i < fields.size(); ++i)
					recordInfo.put((String) fields.get(i),
							dbResultSet.getString((String) fields.get(i)));
				selectResult.add(recordInfo);
			}
			dbResultSet.close();
			selectPro.close();
		} catch (Exception e) {

			System.out.println("selection failed");
			System.out.println("Sql = " + selectSql);
			e.printStackTrace();
		}
		return selectResult;
	}// end dbSelect(...)

	public boolean dbUpdate(String sql) {
		try {
			updatePro = (Statement) dbConnection.createStatement();
			updatePro.executeUpdate(sql);
			updatePro.close();
			return true;
		} catch (Exception err) {
			System.out.println("update failed");
			System.out.println("Sql = " + sql);
			err.printStackTrace();
			return false;
		}
	}// end dbUpdate(...)

	public boolean closeDatabase() {
		try {
			if (dbConnection != null)
				dbConnection.close();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}// end closeDatabase()

	public static void main(String[] args) {
	
		// String selectSql =
		// "select COF_NAME, PRICE from COFFEES where PRICE = 8.99";
		String selectSql = "select * from COFFEES";
		ArrayList<String> fieldsList = new ArrayList<String>();
		fieldsList.add("COF_NAME");
		fieldsList.add("PRICE");
		fieldsList.add("SUP_ID");
		fieldsList.add("TOTAL");
		fieldsList.add("SALES");

		DbConnection conn = null;
		ArrayList<Map> userInfoList = conn.dbSelect(selectSql, fieldsList);
		int infoSize = userInfoList.size();
		String COF_NAME;
		String PRICE;
		if (infoSize == 0)
			System.out.println("no such data");
		else {
			for (int i = 0; i < infoSize; ++i) {
				// COF_NAME = (String)userInfoList.get(i).get("COF_NAME");
				// PRICE = (String)(((Map)userInfoList.get(i)).get("PRICE"));
				// System.out.println("COF_NAME = " + COF_NAME + "  PRICE = " +
				// PRICE);
				// System.out.println();
				for (int j = 0; j < fieldsList.size(); j++) {
					String curr = fieldsList.get(j);
					System.out.print(curr + ": "
							+ userInfoList.get(i).get(curr) + ",");

				}
				System.out.println();
			}
		}

		String insert = "insert into COFFEES "
				+ "values('Cool', 00201, 17.99, 20, 19)";

		conn.dbUpdate(insert);

		System.out.println("***************************");
		userInfoList = conn.dbSelect(selectSql, fieldsList);
		infoSize = userInfoList.size();
		// String COF_NAME;
		// String PRICE;
		if (infoSize == 0)
			System.out.println("no such data");
		else {
			for (int i = 0; i < infoSize; ++i) {
				// COF_NAME = (String)userInfoList.get(i).get("COF_NAME");
				// PRICE = (String)(((Map)userInfoList.get(i)).get("PRICE"));
				// System.out.println("COF_NAME = " + COF_NAME + "  PRICE = " +
				// PRICE);
				// System.out.println();
				for (int j = 0; j < fieldsList.size(); j++) {
					String curr = fieldsList.get(j);
					System.out.print(curr + ": "
							+ userInfoList.get(i).get(curr) + ",");

				}
				System.out.println();
			}
		}
		String createatable = "create table Cars "
				+ "(Car_NAME varchar(32) NOT NULL, " + "car_ID int NOT NULL, "
				+ "PRICE numeric(10,2) NOT NULL, " + "SALES integer NOT NULL, "
				+ "TOTAL integer NOT NULL, " + "PRIMARY KEY (Car_NAME))";

		conn.dbUpdate(createatable);
		conn.closeDatabase();
	}// end main(...)

}// end calss DbConnection