package GUI;

import Core.*;
import Core.List;
import PluginSystem.PluginInterface;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;

public class Tableau extends Observable implements Observer{

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
	private MouseListener label_menu_listener;
	private ItemMenu item_menu;
	Board board;

	public Tableau(Board board, PluginInterface[] plugins) {

		this.board = board;
		this.board.addObserver(this);

		lists_zone = new JPanel();
		lists_zone.setLayout(new BoxLayout(lists_zone, BoxLayout.X_AXIS));
		lists_zone.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
        lists_zone.setBackground(new Color(100,150,200));

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

				// On vérifie que c'était un clic sensé ouvrir un menu
                if (e.isPopupTrigger())
                {
					String class_src = e.getSource().getClass().getName();
					if (class_src.equals("GUI.ItemUI") || class_src.equals("javax.swing.JTextArea"))
					{
						if (class_src.equals("GUI.ItemUI"))
							item_menu.setClickedItem((ItemUI) e.getSource());
						if (class_src.equals("javax.swing.JTextArea"))
							item_menu.setClickedItem((ItemUI) (e.getComponent().getParent()));

						item_menu.show(e.getComponent(), e.getX(), e.getY());
					}
				}
			}
		};

		item_menu = new ItemMenu();

		for (Core.List list : board.getLists())
		{
			list.addObserver(this);
			//setup_list(list);
            lists_zone.add(new ListUI(list, label_menu_listener));
            lists_zone.add(Box.createRigidArea(new Dimension(10, 0)));
        }

		create_empty_list();

		horizontal_scroll = new JScrollPane(lists_zone);

		frame = new JFrame(board.getName() + " - Dashlist");
		frame.add(horizontal_scroll, BorderLayout.CENTER);

		for (PluginInterface plugin : plugins)
		{
			String[] target = plugin.getTargetContainer().split("[:]");

			// On teste si le plugin veut s'attacher à un container de cette classe
			if (target[0].equals(this.getClass().getName()))
			{
				try {
					plugin.acquireContainer(((Container) (getClass().getDeclaredField(target[1]).get(this))));
				} catch (NoSuchFieldException e) {
					e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
				} catch (IllegalAccessException e) {
					e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
				}

			}
		}

		buildMenuBar();
		frame.setIconImage(frame.getToolkit().getImage("icon2.png"));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension current_resolution = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setMinimumSize(new Dimension((int)(current_resolution.width*0.5), (int)(current_resolution.height*0.5)));
		frame.setPreferredSize(new Dimension((int)(current_resolution.width*0.7), (int)(current_resolution.height*0.7)));
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private void create_empty_list() {

		empty_list = new JPanel();
		empty_list.setLayout(new BoxLayout(empty_list, BoxLayout.Y_AXIS));
		empty_list.setName("vide");
        empty_list.setAlignmentY(Component.TOP_ALIGNMENT);

		TogglableTextInput add_list = new TogglableTextInput("Ajouter Liste", new AjoutListeListener());
		empty_list.add(add_list);

		lists_zone.add(empty_list);
	}

	@Override
	public void update(Observable o, Object arg) {

		String sender = o.getClass().getName();

		System.out.println("Tableau received: "+arg+" from: "+sender);

		if (((String)arg).startsWith("List added"))
		{
			// Au lieu de recevoir la notification de board, il vaudrait peut-être mieux la
			// recevoir directement de la liste créée pour éviter à avoir à faire ca :
			Board board = (Board)o;
			List list = board.getListById(Integer.parseInt(((String)arg).replace("List added:", "")));

			// 1. On enlève la liste vide
			// 2. On ajoute la nouvelle liste
			// 3. On remet la liste vide
			lists_zone.remove(empty_list);
			lists_zone.add(new ListUI(list, label_menu_listener));
			lists_zone.add(empty_list);

			lists_zone.revalidate();
			lists_zone.repaint();
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
				clicked_item.SupprItemAction();
		}
	}

	private class AjoutListeListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {

			String list_name = ((TogglableTextInput)e.getSource()).getText();

			BoardDAO dao = new BoardDAO(BddConnection.getInstance());

			dao.addList(board, list_name);
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
		notifyObservers();
		clearChanged();
	}
}
