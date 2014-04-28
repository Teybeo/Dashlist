package Core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;

public class List extends Observable implements Observer {

	private int id;
	private String name;
	private int position;
	private ArrayList<Item> items = new ArrayList<>();
	public static enum Action_Source {
		USER,
		EVENT_LOG
	}

	public List(String name, int position) {

		this.name = name;
		this.position = position;
	}

	public List(int id, String name, int position) {

		this.id = id;
		this.name = name;
		this.position = position;

	}

	public List(int id, String name, int position, Observer observer) {

		this.id = id;
		this.name = name;
		this.position = position;
		this.addObserver(observer);

		setChanged();
		notifyObservers("List added");
		clearChanged();

	}

	public int getId() {

		return id;
	}

	public ArrayList<Item> getItems() {

		return items;
	}

	public void setItems(ArrayList<Item> items) {

		this.items = items;
		if (items != null)
		for (Item item : items)
			item.addObserver(this);
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public int getPosition() {

		return position;
	}

	public void setPosition(int position) {

		this.position = position;
	}

	public void setId(int id) {

		this.id = id;
	}

	public void addItem(Item item) {

		items.add(item);
		item.addObserver(this);
		// On a modifié les données, on notifie les observers
		setChanged();
		notifyObservers();
		clearChanged();
	}

	@Override
	public void update(Observable o, Object arg) {

		String sender = o.getClass().getName();

		System.out.println("List received ["+ arg +"] from ["+ sender +"]");

		if (sender.equals("Core.Item"))
		{
			// L'user a supprimé l'item en cliquant dessus
			// On transmet pour créer un event
			if (arg.equals("Item deleted"))
			{
				setChanged();
				notifyObservers("Item deleted: "+((Item)o).getId() + " from: "+ id);
				clearChanged();
				items.remove(o);
			}
			// Dans ce cas-la, c'est une création qui est annulée
			// Donc on ne transmet pas pour ne pas regénérer d'évent
			else if (arg.equals("Item deleted (by eventlog)"))
			{
				items.remove(o);
			}
		}

	}

	public void add(Item item, Action_Source source) {

		System.out.println("Position: "+item.getPosition());
		items.add(Math.min(item.getPosition()-1, items.size()), item);
		item.addObserver(this);

		setChanged();
		if (source == Action_Source.EVENT_LOG)
			notifyObservers("Item added (by eventlog): "+item.getId() + " in: "+id);
		else if (source == Action_Source.USER)
			notifyObservers("Item added: "+item.getId() + " in: "+id);
		clearChanged();
	}

	public Item getItemById(int id) {

		for (Item item : items)
			if (item.getId() == id)
				return item;

		return null;  //To change body of created methods use File | Settings | File Templates.
	}

	public void delete(Action_Source source) {
		setChanged();
		if (source == Action_Source.USER)
			notifyObservers("List deleted: "+id);
		else if (source == Action_Source.EVENT_LOG)
			notifyObservers("List deleted (by eventlog): "+id);
		clearChanged();
	}

}
