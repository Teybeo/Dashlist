package GUI;

import Core.BddConnection;
import Core.Item;
import Core.ItemDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Guillaume on 23/04/14.
 */
class ItemUI extends JPanel implements Observer {

    private Item item;
    private JTextArea text;

    public ItemUI(Item item, MouseListener ml) {

        this.item = item;

	    item.addObserver(this);

	    text = new JTextArea(item.getName());
        text.setEditable(false);
        text.setFocusable(false);
        text.setLineWrap(true);
        text.setFont(new Font("Arial", Font.PLAIN, 16));
        text.setSize(180, 50);

        this.setMinimumSize(new Dimension(200, 50));
        this.setPreferredSize(new Dimension(200, 50));
        this.setMaximumSize(new Dimension(200, 50));
        this.setOpaque(true);
        this.setBackground(new Color(255, 255, 255));

        /*On ajoute l'écouteur sur le panel et sur le label
	    pour que le clic-droit fonctionne sur toute la zone de l'item*/
        this.addMouseListener(ml);
	    text.addMouseListener(ml);
        this.add(text);
    }

    @Override protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
        text.repaint();
    }

	public void SupprItemAction() {

		ItemDAO dao = new ItemDAO(BddConnection.getInstance());

		dao.delete(item, Item.Action_Source.USER);

	}

    Item getItem() {
        return item;
    }

	@Override
	public void update(Observable o, Object arg) {

		String sender = o.getClass().getName();

		System.out.println("ItemUI received ["+ arg +"] from ["+ sender +"]");

		if (sender.equals("Core.Item"))
		{
			String param = ((String)arg);
			if (param.startsWith("Item deleted"))
			{
				Container parent = getParent();
				parent.remove(this);
				parent.revalidate();
				parent.repaint();
			}
		}
	}
}
