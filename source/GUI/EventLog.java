package GUI;

import Core.*;
import Core.Event;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

public class EventLog {

	private JPanel log_zone;
	private JScrollPane scroll_pane;
	ArrayList<Event> events;
	private EventMenu event_menu;
	private int board_id;
	private static final int FIRST = 1;
	private static final int LAST = -1;

	public EventLog(int board_id) {

		log_zone = new JPanel();
		log_zone.setLayout(new BoxLayout(log_zone, BoxLayout.Y_AXIS));
		log_zone.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));

		scroll_pane = new JScrollPane(log_zone);

		event_menu = new EventMenu();

		this.board_id = board_id;
		EventDAO dao = new EventDAO(BddConnection.getInstance());
		events = dao.getEventsByBoard(board_id);

		for (Event event : events)
			setup_event(event, LAST);

		log_zone.revalidate();

	}

	private void setup_event(Event event, int position) {

		JLabel label = new JLabel(event.getReadableDescription());
		label.setName(String.valueOf(event.getId()));
		label.addMouseListener(event_menu.getLabelListener());
		System.out.println(event.toString());
		log_zone.add(label, position);
	}

	/**
	 *	Va chercher les nouveaux events et met à jour l'affichage
 	 */
	public void refresh() {

		EventDAO dao = new EventDAO(BddConnection.getInstance());
        ArrayList<Event> new_events;

		// On ne récupère que les events générés après le dernier de ceux qu'on a déjà
		if (events.size() >= 1)
			new_events = dao.getEventsByBoardAfter(board_id, events.get(1).getDate());
		else
			new_events = dao.getEventsByBoard(board_id); // Si on en avait pas, on prend tout

		System.out.println(new_events.size() + " new events found");

		// On les insère en haut de la liste d'affichage
		for (Event event : new_events) {
			setup_event(event, FIRST);
		}

		// Et on les insère en premier dans la mémoire
		for (Event new_ev : new_events)
			events.add(1, new_ev);

		scroll_pane.getParent().revalidate();
		scroll_pane.getParent().repaint();

	}

	public JScrollPane getScroll_pane() {

		return scroll_pane;
	}

	private void revertTo(Event event) {

		Iterator<Event> iterator = events.iterator();
		Event ev_tmp = null;
		EventDAO dao = new EventDAO(BddConnection.getInstance());

		while (iterator.hasNext())
		{
			ev_tmp = iterator.next();
			System.out.println("Annulation de "+ev_tmp.getReadableDescription());

			if (dao.revert(ev_tmp) == true)
			{
				events.remove(ev_tmp);
				log_zone.remove(findLabel(ev_tmp.getId()));
			}

			// Une fois atteint l'event ciblé, on s'arrête
			if (ev_tmp.getId() == event.getId())
				break;
		}

		scroll_pane.getParent().revalidate();
		scroll_pane.getParent().repaint();

	}

	private JLabel findLabel(int id) {

		for (Component c : log_zone.getComponents())
			if (Integer.parseInt(c.getName()) == id)
				return (JLabel)c;

		return null;
	}

	private class EventMenu extends JPopupMenu {

		private Event event_clicked;
		private MouseAdapter label_listener;

		public EventMenu() {

			super();

			// Ecouteur de clics de souris pour afficher le menu contextuel
			label_listener = new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {

					super.mouseReleased(e);

					if (e.isPopupTrigger()) {

						// On récupère l'id de l'event stocké dans le name du JLabel
						int id = Integer.parseInt(e.getComponent().getName());

						// On récupère l'event et le garde en mémoire pour les actions du menu
						for (Event event : events)
							if (event.getId() == id)
							{
								setEventClicked(event);
								show(e.getComponent(), e.getX(), e.getY());
								break;
							}

					}
				}
			};

			// Cet écouteur se déclenche quand on clique sur l'option "Revenir à ce point" du menu
			ActionListener revert_listener = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

					// On récupère l'event sur lequel avait été invoqué le menu contextuel
					Event clicked_event = ((EventMenu)((JMenuItem)e.getSource()).getParent()).getEventClicked();

					if (clicked_event == null)
						System.out.println("Le clicked label était null");
					else
						revertTo(clicked_event);
				}
			};

			JMenuItem revert_option = new JMenuItem("Revenir à ce point");
			revert_option.addActionListener(revert_listener);
			add(revert_option);

		}

		private void setEventClicked(Event event_clicked) {

			this.event_clicked = event_clicked;
		}

		private Event getEventClicked() {


			return event_clicked;
		}

		public MouseListener getLabelListener() {

			return label_listener;
		}
	}
}
