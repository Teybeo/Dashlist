package Plugins.EventLog;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Guillaume on 27/04/14.
 */
public class EventUI extends JPanel {

	private Event event;
	private JPanel panelEvent;
	private JLabel labelStr;
	private JLabel labelDate;

	public EventUI(Plugins.EventLog.Event event) {

		this.event = event;

		String str[] = event.getReadableDescription().split("on");
		panelEvent = new JPanel();
		labelStr = new JLabel(str[0]);
		labelDate = new JLabel(str[1]);

		panelEvent.setLayout(new BoxLayout(panelEvent, BoxLayout.Y_AXIS));

		labelStr.setName(String.valueOf(event.getId()));
		labelStr.setFont(new Font("Arial", Font.PLAIN, 12));

		labelDate.setFont(new Font("Arial", Font.BOLD, 10));
		labelDate.setForeground(new Color(151, 151, 151));

		System.out.println(event.toString());

		panelEvent.add(labelStr);
		panelEvent.add(labelDate);
		this.add(panelEvent);

	}

	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}
}