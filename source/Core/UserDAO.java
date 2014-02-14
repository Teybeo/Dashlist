package Core;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;

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
							"WHERE id ='" + id + "';");
			if (res.next())
			{
				String name = res.getString(1);
				HashSet<Board> boards = new HashSet<>();

				res = query.executeQuery("" +
						"SELECT id, name " +
						"FROM board " +
						"WHERE id_user = '"+ id + "';");

				while (res.next())
					boards.add(new Board(res.getInt("id"), res.getString("name")));

				return new User(id, name, boards);
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
}
