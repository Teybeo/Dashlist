package Core;

import java.util.ArrayList;
import java.util.HashSet;

public class List {

	int id;
	String name;
	int position;
	ArrayList<Item> items;

	public List(String name, int position) {

		this.name = name;
		this.position = position;
		this.items = new ArrayList<>();
	}

	public List(int id, String name, int position, ArrayList<Item> items) {

		this.id = id;
		this.name = name;
		this.position = position;
		this.items = items;
	}

	public int getId() {

		System.out.println(id);
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
}
