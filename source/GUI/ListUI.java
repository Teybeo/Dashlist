package GUI;

import Core.BddConnection;
import Core.Item;
import Core.List;
import Core.ListDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Guillaume on 23/04/14.
 */
class ListUI extends JPanel implements Observer{

    private List list;
    private MouseListener ml;
    private JPanel panelTitle;
    private JLabel title;
	private TogglableTextInput add_item;
	private static ListMenu listMenu = new ListMenu();
	//private int cpt;

    public ListUI(List list, MouseListener ml){

        this.list = list;
	    this.list.addObserver(this);
        this.ml = ml;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setPreferredSize(new Dimension(200, 50));
        this.setMinimumSize(new Dimension(200, 50));
        this.setBackground(new Color(224, 224, 224));
        this.setAlignmentY(Component.TOP_ALIGNMENT);
        this.setBorder(new EmptyBorder(8, 8, 8, 8));
		this.addMouseListener(listMenu.mouseListener);

        panelTitle = new JPanel();
        panelTitle.setLayout(new BoxLayout(panelTitle, BoxLayout.Y_AXIS));
        panelTitle.setAlignmentX(Component.RIGHT_ALIGNMENT);
        panelTitle.setBackground(this.getBackground());
        panelTitle.setMaximumSize(new Dimension(200, 30));

        title = new JLabel(list.getName());
        title.setFont(new Font("Arial", Font.BOLD, 16));
        panelTitle.add(title);
        this.add(panelTitle);

        //cpt = 0;
        for (Item item : list.getItems()){
            this.add(new ItemUI(item, ml));
            this.add(Box.createRigidArea(new Dimension(0, 5)));
            //cpt++;
        }

        //this.setMaximumSize(new Dimension(200,cpt*55+60));

	    add_item = new TogglableTextInput("Ajouter item", new AjoutItemListener());
        this.add(add_item);

        //this.revalidate();
    }

	List getList() {

		return list;
	}

	private class AjoutItemListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {

			String item_name = ((TogglableTextInput)e.getSource()).getText();

			ListDAO dao = new ListDAO(BddConnection.getInstance());

			dao.addItem(list, item_name);
		}
	}

    @Override protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
        revalidate();
	}


	@Override
	public void update(Observable o, Object arg) {

		String sender = o.getClass().getName();

		System.out.println("ListUI received ["+ arg +"] from ["+ sender +"]");

		if (sender.equals("Core.List"))
		{
			String message = ((String)arg);

			if (message.startsWith("Item added"))
			{
				message = message.replace(" (by eventlog)", "");
				String[] array = message.replace("Item added: ", "").split(" in: ");

				Item item = list.getItemById(Integer.parseInt(array[0]));

				remove(add_item);
				add(new ItemUI(item, ml), item.getPosition());
				this.add(Box.createRigidArea(new Dimension(0, 5)));
				add(add_item);

				revalidate();
				repaint();
			}
			else if (message.startsWith("List deleted")) {
				Container parent = getParent();
				parent.remove(this);
				parent.revalidate();
				parent.repaint();
			}

		}
	}

	private static class ListMenu extends JPopupMenu {

		private static ListUI clicked_list;
		private MouseAdapter mouseListener;

		public ListMenu() {

			JMenuItem delete_list = new JMenuItem("Supprimer");
			delete_list.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

					if (clicked_list == null)
						System.out.println("La liste sélectionnée était null");
					else {
						ListDAO dao = new ListDAO(BddConnection.getInstance());
						dao.delete(clicked_list.getList(), List.Action_Source.USER);
					}
				}
			});

			add(delete_list);

			mouseListener = new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {

					super.mouseReleased(e);

					if (e.isPopupTrigger()) {
						// On récupère le ListUI sur lequel on a cliqué
						clicked_list = (ListUI) e.getComponent();
						show(e.getComponent(), e.getX(), e.getY());
					}
				}
			};
		}
	}
}
