package GUI;

import Core.*;
import Core.Event;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class EventLog {

	private JPanel log_zone;
	private JScrollPane scroll_pane;
	ArrayList<Event> events;
	private EventMenu event_menu;
	private int board_id;

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
			setup_event(event);

		log_zone.revalidate();

	}

	private void setup_event(Event event) {

		JLabel label = new JLabel(event.getReadableDescription());
		label.setName(String.valueOf(event.getId()));
		label.addMouseListener(event_menu.getLabelListener());
		log_zone.add(label);
		System.out.println(event.toString());
	}

	/**
	 *	Va chercher les nouveaux events et met à jour l'affichage
 	 */
	public void refresh() {

		EventDAO dao = new EventDAO(BddConnection.getInstance());
        ArrayList<Event> new_events;

		// On ne récupère que les events générés après le dernier de ceux qu'on a déjà
		if (events.size() >= 1)
			new_events = dao.getEventsByBoardAfter(board_id, events.get(events.size() - 1).getDate());
		else
			new_events = dao.getEventsByBoard(board_id); // Si on en avait pas, on prend tout

		System.out.println(new_events.size() + " new events found");

		for (Event event : new_events) {
			setup_event(event);
		}

		events.addAll(new_events);

		scroll_pane.getParent().revalidate();
		scroll_pane.getParent().repaint();

	}

	public JScrollPane getScroll_pane() {

		return scroll_pane;
	}

	private void RevertTo(Event event) {

		System.out.println("Annulation de "+event.getReadableDescription());
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
						RevertTo(clicked_event);
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
