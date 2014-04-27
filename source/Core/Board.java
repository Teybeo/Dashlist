package Core;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Board extends Observable implements Observer {

	private int id;
	private String name;
	private ArrayList<List> lists;

	public Board(String name) {

		this.name = name;
	}

	public Board(int id, String name) {

		this.id = id;
		this.name = name;
	}

	public Board(int id, String name, ArrayList<List> lists) {

		this.id = id;
		this.name = name;
		this.lists = lists;

		for (List list : lists)
			list.addObserver(this);
	}

	public int getId() {

		return id;
	}

	public void setId(int id) {

		this.id = id;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public List getListByName(String name) {

		List list = null;
		for (List l : lists)
			if (l.getName().equals(name))
				return l;

		return null;
	}

	public ArrayList<List> getLists() {

		return lists;
	}

	public void setLists(ArrayList<List> lists) {

		this.lists = lists;
		for (List list : lists)
			list.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) {

		String sender = o.getClass().getName();

		System.out.println("Board received ["+ arg +"] from ["+ sender +"]");

		if (sender.equals("Core.List"))
		{
			String message = (String) arg;
			// On transmet la notification pour les plugins
			// Seulement si le message ne contient pas (by eventlog)
			if (message.startsWith("Item added: ")   ||
				message.startsWith("Item deleted: ") ||
				message.startsWith("List deleted: "))
			{
				setChanged();
				notifyObservers(arg);
				clearChanged();
			}

		}

	}

	/**
	 * Ajout d'une liste à la board. La board s'inscrit en tant qu'observateur sur cette liste et signale ses propres
	 * observateurs qu'elle a obtenue une nouvelle liste et envoie l'id de cette liste
	 * @param list La liste à ajouter
	 */
	public void add(List list, List.Action_Source source) {

		lists.add(Math.min(list.getPosition()-1, lists.size()), list);
		list.addObserver(this);

		setChanged();
		if (source == List.Action_Source.EVENT_LOG)
			notifyObservers("List added (by eventlog): "+list.getId());
		else if (source == List.Action_Source.USER)
			notifyObservers("List added: "+list.getId());
		clearChanged();
	}

	public List getListById(int id) {

		for (List l : lists)
			if (l.getId() == id)
				return l;

		return null;
	}

	// Et ouais
	public Item getItemById(int id) {

		for (List l : lists)
			for (Item item : l.getItems())
				if (item.getId() == id)
					return item;

		return null;  //To change body of created methods use File | Settings | File Templates.
	}
}

