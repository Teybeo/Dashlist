package Plugins.EventLog;

import Core.*;

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

	/**
	 * Enregistre un event de list dans la base
	 *<P> Si old_list est null et new_list est valide, cet event est un ajout </P>
	 *<P> Si old_list est valide et new_list est null, cet event est un suppression </P>
	 *<P> Si old_list et new_list sont valides, cet event est une modification </P>
	 *
	 * @param old_list Id de l'ancien list
	 * @param new_list Id du nouvel list
	 */
	public Event add(List old_list, List new_list) {

		int old_list_id = (old_list == null) ? 0 : old_list.getId();
		int new_list_id = (new_list == null) ? 0 : new_list.getId();

		Event event = new Event(old_list_id, new_list_id, 0, 0);

		String old_list_id_str = (old_list == null) ? "null" : Integer.toString(old_list.getId());
		String new_list_id_str = (new_list == null) ? "null" : Integer.toString(new_list.getId());

		try {

			Statement query = link.createStatement();


			query.execute("INSERT INTO event "+
					"VALUES ( default, "+ old_list_id_str +", "+ new_list_id_str +", null, null, '"+ event.getUserId() +"', NOW());", Statement.RETURN_GENERATED_KEYS);

			// On récupère l'id créé par MySQL
			ResultSet res = query.getGeneratedKeys();

			if (res.next())
				event.setId(res.getInt(1));

		} catch (SQLException e) {

			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}

		return event;
	}

	/**
	 * Enregistre un event d'item dans la base
	 *<P> Si old_item est null et new_item est valide, cet event est un ajout </P>
	 *<P> Si old_item est valide et new_item est null, cet event est un suppression </P>
	 *<P> Si old_item et new_item sont valides, cet event est une modification </P>
	 *
	 * @param old_item Id de l'ancien item
	 * @param new_item Id du nouvel item
	 */
	public Event add(Item old_item, Item new_item, List list) {

		int old_item_id = (old_item == null) ? 0 : old_item.getId();
		int new_item_id = (new_item == null) ? 0 : new_item.getId();

		Event event = new Event(list.getId(), list.getId(), old_item_id, new_item_id);


		try {

			Statement query = link.createStatement();

			String old_item_id_str = (old_item == null) ? "null" : Integer.toString(old_item.getId());
			String new_item_id_str = (new_item == null) ? "null" : Integer.toString(new_item.getId());

			query.execute("INSERT INTO event "+
					"VALUES ( default,'"+list.getId()+"','"+list.getId()+"', "+ old_item_id_str +", "+ new_item_id_str +",'"+ event.getUserId() +"', NOW());", Statement.RETURN_GENERATED_KEYS);

			// On récupère l'id créé par MySQL
			ResultSet res = query.getGeneratedKeys();

			if (res.next())
				event.setId(res.getInt(1));

		} catch (SQLException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}

		return event;
	}

	public boolean revert(Event event, Board board) {

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
				// 1. On supprime l'event dans la BDD
				// 2. On supprime l'item dans la BDD
				// 3. On supprime l'item de la mémoire
				delete(event);
				dao.revertCreate(event.getItem_id_new());
				board.getItemById(event.getItem_id_new()).delete(Item.Delete_Type.HARD_DELETE);
				done = true;
				break;
			case Event.ITEM_DELETED:
				// 1. On supprime l'event dans la BDD
				// 2. On reflag   l'item dans la BDD comme n'étant pas supprimé
				// 3. On recrée l'item dans la mémoire
				delete(event);
				dao.revertDelete(event.getItem_id_old());
				board.getListById(event.getList_id_old()).add(dao.get(event.getItem_id_old(), true), true);
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
