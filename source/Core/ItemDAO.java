package Core;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;

public class ItemDAO {

	private Connection link;


	public ItemDAO(Connection link) {
		this.link = link;
	}

	public HashSet<Item> getListItems(int list_id) {

		HashSet<Item> items = new HashSet<>();

		try {
			Statement query = link.createStatement();

			ResultSet res = query.executeQuery(
					"SELECT * " +
					"FROM item " +
					"WHERE id_list='" + list_id + "';"
			);

			while (res.next()) {

				items.add(new Item(res.getDate("date"), res.getString("name"), res.getInt("position"), res.getString("description")));
			}

		} catch (SQLException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}

		return items;
	}
}
