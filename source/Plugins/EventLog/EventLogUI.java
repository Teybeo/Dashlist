package Plugins.EventLog;

import Core.*;
import Core.List;
import PluginSystem.PluginInterface;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;

public class EventLogUI implements PluginInterface, Observer {

	private JPanel log_zone;
	private JScrollPane scroll_pane;
	EventLog log;
	EventLogController controller;
	private EventMenu event_menu;
	private static final int FIRST = 0;
	private static final int LAST = -1;

	public EventLogUI() {

		log_zone = new JPanel();
		log_zone.setLayout(new BoxLayout(log_zone, BoxLayout.Y_AXIS));
		log_zone.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));

		scroll_pane = new JScrollPane(log_zone);

		event_menu = new EventMenu();
	}

	 /**
	 *	Va chercher les nouveaux events et met à jour l'affichage
	 *//*
	public void refresh() {

		EventDAO dao = new EventDAO(BddConnection.getInstance());
		ArrayList<Plugins.Plugins.EventLog.Event> new_events;

		// On ne récupère que les events générés après le plus récent (premier) de ceux qu'on à déjà
		if (events.size() > 0)
			new_events = dao.getEventsByBoardAfter(board_id, events.get(0).getDate());
		else
			new_events = dao.getEventsByBoard(board_id); // Si on en avait pas, on prend tout

		System.out.println(new_events.size() + " new events found");

		// On les insère en haut de la liste d'affichage
		for (Plugins.Plugins.EventLog.Event event : new_events) {
			setup_event(event, FIRST);
		}

		// Et on les insère en premier dans la mémoire
		new_events.addAll(events);
		events = new_events;

		scroll_pane.getParent().revalidate();
		scroll_pane.getParent().repaint();

	}*/

	private JLabel findLabel(int id) {

		for (Component c : log_zone.getComponents())
			if (Integer.parseInt(c.getName()) == id)
				return (JLabel)c;

		return null;
	}

	@Override
	public void acquireBoard(Board board) {

		System.out.println("EventLog Plugin acquired board: " + board.getName());

		log = new EventLog(board);

		controller = new EventLogController(log, BddConnection.getInstance());

		controller.loadEvents();

		board.addObserver(this);
		log.addObserver(this);

		log_zone.removeAll();

		// Les events sont triés du plus récent au plus vieux
		// donc on les insère chacun après tous les autres
		for (Event event : log.getEvents())
			log_zone.add(new EventUI(event), LAST);

		log_zone.revalidate();
	}

	@Override
	public String getTargetContainer() {

		return "GUI.BoardUI:frame";
	}

	@Override
	public void acquireContainer(Container container) {

		System.out.println("EventLog Plugin acquired container");

		container.add(log_zone, BorderLayout.EAST);
		container.revalidate();
		container.repaint();
	}

	@Override
	public void update(Observable o, Object arg) {

		String sender = o.getClass().getName();
		String message = (String)arg;
		System.out.println("EventLogUI received ["+ arg +"] from ["+ sender +"]");

		if (sender.equals("Core.Board"))
		{
			if (message.startsWith("List added: "))
			{
				List list = log.getBoard().getListById(Integer.parseInt(message.replace("List added: ", "")));
				controller.addListEvent(null, list);
			}
			if (message.startsWith("Item added: "))
			{
				message = message.replace("Item added: ", "");

				String[] array = message.split(" in: ");

				List list = log.getListById(Integer.parseInt(array[1]));
				Item item = list.getItemById(Integer.parseInt(array[0]));

				controller.addItemEvent(null, item, list);
			}
			if (message.startsWith("Item deleted: "))
			{
				message = message.replace("Item deleted: ", "");

				String[] array = message.split(" from: ");

				List list = log.getListById(Integer.parseInt(array[1]));
				Item item = list.getItemById(Integer.parseInt(array[0]));

				controller.addItemEvent(item, null, list);
			}
		}
		else if (sender.equals("Plugins.EventLog.EventLog"))
		{
			if (message.startsWith("Event added: ")) {

				Event event = log.getEventById(Integer.parseInt(message.replace("Event added: ", "")));

				log_zone.add(new EventUI(event), FIRST);
				log_zone.revalidate();
				log_zone.repaint();
			}
			if (message.startsWith("Event deleted: ")) {

				Event event = log.getEventById(Integer.parseInt(message.replace("Event deleted: ", "")));

				log_zone.remove(findLabel(event.getId()));
				log_zone.revalidate();
				log_zone.repaint();
			}


		}

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

						// HACK: On récupère l'id de l'event stocké dans le name du JLabel
						// La bonne façon serait d'avoir une classe EventUI stockant l'event
						int id = Integer.parseInt(e.getComponent().getName());

						// On récupère l'event et le garde en mémoire pour les actions du menu
						setEventClicked(log.getEventById(id));
						show(e.getComponent(), e.getX(), e.getY());

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
						controller.revertTo(clicked_event);
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
