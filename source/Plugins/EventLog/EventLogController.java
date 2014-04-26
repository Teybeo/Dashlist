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
							"ORDER BY event.date DESC");

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
	/**
	 * Récupère la liste de tous les events du journal actuel qui se sont déroulés après l'event donné en paramètre
	 * @param event L'event auquel sont postérieurs les events renvoyés par la fonction
	 * @return La liste des events de la board actuelle postérieurs à event. Liste vide si pas d'events postérieurs
	 * */
	private ArrayList<Plugins.EventLog.Event> findFollowingEvents(Event event) {

	ArrayList<Plugins.EventLog.Event> following_events = new ArrayList<>();

	// Les events sont triés du plus récent au plus vieux
	for (Plugins.EventLog.Event event_tmp : log.getEvents())
	{
	// Une fois atteint l'event ciblé, on s'arrête
	if (event_tmp.getId() == event.getId())
	break;

	// Tant qu'on a pas trouvé l'event, on ajoute à la liste
	// Vérification au cas où, le bon tri de la liste est sensé être assuré
	if (event_tmp.getDate().after(event.getDate()))
	following_events.add(event_tmp);
	}

	return following_events;
	}


	public void addListEvent(List old_list, List new_list) {

		EventDAO dao = new EventDAO(BddConnection.getInstance());

		Event event = dao.add(old_list, new_list);

		log.add(event);
	}
}
