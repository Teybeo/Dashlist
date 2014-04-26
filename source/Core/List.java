package Core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;

public class List extends Observable implements Observer {

	int id;
	String name;
	int position;
	ArrayList<Item> items = new ArrayList<>();

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

	public Item getItem(String name) {

		for (Item item : items)
		{
			if (item.getName().equals(name) == true)
				return item;

		}
		return null;
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
		notifyObservers(item);
		clearChanged();
	}

	@Override
	public void update(Observable o, Object arg) {

		String sender = o.getClass().getName();

		System.out.println("List received ["+ arg +"] from ["+ o +"]");

		if (sender.equals("Core.Item"))
		{
			String param = ((String)arg);
			if (param.equals("Item deleted"))
			{
				items.remove(o);

			}
		}

	}
}
