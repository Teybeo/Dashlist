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
    private JTextArea text;

    public ItemUI(Item item, MouseListener ml) {

        this.item = item;
        text = new JTextArea(item.getName());
        text.setEditable(false);
        text.setFocusable(false);
        text.setLineWrap(true);
        text.setFont(new Font("Arial", Font.PLAIN, 16));
        text.setSize(180, 50);

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

    public JTextArea getLabel() {
        return text;
    }

    public Item getItem() {
        return item;
    }
}
