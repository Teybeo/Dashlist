package Plugins.EventLog;

import Core.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Guillaume on 27/04/14.
 */
class EventUI extends JPanel implements Observer{

	private Event event;
	private JPanel panelEvent;
	private JLabel labelStr;
	private JLabel labelDate;

	public EventUI(Event event, MouseAdapter mouse_listener) {

		this.event = event;
		this.event.addObserver(this);
		this.addMouseListener(mouse_listener);

		String str[] = event.getReadableDescription().split(" on ");
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

	@Override
	public void update(Observable o, Object arg) {

		String sender = o.getClass().getName();

		System.out.println("EventUI received ["+ arg +"] from ["+ sender +"]");

		if (sender.equals("Plugins.EventLog.Event"))
		{
			String message = ((String)arg);

			if (message.startsWith("Event deleted")) {
				Container parent = getParent();
				parent.remove(this);
				parent.revalidate();
				parent.repaint();
			}

		}
	}
}