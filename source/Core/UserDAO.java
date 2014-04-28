package Core;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDAO {

	private Connection link;

	public UserDAO(Connection link) {

		this.link = link;
	}

	public User get(int id) {

		try {

			Statement query = link.createStatement();
			ResultSet res = query.executeQuery(
					"SELECT name " +
					"FROM user " +
					"WHERE id ='" + id + "';"
			);

			if (res.next()) {

				String name = res.getString(1);
				return new User(id, name);
			}

		} catch (SQLException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}

		return null;
	}

	public User login(String login, String password) {

		try {
			Statement query = link.createStatement();
			ResultSet res = query.executeQuery(
					"SELECT id " +
							"FROM user " +
							"WHERE name ='" + login + "'" +
							"AND password ='" + password + "';");

			if (res.next())
				return get(res.getInt("id"));

		} catch (SQLException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}

		return null;
	}

	public boolean isAdminForBoard(int user_id, int board_id) {

		boolean is_admin = false;

		try {
			Statement query = link.createStatement();
			ResultSet res = query.executeQuery(
					"SELECT is_admin " +
					"FROM board_members " +
					"WHERE user_id = '" + user_id + "' " +
					"AND board_id = '"+ board_id +"';");


			if (res.next())
				is_admin = res.getBoolean("is_admin");

		} catch (SQLException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
		return is_admin;
	}
}
