package GUI;

import Core.*;
import Core.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
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

		for (Core.List list : board.getLists())
			setup_list(list);

		create_empty_list();

		horizontal_scroll = new JScrollPane(lists_zone);

		frame = new JFrame(board.getName() + " - Dashlist");
		frame.add(horizontal_scroll, BorderLayout.CENTER);

		buildMenuBar();
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
			panel_list.add(new JLabel(item.getName()));

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
		dao.add(item, list.getName());

		panel_list.remove(t);
		panel_list.add(new JLabel(item.getName()));

		panel_list.add(t);

		panel_list.revalidate();
		panel_list.repaint();

	}

	private void AddListAction(TogglableTextInput t) {

		JPanel panel_list = (JPanel)t.getParent();

		ListDAO dao = new ListDAO(BddConnection.getInstance());

		int position = -1;
		position = board.getLists().size() + 1;

		// On crée la nouvelle liste et on l'entre dans la base
		List liste = new List(t.getText(), position);
		board.getLists().add(liste);
		dao.add(liste, board.getId());

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

	private void closeBoard() {

		frame.setVisible(false);
		setChanged();
		notifyObservers(user);
		clearChanged();
	}
}
