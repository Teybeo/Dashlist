package Core;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BoardDAO {

	private Connection link;

	public BoardDAO(Connection link) {

		this.link = link;
	}

	public Board get(int id) {
		try {
			Statement query = link.createStatement();
			ResultSet res = query.executeQuery(
					"SELECT * " +
							"FROM board " +
							"WHERE id ='" + id + "';");
			if (res.next())
			{
				String name = res.getString(1);

				return new Board(id, name);
			}

		} catch (SQLException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}

		return null;
	}
}
