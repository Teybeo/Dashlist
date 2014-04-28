package Core;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Observer;

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
					"SELECT id " +
					"FROM list " +
					"WHERE id_board='" + board_id + "' " +
					"AND is_deleted = FALSE;"
			);

			while (res.next()) {

				int list_id = res.getInt("id");

				ItemDAO dao = new ItemDAO(BddConnection.getInstance());

				lists.add(get(list_id, true, true));
			}

		} catch (SQLException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}

		return lists;
	}

	public List add(String list_name, int position, int board_id) {

		List list = new List(list_name, position);

		try {

			Statement query = link.createStatement();

			query.execute("" +
					"INSERT INTO list " +
					"VALUES(default, "+ board_id +",'"+ list.getName() +"', "+ list.getPosition() +", FALSE);", Statement.RETURN_GENERATED_KEYS);

			// On récupère l'id créé par MySQL
			ResultSet res = query.getGeneratedKeys();

			if (res.next())
				list.setId(res.getInt(1));

		} catch (SQLException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}

		return list;
	}

	public List get(int list_id, boolean complete_load, boolean only_alive) {

		List list = null;

        try {
            Statement query = link.createStatement();

            StringBuilder request = new StringBuilder("SELECT * " +
					         "FROM list " +
					         "WHERE id = '" + list_id + "'");
	        if (only_alive)
		        request.append(" AND is_deleted = FALSE;");
	        else
		        request.append(";");

	        query.execute(String.valueOf(request));

	        ResultSet res = query.getResultSet();

	        if (res.next())
	        {
		        list = new List(res.getInt("id"), res.getString("name"), res.getInt("position"));

	            if (complete_load == true)
			        loadItems(list);
	        }

        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

		return list;
	}

	/**
	 *	Récupère la liste contenant un certain item
	 */
	public List get(int item_id) {

		List list = null;

		try {
			Statement query = link.createStatement();

			query.execute("" +
					"SELECT list.id, list.name, list.position " +
					"FROM list, item " +
					"WHERE list.id = item.id_list AND " +
					"item.id = '" + item_id + "';");

			ResultSet res = query.getResultSet();

			if (res.next())
				list = new List(res.getInt("id"), res.getString("name"), res.getInt("position"));

		} catch (SQLException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}

		return list;
	}

	public void edit(List list) {

        try {
            Statement query = link.createStatement();

            query.execute("" +
                    "UPDATE list " +
                    "SET name ='"+list.getName()+"', " +
                    "position = "+list.getPosition() +
                    " WHERE id ="+list.getId());
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void delete(List list, List.Action_Source source) {

        try {
            Statement query = link.createStatement();

	        if (source == List.Action_Source.USER)
				query.execute("UPDATE list SET is_deleted = TRUE WHERE id ="+ list.getId());
	        else if (source == List.Action_Source.EVENT_LOG)
		        query.execute("DELETE FROM list WHERE id ="+ list.getId());

			list.delete(source);

        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

	/**
	 * Annule la suppression d'une liste. Le flag is_deleted de cette liste est simplement remis à false
	 * @param list_id l'id de la liste dont la suppression est à annuler
	 */
	public void revertDelete(int list_id) {

		try {
			Statement query = link.createStatement();

			query.execute("UPDATE list SET is_deleted=FALSE WHERE id = '" + list_id + "';");

		} catch (SQLException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}

	public void loadItems(List list) {

		ItemDAO dao = new ItemDAO(BddConnection.getInstance());
		list.setItems(dao.getListItems(list.getId()));

	}

	public void addItem(List list, String item_name) {

		ItemDAO dao = new ItemDAO(BddConnection.getInstance());

		Item item = dao.add(item_name, list.getItems().size() + 1, list.getId());

		list.add(item, List.Action_Source.USER);
	}

}
