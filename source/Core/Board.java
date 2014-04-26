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

		System.out.println("Board received ["+ arg +"] from ["+ o +"]");

		if (((String)arg).equals("List added")) {
			add((List)o);
		}
	}

	public void add(List list) {

		lists.add(list);
		list.addObserver(this);
		setChanged();
		notifyObservers("List added:"+list.getId());
		clearChanged();
	}

	public List getListById(int id) {

		for (List l : lists)
			if (l.getId() == id)
				return l;

		return null;
	}
}

