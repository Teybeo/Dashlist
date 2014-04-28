package Core;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class ItemDAO {

	private Connection link;


	public ItemDAO(Connection link) {
		this.link = link;
	}

	public Item add(String item_name, int position, int list_id) {

		Item item = new Item(item_name, position);

		try {

			Statement query = link.createStatement();

			query.execute("" +
					"INSERT INTO item " +
					"VALUES(default, "+list_id+",'"+item.getName()+"', NOW(), NULL, "+item.getPosition()+", FALSE, NULL);", Statement.RETURN_GENERATED_KEYS);

			// On récupère l'id créé par MySQL
			ResultSet res = query.getGeneratedKeys();

			if (res.next())
				item.setId(res.getInt(1));

		} catch (SQLException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}

		return item;
	}

    public void edit(Item item, int list_id) {

        try {
            Statement query = link.createStatement();

            query.execute("" +
                    "UPDATE item " +
                    "SET name ='"+item.getName()+"', " +
                    "position = "+item.getPosition() +
                    "description = '"+item.getText() +"', " +
                    "id_list = "+list_id +
                    " WHERE id ="+item.getId());
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void delete(Item item, Item.Action_Source source) {
        try {
            Statement query = link.createStatement();

	        if (source == Item.Action_Source.USER)
	            query.execute("UPDATE item SET is_deleted = TRUE WHERE id ="+item.getId());
	        else if (source == Item.Action_Source.EVENT_LOG)
		        query.execute("DELETE FROM item WHERE id = '" + item.getId() + "';");

	        item.delete(source);

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
					"WHERE id_list='" + list_id + "' " +
					"AND is_deleted = FALSE " +
					"ORDER BY position ASC;"
			);

			while (res.next()) {
                int item_id = res.getInt("id");

				items.add(new Item(item_id, res.getDate("date"), res.getString("name"), res.getInt("position"), res.getString("description")));
			}

		} catch (SQLException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}

		return items;
	}

	public Item get(int item_id, boolean complete_load) {

		Item item = null;

		try {
			Statement query = link.createStatement();

			query.execute("" +
					"SELECT * FROM item WHERE id = '" + item_id + "';");

			ResultSet res = query.getResultSet();

			if (res.next())
			{
				item = new Item(res.getInt("id"), new Date(res.getTimestamp("date").getTime()), res.getString("name"), res.getInt("position"), null);

				if (complete_load == true)
					loadComments(item);
			}

		} catch (SQLException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}

		return item;
	}

	/**
	 * Annule la suppression d'un item. Le flag is_deleted de cet item est simplement remis à false
	 * @param item_id l'id de l'item dont la suppression est à annuler
	 */
	public void revertDelete(int item_id) {

		try {
			Statement query = link.createStatement();

			query.execute("" +
					"UPDATE item SET is_deleted=FALSE WHERE id = '" + item_id + "';");

		} catch (SQLException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}

	private void loadComments(Item item) {
		//TODO: fonction loadcomments()
	}


}
