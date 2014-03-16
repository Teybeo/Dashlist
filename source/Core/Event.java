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
	final static int LIST_DELETED = 1;
	final static int LIST_CREATED = 2;
	final static int LIST_CHANGED = 3;
	final static int ITEM_DELETED = 4;
	final static int ITEM_CREATED = 8;
	final static int ITEM_CHANGED = 12;

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

		ItemDAO item_dao = new ItemDAO(BddConnection.getInstance());
		Item item = item_dao.get(item_id_new, false);

		int event_list_code = (list_id_old != 0 ? LIST_DELETED : 0) + (list_id_new != 0 ? LIST_CREATED : 0);
		int event_item_code = (item_id_old != 0 ? ITEM_DELETED : 0) + (item_id_new != 0 ? ITEM_CREATED : 0);

		switch (event_list_code + event_item_code)
		{
			case LIST_CREATED:
				string.append(user.getName() + " created list " + list.getName());
				break;
			case LIST_DELETED:
				break;
			case LIST_CHANGED:
				break;
			case ITEM_CREATED:
				string.append(user.getName() + " added item " + item.getName() + " to list " + list_dao.get(item_id_new).getName());
				break;
			case ITEM_DELETED:
				break;
			case ITEM_CHANGED:
				break;
			default:
				System.out.println("Error: unrecognized event type: list_code: "+event_list_code+
						"item_code: "+event_item_code+"\n"+this);
				break;
		}

		readable_description = string.toString();

		return readable_description;
	}

	public Date getDate() {

		return date;
	}
}
