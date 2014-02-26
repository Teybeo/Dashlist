package Core;

import java.util.ArrayList;

public class Board {

	private int id;
	private String name;
	private ArrayList<List> lists;

	public Board(int id, String name) {

		this.id = id;
		this.name = name;
	}

	public Board(int id, String name, ArrayList<List> lists) {

		this.id = id;
		this.name = name;
		this.lists = lists;
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

	public ArrayList<List> getLists() {

		return lists;
	}

	public void setLists(ArrayList<List> lists) {

		this.lists = lists;
	}
}

