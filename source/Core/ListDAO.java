package Core;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ListDAO {

	private Connection link;

	public ListDAO(Connection link) {

		this.link = link;
	}

	public ArrayList<List> getBoardLists(int board_id) {

		ArrayList<List> lists = new ArrayList<>();

		try {
			Statement query = link.createStatement();

			ResultSet res = query.executeQuery(
					"SELECT * " +
							"FROM list " +
							"WHERE id_board='" + board_id + "';"
			);

			while (res.next()) {

				int list_id = res.getInt("id");

				ItemDAO dao = new ItemDAO(BddConnection.getInstance());

				lists.add(new List(dao.getListItems(list_id), res.getString("name"), res.getInt("position")));

			}

		} catch (SQLException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}

		return lists;
	}


	public void loadItems(List list) {

		ItemDAO dao = new ItemDAO(BddConnection.getInstance());
		list.setItems(dao.getListItems(list.getId()));

	}

}
