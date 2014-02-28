package Core;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Item {

	String name;
	int position;
	Date date;
	String text;

	public Item(Date date, String name, int position, String text) {


		this.date = date;
		this.name = name;
		this.position = position;
		this.text = text;
	}

	public Item(String name, int position) {

		this.name = name;
		this.position = position;
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("YYYY-MM-DD");
		format.format(date);
		this.date = date;
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
}
