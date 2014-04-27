package Plugins.EventLog;

import Core.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

	/**
	 * Constructeur complet utilisé pour charger les events de la base de données
	 * @param id
	 * @param list_id_old
	 * @param list_id_new
	 * @param item_id_old
	 * @param item_id_new
	 * @param user_id
	 */
	public Event(int id, int list_id_old, int list_id_new, int item_id_old, int item_id_new, int user_id, Date date) {

		this.id = id;
		this.list_id_old = list_id_old;
		this.list_id_new = list_id_new;
		this.item_id_old = item_id_old;
		this.item_id_new = item_id_new;
		this.user_id = user_id;
		this.date = date;
	}

	/**
	 * Constructeur utilisé pour créer de nouveaux events
	 * @param list_id_old
	 * @param list_id_new
	 * @param item_id_old
	 * @param item_id_new
	 */
	public Event(int list_id_old, int list_id_new, int item_id_old, int item_id_new) {

		this.id = 0;
		this.list_id_old = list_id_old;
		this.list_id_new = list_id_new;
		this.item_id_old = item_id_old;
		this.item_id_new = item_id_new;
		this.user_id = CurrentUser.getInstance().getId();
		this.date = new Date();
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
		List list_old = list_dao.get(list_id_old, false, false);
		List list_new = list_dao.get(list_id_new, false, false);

		ItemDAO item_dao = new ItemDAO(BddConnection.getInstance());
		Item item_old = item_dao.get(item_id_old, false);
		Item item_new = item_dao.get(item_id_new, false);

		int event_list_code = (list_id_old != 0 ? LIST_DELETED : 0) + (list_id_new != 0 ? LIST_CREATED : 0);
		int event_item_code = (item_id_old != 0 ? ITEM_DELETED : 0) + (item_id_new != 0 ? ITEM_CREATED : 0);

		// Si les 2 list id sont égaux, on les ignore
		if (list_id_old - list_id_new == 0)
			event_list_code = 0;

		Locale language = Locale.ENGLISH;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM 'at' HH:mm", language);

		switch (event_list_code + event_item_code)
		{
			case LIST_CREATED:
				string.append(user.getName() + " created list: " + list_new.getName()+ " on " + dateFormat.format(getDate()));
				break;
			case LIST_DELETED:
				string.append(user.getName() + " deleted list: " + list_old.getName());
				break;
			case LIST_CHANGED:
				break;
			case ITEM_CREATED:
				string.append(user.getName() + " added item: " + item_new.getName() + " to list: " + list_dao.get(item_id_new).getName() + " on " + dateFormat.format(getDate()));
				break;
			case ITEM_DELETED:
				string.append(user.getName() + " deleted item: " + item_old.getName() + " from list: " + list_dao.get(item_id_old).getName() + " on " + dateFormat.format(getDate()));
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

	public int getId() {

		return id;
	}

	public int getItem_id_old() {

		return item_id_old;
	}

	public int getEventType() {

		int event_list_code = (list_id_old != 0 ? LIST_DELETED : 0) + (list_id_new != 0 ? LIST_CREATED : 0);
		int event_item_code = (item_id_old != 0 ? ITEM_DELETED : 0) + (item_id_new != 0 ? ITEM_CREATED : 0);

		// Si les 2 list id sont égaux, on les ignore
		if (list_id_old - list_id_new == 0)
			event_list_code = 0;

		return event_item_code + event_list_code;
	}

	public int getItem_id_new() {

		return item_id_new;
	}

	public int getUserId() {

		return user_id;
	}

	public void setId(int id) {

		this.id = id;
	}

	public int getList_id_old() {

		return list_id_old;
	}
}