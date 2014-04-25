package Plugins.EventLog;

import Core.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class EventLogUI {

	private JPanel log_zone;
	private JScrollPane scroll_pane;
	ArrayList<Plugins.EventLog.Event> events;
	private EventMenu event_menu;
	private int board_id;
	private static final int FIRST = 0;
	private static final int LAST = -1;

	public EventLogUI(int board_id) {

		log_zone = new JPanel();
		log_zone.setLayout(new BoxLayout(log_zone, BoxLayout.Y_AXIS));
		log_zone.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));

		scroll_pane = new JScrollPane(log_zone);

		event_menu = new EventMenu();

		this.board_id = board_id;
		EventDAO dao = new EventDAO(BddConnection.getInstance());
		events = dao.getEventsByBoard(board_id);

		// Les events sont triés du plus récent au plus vieux
		// donc on les insère chacun après tous les autres
		for (Plugins.EventLog.Event event : events)
			setup_event(event, LAST);

		log_zone.revalidate();

	}

	private void setup_event(Plugins.EventLog.Event event, int position) {

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
		ArrayList<Plugins.EventLog.Event> new_events;

		// On ne récupère que les events générés après le plus récent (premier) de ceux qu'on à déjà
		if (events.size() > 0)
			new_events = dao.getEventsByBoardAfter(board_id, events.get(0).getDate());
		else
			new_events = dao.getEventsByBoard(board_id); // Si on en avait pas, on prend tout

		System.out.println(new_events.size() + " new events found");

		// On les insère en haut de la liste d'affichage
		for (Plugins.EventLog.Event event : new_events) {
			setup_event(event, FIRST);
		}

		// Et on les insère en premier dans la mémoire
		new_events.addAll(events);
		events = new_events;

		scroll_pane.getParent().revalidate();
		scroll_pane.getParent().repaint();

	}

	public JScrollPane getScroll_pane() {

		return scroll_pane;
	}

	private void revertTo(Plugins.EventLog.Event event) {

		EventDAO dao = new EventDAO(BddConnection.getInstance());

		ArrayList<Plugins.EventLog.Event> following_events = findFollowingEvents(event);

		for (Plugins.EventLog.Event event_to_revert : following_events) {

			System.out.println("Annulation de "+event_to_revert.getReadableDescription());

			// Temporaire, revert() n'est pas implémentée pour tous les types d'events
			// Si un type n'est pas implémentée, elle renvoie false et ne change rien à la base
			// Dans ces cas-la, on ne fait rien non plus dans l'appli sur ces events
			if (dao.revert(event_to_revert) == true)
			{
				events.remove(event_to_revert);
				log_zone.remove(findLabel(event_to_revert.getId()));
			}
		}

		scroll_pane.getParent().revalidate();
		scroll_pane.getParent().repaint();

	}

	/**
	 * Récupère la liste de tous les events du journal actuel qui se sont déroulés après l'event donné en paramètre
	 * @param event L'event auquel sont postérieurs les events renvoyés par la fonction
	 * @return La liste des events de la board actuelle postérieurs à event. Liste vide si pas d'events postérieurs
	 */
	private ArrayList<Plugins.EventLog.Event> findFollowingEvents(Plugins.EventLog.Event event) {

		ArrayList<Plugins.EventLog.Event> following_events = new ArrayList<>();

		// Les events sont triés du plus récent au plus vieux
		for (Plugins.EventLog.Event event_tmp : events)
		{
			// Une fois atteint l'event ciblé, on s'arrête
			if (event_tmp.getId() == event.getId())
				break;

			// Tant qu'on a pas trouvé l'event, on ajoute à la liste
			// Vérification au cas où, le bon tri de la liste est sensé être assuré
			if (event_tmp.getDate().after(event.getDate()))
				following_events.add(event_tmp);
		}

		return following_events;
	}

	private JLabel findLabel(int id) {

		for (Component c : log_zone.getComponents())
			if (Integer.parseInt(c.getName()) == id)
				return (JLabel)c;

		return null;
	}

	private class EventMenu extends JPopupMenu {

		private Plugins.EventLog.Event event_clicked;
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
						for (Plugins.EventLog.Event event : events)
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
					Plugins.EventLog.Event clicked_event = ((EventMenu)((JMenuItem)e.getSource()).getParent()).getEventClicked();

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

		private void setEventClicked(Plugins.EventLog.Event event_clicked) {

			this.event_clicked = event_clicked;
		}

		private Plugins.EventLog.Event getEventClicked() {


			return event_clicked;
		}

		public MouseListener getLabelListener() {

			return label_listener;
		}
	}
}
