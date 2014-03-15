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
	private String readable_description;

	public Event(int id, int list_id_old, int list_id_new, int item_id_old, int item_id_new, int user_id, Date date) {

		this.id = id;
		this.list_id_old = list_id_old;
		this.list_id_new = list_id_new;
		this.item_id_old = item_id_old;
		this.item_id_new = item_id_new;
		this.user_id = user_id;
		this.date = date;
	}

	@Override
	public String toString() {

		return "Event{" +
				"id=" + id +
				", list_id_old=" + list_id_old +
				", list_id_new=" + list_id_new +
				", item_id_old=" + item_id_old +
				", item_id_new=" + item_id_new +
				", user_id=" + user_id +
				", date=" + date +
				'}';
	}

	public String getReadableDescription() {

		if (readable_description != null)
			return readable_description;

		StringBuilder string = new StringBuilder();

		UserDAO user_dao = new UserDAO(BddConnection.getInstance());
		User user = user_dao.get(user_id);

		ListDAO list_dao = new ListDAO(BddConnection.getInstance());
		List list = list_dao.get(list_id_new, false);

		string.append(user.getName() + " added " + list.getName());

		readable_description = string.toString();

		return readable_description;
	}
}
