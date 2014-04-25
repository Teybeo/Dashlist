package Plugins.EventLog;

import Core.BddConnection;
import Core.Item;
import Core.ItemDAO;
import Core.List;

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

	public EventLogController(EventLog log, Connection link) {

		this.link = link;
		this.log = log;
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

	/**
	 * Enregistre un event d'item dans la base
	 *<P> Si old_item est null et new_item est valide, cet event est un ajout </P>
	 *<P> Si old_item est valide et new_item est null, cet event est un suppression </P>
	 *<P> Si old_item et new_item sont valides, cet event est une modification </P>
	 *
	 * @param old_item Id de l'ancien item
	 * @param new_item Id du nouvel item
	 * @param user_id Id de l'user ayant fait l'action
	 */
	public void add(Item old_item, Item new_item, int user_id) {

		try {

			Statement query = link.createStatement();

			String old_item_id = (old_item == null) ? "null" : Integer.toString(old_item.getId());
			String new_item_id = (new_item == null) ? "null" : Integer.toString(new_item.getId());

			query.execute("INSERT INTO event "+
					"VALUES ( default, null, null, "+ old_item_id +", "+ new_item_id +",'"+ user_id +"', NOW());");

		} catch (SQLException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
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

	public boolean revert(Event event) {

		boolean done = false;

		int event_type = event.getEventType();

		ItemDAO dao = new ItemDAO(BddConnection.getInstance());

		switch (event_type)
		{
			case Event.LIST_CREATED:
				System.out.println("Not implemented yet");
				break;
			case Event.LIST_DELETED:
				System.out.println("Not implemented yet");
				break;
			case Event.LIST_CHANGED:
				System.out.println("Not implemented yet");
				break;
			case Event.ITEM_CREATED:
				// On supprime d'abord l'event dans la BDD car il référence l'id de l'event à supprimer
				delete(event);
				dao.revertCreate(event.getItem_id_new());
				done = true;
				break;
			case Event.ITEM_DELETED:
				delete(event);
				dao.revertDelete(event.getItem_id_old());
				done = true;
				break;
			case Event.ITEM_CHANGED:
				System.out.println("Not implemented yet");
				break;
			default:
				System.out.println("Error: unrecognized event type: "+event_type+"\n"+this);
				break;
		}

		return done;
	}

	public void delete(Event event) {
		try {

			Statement query = link.createStatement();

			query.execute("DELETE FROM event WHERE id ='" + event.getId() + "'");

		} catch (SQLException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}

	}

}
