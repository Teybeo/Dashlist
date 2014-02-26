package Core;

import java.util.HashSet;

public class List {

	int id;
	String name;
	int position;
	HashSet<Item> items;

	public List(HashSet<Item> items, String name, int position) {

		this.items = items;
		this.name = name;
		this.position = position;
	}

	public int getId() {

		return id;
	}

	public HashSet<Item> getItems() {

		return items;
	}

	public void setItems(HashSet<Item> items) {

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
}
