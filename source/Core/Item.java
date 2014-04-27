package Core;

import java.util.Date;
import java.util.Observable;

public class Item extends Observable {

    int id;
	String name;
	int position;
	Date date;
	String text;
	public static enum Delete_Type {
		SOFT_DELETE,
		HARD_DELETE
	};

	public Item(int id, Date date, String name, int position, String text) {

        this.id = id;
		this.date = date;
		this.name = name;
		this.position = position;
		this.text = text;
	}

	public Item(String name, int position) {

		this.name = name;
		this.position = position;
		this.date = new Date();
	}

    public int getId() {

        return id;
    }

	public Date getDate() {

		return date;
	}

	public void setDate(Date date) {

		this.date = date;
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

	public String getText() {

		return text;
	}

	public void setText(String text) {

		this.text = text;
	}

	public void setId(int id) {

		this.id = id;
	}

	public void delete(Delete_Type type) {

		setChanged();
		if (type == Delete_Type.SOFT_DELETE)
			notifyObservers("Item deleted");
		else if (type == Delete_Type.HARD_DELETE)
			notifyObservers("Item deleted (by eventlog)");
		clearChanged();
	}
}
