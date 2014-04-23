package GUI;

import Core.*;
import Core.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
import java.awt.event.*;
import java.util.Observable;

public class Tableau extends Observable {

	private JPanel main;
	private JPanel lists_zone;
	private JPanel empty_list;
	private JScrollPane horizontal_scroll;
	private JButton button1;
	private JButton button2;
	private JPanel north;
	private JPanel center;
	private JPanel east;
	private JLabel board_title;
	private JFrame frame;
	private EventLog event_log;
	private MouseListener label_menu_listener;
	private ItemMenu item_menu;
	Board board;
	User user;

	public Tableau(User user, Board board) {

		this.board = board;
		this.user = user;

		lists_zone = new JPanel();
		lists_zone.setLayout(new BoxLayout(lists_zone, BoxLayout.X_AXIS));
		lists_zone.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));

		// HACK: Pour defocuser les textinput si on clique ailleurs,
		// on vole le focus si on clique sur le panel principal
		lists_zone.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				lists_zone.grabFocus();
			}
		});

		label_menu_listener = new MouseAdapter()
		{
			@Override
			public void mouseReleased(MouseEvent e)
			{
				super.mouseReleased(e);    //To change body of overridden methods use File | Settings | File Templates.

                if (e.isPopupTrigger()) {
					if (e.getSource().getClass().getName().equals("GUI.ItemUI"))
					{
						item_menu.setClickedItem((ItemUI) e.getSource());
						item_menu.show(e.getComponent(), e.getX(), e.getY());
					}
				}
			}
		};

		item_menu = new ItemMenu();

		for (Core.List list : board.getLists())
			setup_list(list);

		create_empty_list();

		horizontal_scroll = new JScrollPane(lists_zone);

		frame = new JFrame(board.getName() + " - Dashlist");
		frame.add(horizontal_scroll, BorderLayout.CENTER);

		event_log = new EventLog(board.getId());
		frame.add(event_log.getScroll_pane(), BorderLayout.EAST);

		buildMenuBar();
		frame.setIconImage(frame.getToolkit().getImage("icon2.png"));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(800, 500));
		frame.setLocationRelativeTo(null);
		frame.pack();
		frame.setVisible(true);
	}

	private void create_empty_list() {

		empty_list = new JPanel();
		empty_list.setLayout(new BoxLayout(empty_list, BoxLayout.Y_AXIS));
		empty_list.setName("vide");

		TogglableTextInput add_list = new TogglableTextInput("Ajouter Liste", new AjoutListeListener());
		empty_list.add(add_list);

		lists_zone.add(empty_list);
	}

	private void setup_list(List list) {

		JPanel panel_list = new JPanel();
		panel_list.setBorder(new BorderUIResource.TitledBorderUIResource(list.getName()));
		panel_list.setLayout(new BoxLayout(panel_list, BoxLayout.Y_AXIS));
		panel_list.setName(list.getName());
		panel_list.setMinimumSize(new Dimension(100, 0));

		for (Item item : list.getItems())
            panel_list.add(new ItemUI(item, label_menu_listener));

		TogglableTextInput add_item = new TogglableTextInput("Ajouter item", new AjoutItemListener());
		panel_list.add(add_item);

		lists_zone.add(panel_list);
	}

	private void AddItemAction(TogglableTextInput t) {

		JPanel panel_list = (JPanel)t.getParent();

		ItemDAO dao = new ItemDAO(BddConnection.getInstance());

		List list = board.getListByName(panel_list.getName());

		int position = -1;
		position = list.getItems().size() + 1;

		Item item = new Item(t.getText(), position);
		list.getItems().add(item);
		dao.add(item, list.getId(), user.getId());
		event_log.refresh();

		panel_list.remove(t);
        panel_list.add(new ItemUI(item, label_menu_listener));
		panel_list.add(t);

		panel_list.revalidate();
		panel_list.repaint();

	}

	private void SupprItemAction(ItemUI source) {

        if(source.getItem() == null)
            System.out.println("bhbhbvhvlvuvuvulvlhl");
        JPanel panel_list = (JPanel)source.getParent();

		ItemDAO dao = new ItemDAO(BddConnection.getInstance());

		List list = board.getListByName(panel_list.getName());

		//Item item = list.getItem(source.getText());
		list.getItems().remove(source.getItem());
		dao.delete(source.getItem(), user.getId());
		event_log.refresh();

		panel_list.remove(source);

		panel_list.revalidate();
		panel_list.repaint();
	}

	private void AddListAction(TogglableTextInput t) {

		JPanel panel_list = (JPanel)t.getParent();

		ListDAO dao = new ListDAO(BddConnection.getInstance());

		int position = board.getLists().size() + 1;

		// On crée la nouvelle liste et on l'entre dans la base
		List liste = new List(t.getText(), position);
		board.getLists().add(liste);
		dao.add(liste, board.getId(), user.getId());
		event_log.refresh();

		// 1. On enlève la liste vide
		// 2. On ajoute la nouvelle liste
		// 3. On remet la liste vide
		lists_zone.remove(panel_list);
		setup_list(liste);
		lists_zone.add(panel_list);

		lists_zone.revalidate();
		lists_zone.repaint();
	}

	private class AjoutItemListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {

			AddItemAction((TogglableTextInput)e.getSource());
		}
	}
	private class SupprItemListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {

			ItemUI clicked_item = ((ItemMenu)((JMenuItem)e.getSource()).getParent()).getClicked_item();

			if (clicked_item == null)
				System.out.println("Le clicked item était null");
			else
				SupprItemAction(clicked_item);
		}
	}

	private class AjoutListeListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {

			AddListAction((TogglableTextInput)e.getSource());
		}
	}

	private void buildMenuBar() {

		JMenuBar menu_bar = new JMenuBar();
		JMenu board_menu = new JMenu("Board");
		JMenuItem accueil = new JMenuItem("Retour à l'accueil");

		accueil.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				closeBoard();
			}
		});
		board_menu.add(accueil);
		menu_bar.add(board_menu);

		frame.setJMenuBar(menu_bar);
	}
	
	private class ItemMenu extends JPopupMenu {

		private ItemUI clicked_item;

		public ItemMenu() {

			super("Menu");
			JMenuItem delete_item = new JMenuItem("Supprimer");
			delete_item.addActionListener(new SupprItemListener());

			add(delete_item);
		}

		private ItemUI getClicked_item() {

			return clicked_item;
		}

		public void setClickedItem(ItemUI clicked_item) {

			this.clicked_item = clicked_item;
		}
	}

	private void closeBoard() {

		frame.setVisible(false);
		setChanged();
		notifyObservers(user);
		clearChanged();
	}
}
