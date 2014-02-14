package Pack;

import java.sql.*;

public class Core {

	private static String mysql_url = "//localhost";
	private static String mysql_base = "java_base";
	private static String mysql_user = "root";
	private static String mysql_pass = "";
	private Connection connect = null;
	private Statement statement = null;


	public Core() {

		try
		{
			// This will load the MySQL driver
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql:"+mysql_url+"/"+mysql_base, mysql_user, mysql_pass);
			statement = connect.createStatement();

		} catch (Exception e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}

	}

	public int login(String login, String password) {

		try {
			ResultSet res = statement.executeQuery(
				"SELECT id " +
				"FROM user " +
				"WHERE name ='"+login+"'" +
				"AND password ='"+password+"';");

			if (res.next())
				return res.getInt("id");

		} catch (SQLException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}

		return 0;
	}
}
