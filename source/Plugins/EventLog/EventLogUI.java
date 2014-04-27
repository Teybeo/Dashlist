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
	private static EventMenu event_menu;
	private static final int FIRST = 0;
	private static final int LAST = -1;

	public EventLogUI() {

		log_zone = new JPanel();
		log_zone.setLayout(new BoxLayout(log_zone, BoxLayout.Y_AXIS));
		log_zone.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));

		scroll_pane = new JScrollPane(log_zone, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll_pane.getVerticalScrollBar().setUnitIncrement(14);
	}

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

		event_menu = new EventMenu(controller);

		// Les events sont triés du plus récent au plus vieux
		// donc on les insère chacun après tous les autres
		for (Event event : log.getEvents())
			log_zone.add(new EventUI(event, event_menu.mouse_listener), LAST);

		log_zone.revalidate();
	}

	@Override
	public String getTargetContainer() {

		return "GUI.BoardUI:frame";
	}

	@Override
	public void acquireContainer(Container container) {

		System.out.println("EventLog Plugin acquired container");

		container.add(scroll_pane, BorderLayout.EAST);
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
			else if (message.startsWith("List deleted: "))
			{
				message = message.replace("List deleted: ", "");

				List list = log.getListById(Integer.parseInt(message));

				controller.addListEvent(list, null);
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

				log_zone.add(new EventUI(event, event_menu.mouse_listener), FIRST);
				log_zone.revalidate();
				log_zone.repaint();
			}
		}
	}

	private static class EventMenu extends JPopupMenu {

		private EventUI clicked_event;
		private static MouseAdapter mouse_listener;
		private EventLogController controller;

		public EventMenu(final EventLogController controller) {

			this.controller = controller;

			JMenuItem revert_option = new JMenuItem("Revenir à ce point");
			// Cet écouteur se déclenche quand on clique sur l'option "Revenir à ce point" du menu
			revert_option.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

					if (clicked_event == null)
						System.out.println("Le clicked label était null");
					else
						controller.revertTo(clicked_event.getEvent());
				}
			});
			add(revert_option);

			// Ecouteur de clics de souris pour afficher le menu contextuel
			mouse_listener = new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					super.mouseReleased(e);

					if (e.isPopupTrigger()) {
						// On récupère l'event et le garde en mémoire pour les actions du menu
						clicked_event = (EventUI)e.getComponent();
						show(e.getComponent(), e.getX(), e.getY());
					}
				}
			};

		}

	}

}
