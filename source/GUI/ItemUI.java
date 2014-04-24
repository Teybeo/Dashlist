package GUI;

import Core.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;

/**
 * Created by Guillaume on 23/04/14.
 */
public class ItemUI extends JPanel{

    private Item item;
    private JTextArea label;

    public ItemUI(Item item, MouseListener ml) {

        this.item = item;
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

    public JTextArea getLabel() {
        return label;
    }

    public Item getItem() {
        return item;
    }
}
