package Plugins.EventLog;

import Core.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EventLogController {

	private Connection link;
	private EventLog log;

	public EventLogController(EventLog log , Connection link) {

		this.link = link;
		this.log = log;
	}

	public void loadEvents() {

		loadEventsAfter(new Date(0));

	}

	public void loadEventsAfter(Date date) {

		try {

			Statement query = link.createStatement();

 			/* Récupérer tous les events contenant soit
 			 	- un id de liste appartenant à la board spécifié par boarad_id
 				- un id d'item appartenant à une liste appartenant à la board spécifiée par board_id  5*/
			query.execute(
					"SELECT * " +
							"FROM event, list, item " +
							"WHERE list.id_board = '"+log.getBoard().getId()+"' AND " +
							"(" +
							"      (list.id = event.list_id_old OR list.id = event.list_id_new) OR" +
							"      (list.id = item.id_list AND (item.id = event.item_id_old OR item.id = event.item_id_new))" +
							")" +
							"AND event.date > '" + new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(date) + "' " +
							"GROUP BY event.id "+
							"ORDER BY event.date ASC");

			ResultSet res = query.getResultSet();

			while (res.next()) {

				log.add(new Event(res.getInt("id"),
						res.getInt("list_id_old"), res.getInt("list_id_new"),
						res.getInt("item_id_old"), res.getInt("item_id_new"),
						res.getInt("user_id"),     new Date(res.getTimestamp("date").getTime())));
			}

		} catch (SQLException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}

	}

	public void addListEvent(List old_list, List new_list) {

		EventDAO dao = new EventDAO(BddConnection.getInstance());

		Event event = dao.add(old_list, new_list);

		log.add(event);
	}

	public void addItemEvent(Item old_item, Item new_item, List list) {

		EventDAO dao = new EventDAO(BddConnection.getInstance());

		Event event = dao.add(old_item, new_item, list);

		log.add(event);
	}

	public void revertTo(Event event) {

		EventDAO dao = new EventDAO(BddConnection.getInstance());

		ArrayList<Event> following_events = log.findFollowingEvents(event);

		for (Event event_to_revert : following_events) {

			System.out.println("Annulation de "+event_to_revert.getReadableDescription());

			// Temporaire, revert() n'est pas implémentée pour tous les types d'events
			// Si un type n'est pas implémentée, elle renvoie false et ne change rien à la base
			// Dans ces cas-la, on ne fait rien non plus dans l'appli sur ces events
			if (dao.revert(event_to_revert, log.getBoard()) == true)
				log.remove(event_to_revert);


		}

	}

}
