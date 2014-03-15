package GUI;

import Core.*;
import Core.Event;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class EventLog {

	private JPanel log_zone;
	private JScrollPane scroll_pane;
	ArrayList<Event> events;
	private int board_id;

	public EventLog(int board_id) {

		log_zone = new JPanel();
		log_zone.setLayout(new BoxLayout(log_zone, BoxLayout.Y_AXIS));
		log_zone.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));

		scroll_pane = new JScrollPane(log_zone);

		this.board_id = board_id;
		EventDAO dao = new EventDAO(BddConnection.getInstance());
		events = dao.getEventsByBoard(board_id);

		for (Event event : events)
			setup_event(event);

		log_zone.revalidate();

	}

	private void setup_event(Event event) {

		JLabel label = new JLabel(event.getReadableDescription());
		log_zone.add(label);
		System.out.println(event.toString());
	}

	public JScrollPane getScroll_pane() {

		return scroll_pane;
	}


}
