package Core;

import java.sql.*;

public class BddConnection {

	private static String mysql_url = "//localhost";
	private static String mysql_base = "dashlist";
	private static String mysql_user = "root";
	private static String mysql_pass = "";
	private Connection connect = null;

	private static Connection link = null;

	public static Connection getInstance() {

		if (link == null)
			new BddConnection();

		return link;
	}

	private BddConnection() {
		try
		{
			// This will load the MySQL driver
			Class.forName("com.mysql.jdbc.Driver");
			link = DriverManager.getConnection("jdbc:mysql:"+mysql_url+"/"+mysql_base, mysql_user, mysql_pass);

		} catch (Exception e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}

	}

}
