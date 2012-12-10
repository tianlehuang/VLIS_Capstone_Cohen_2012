package DataBase;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class DBAgent {
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
	
	private static DBAgent instance;
	public DBAgent(String host, String port, String dName, String uName,
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

	public static DBAgent getInstance(){
		
		return instance;
	}
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

	  public ArrayList dbSelect(String selectSql, ArrayList fields)
	    {
	        ArrayList<Map> selectResult = new ArrayList<Map>();
	        Map<String, String> recordInfo;
	        try{
	            selectPro = (Statement) dbConnection.createStatement();
	            dbResultSet = selectPro.executeQuery(selectSql);
	            while(dbResultSet.next()){
	                recordInfo = new HashMap<String, String>();
	                for(int i = 0; i<fields.size(); ++i)
	                    recordInfo.put((String)fields.get(i), dbResultSet.getString((String)fields.get(i)));
	                selectResult.add(recordInfo);
	            }
	            dbResultSet.close(); 
	            selectPro.close(); 
	        }catch(Exception e){
	            
	        	System.out.println("selection failed");
	            System.out.println("Sql = " + selectSql);
	            e.printStackTrace();
	        }
	        return selectResult;
	    }//end dbSelect(...)
	   
	    public boolean dbUpdate(String sql)
	    {
	        try
	        {
	            updatePro = (Statement) dbConnection.createStatement(); 
	            updatePro.executeUpdate(sql);
	            updatePro.close();
	            return true;
	        }catch(Exception err){
	        	System.out.println("update failed");
	            System.out.println("Sql = " + sql);
	            err.printStackTrace();
	            return false;
	        }
	    }//end dbUpdate(...)
	   
	    public boolean closeDatabase()
	    {
	        try{
	            if(dbConnection != null)
	                dbConnection.close();
	            return true;
	        }catch (Exception ex){
	            ex.printStackTrace();
	            return false;
	        }
	    }//end closeDatabase()
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String dbHost = "localhost";
		String dbPort = "3306";
		String dbName = "FrameWork";
		String dbUserName = "root";
		String dbPassword = "";
		DbConnection conn = new DbConnection(dbHost, dbPort, dbName,
				dbUserName, dbPassword);
		boolean bool = conn.dbConnection();
		if (!bool)
			return;
		
		String createatableForTwitts = "create table User_Twitts "
				+ "(User_Name varchar(32) NOT NULL, "
				+ "Twitt varchar(32) NOT NULL)";
		conn.dbUpdate(createatableForTwitts);

		createatableForTwitts = "create table Freebase_Types "
				+ "(Word varchar(32) NOT NULL, "
				+ "Type varchar(32) NOT NULL)";
		conn.dbUpdate(createatableForTwitts);
		
		createatableForTwitts = "create table User_Topics "
				+ "(User_Name varchar(32) NOT NULL, "
				+ "Topic varchar(32) NOT NULL)";
		conn.dbUpdate(createatableForTwitts);
		
		

	}

}
