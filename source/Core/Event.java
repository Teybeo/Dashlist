package Core;

import java.util.Date;

public class Event {

	private int id;
	private int list_id_old;
	private int list_id_new;
	private int item_id_old;
	private int item_id_new;
	private int user_id;
	private Date date;

	public Event(int id, int list_id_old, int list_id_new, int item_id_old, int item_id_new, int user_id, Date date) {

		this.id = id;
		this.list_id_old = list_id_old;
		this.list_id_new = list_id_new;
		this.item_id_old = item_id_old;
		this.item_id_new = item_id_new;
		this.user_id = user_id;
		this.date = date;
	}
}
