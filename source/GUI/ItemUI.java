package GUI;

import Core.BddConnection;
import Core.CurrentUser;
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
public class ItemUI extends JPanel implements Observer {

    private Item item;
    private JTextArea label;

    public ItemUI(Item item, MouseListener ml) {

        this.item = item;
	    item.addObserver(this);

        label = new JTextArea(item.getName());
        label.setEditable(false);
        label.setFocusable(false);
        label.setLineWrap(true);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        this.setPreferredSize(new Dimension(200, 50));
        this.setMaximumSize(new Dimension(200, 50));
        this.setOpaque(true);
        this.setBackground(new Color(255, 255, 255));
        //this.setBorder(BorderFactory.createLineBorder(Color.black));

	    /* On ajoute l'écouteur sur le panel et sur le label
	    pour que le clic-droit fonctionne sur toute la zone de l'item*/
        this.addMouseListener(ml);
	    label.addMouseListener(ml);
        this.add(label);
    }

    @Override protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
        label.repaint();
    }

	public void SupprItemAction() {

		ItemDAO dao = new ItemDAO(BddConnection.getInstance());

		dao.delete(getItem(), CurrentUser.getInstance().getId());

	}

    public JTextArea getLabel() {
        return label;
    }

    public Item getItem() {
        return item;
    }

	@Override
	public void update(Observable o, Object arg) {

		String sender = o.getClass().getName();

		System.out.println("ItemUI received ["+ arg +"] from ["+ o +"]");

		if (sender.equals("Core.Item"))
		{
			String param = ((String)arg);
			if (param.equals("Item deleted"))
			{
				Container parent = getParent();
				parent.remove(this);
				parent.revalidate();
				parent.repaint();
			}
		}
	}
}
