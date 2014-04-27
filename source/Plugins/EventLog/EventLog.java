package Plugins.EventLog;

import Core.Board;
import Core.List;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class EventLog extends Observable implements Observer {

	ArrayList<Event> events = new ArrayList<>();
	Board board;

	public EventLog(Board board) {
		this.board = board;
	}

	public void setBoard(Board board) {

		this.board = board;
	}

	public ArrayList<Event> getEvents() {


		return events;
	}

	public Board getBoard() {

		return board;
	}

	public void add(Event event) {

		// On l'insère en tête de liste car plus récent
		events.add(0, event);

		setChanged();
		notifyObservers("Event added: "+event.getId());
		clearChanged();
	}

	public void remove(Event event_to_revert) {

		setChanged();
		notifyObservers("Event deleted: "+event_to_revert.getId());
		clearChanged();

		events.remove(event_to_revert);
	}

	@Override
	public void update(Observable o, Object arg) {

		String sender = o.getClass().getName();

		System.out.println("EventLog received ["+ arg +"] from ["+ sender +"]");

	}

	/**
	 * Récupère la liste de tous les events du journal actuel qui se sont déroulés après l'event donné en paramètre
	 * @param event L'event auquel sont postérieurs les events renvoyés par la fonction
	 * @return La liste des events de la board actuelle postérieurs à event. Liste vide si pas d'events postérieurs
	 * */
	public ArrayList<Event> findFollowingEvents(Event event) {

		ArrayList<Event> following_events = new ArrayList<>();

		// Les events sont triés du plus récent au plus vieux
		for (Plugins.EventLog.Event event_tmp : events)
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

	public Event getEventById(int id) {

		for (Event event : events)
			if (event.getId() == id)
				return event;
		return null;
	}

	public List getListById(int list_id) {

		for (List list : board.getLists())
			if (list.getId() == list_id)
				return list;

		return null;
	}
}
