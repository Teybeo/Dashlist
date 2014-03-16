package Core;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EventDAO {

	private Connection link;

	public EventDAO(Connection link) {

		this.link = link;
	}

	public void add(List list, int user_id) {

		try {

			Statement query = link.createStatement();

			query.execute("INSERT INTO event "+
						  "VALUES ( default, null, '"+ list.getId() +"', null, null, '"+ user_id +"', NOW());");

		} catch (SQLException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}

	}

	public ArrayList<Event> getEventsByBoard(int board_id) {

		return getEventsByBoardAfter(board_id, new Date(0));

	}

	public ArrayList<Event> getEventsByBoardAfter(int board_id, Date date) {

		ArrayList<Event> events = new ArrayList<>();

		try {

			Statement query = link.createStatement();

			query.execute(  "SELECT * " +
					"FROM event, list " +
					"WHERE (list.id = event.list_id_old OR list.id = event.list_id_new) AND " +
					"list.id_board ='"+board_id+"' AND " +
					"event.date > '" + new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(date) + "';");

			ResultSet res = query.getResultSet();

			while (res.next()) {

				events.add(new Event(res.getInt("id"),
						res.getInt("list_id_old"), res.getInt("list_id_new"),
						res.getInt("item_id_old"), res.getInt("item_id_new"),
						res.getInt("user_id"),     new Date(res.getTimestamp("date").getTime())));
			}

		} catch (SQLException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
		return events;


	}
}
