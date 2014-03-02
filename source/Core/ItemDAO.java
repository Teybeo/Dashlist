package Core;

import java.sql.*;
import java.util.*;

public class ItemDAO {

	private Connection link;


	public ItemDAO(Connection link) {
		this.link = link;
	}

	public void add(Item item, int list_id) {

		try {
			Statement query = link.createStatement();

			query.execute("" +
					"INSERT INTO item " +
					"VALUES(default, "+list_id+",'"+item.getName()+"', CURDATE(), NULL, "+item.getPosition()+", NULL);");

			} catch (SQLException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}

	public ArrayList<Item> getListItems(int list_id) {

		ArrayList<Item> items = new ArrayList<>();

		try {
			Statement query = link.createStatement();

			ResultSet res = query.executeQuery(
					"SELECT * " +
					"FROM item " +
					"WHERE id_list='" + list_id + "'" +
					"ORDER BY position ASC;"
			);

			while (res.next()) {

				items.add(new Item(res.getDate("date"), res.getString("name"), res.getInt("position"), res.getString("description")));
			}

		} catch (SQLException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}

		return items;
	}

	public void add(Item item, String list_name) {

		try {
			Statement query = link.createStatement();

			ResultSet res = query.executeQuery("" +
					"SELECT id " +
					"FROM list " +
					"WHERE name='" + list_name + "';");

			if (res.next())
			{
				add(item, res.getInt("id"));
			}

		} catch (SQLException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}
}
