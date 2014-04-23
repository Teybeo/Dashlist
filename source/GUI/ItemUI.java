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
    private JLabel label;

    public ItemUI(Item item, MouseListener ml) {

        this.item = item;
        label = new JLabel(item.getName());
        this.setOpaque(true);
        this.setBackground(Color.RED);
        this.addMouseListener(ml);
        this.add(label);
    }

    @Override protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
        label.repaint();

    }

    public JLabel getLabel() {
        return label;
    }

    public Item getItem() {
        return item;
    }
}
