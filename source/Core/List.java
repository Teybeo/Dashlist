package Core;

import java.util.HashSet;

public class List {

	int id;
	String name;
	int position;
	HashSet<Item> items;

	public List(int id, String name, int position, HashSet<Item> items) {

		this.id = id;
		this.name = name;
		this.position = position;
		this.items = items;
	}

	public int getId() {

		System.out.println(id);
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
