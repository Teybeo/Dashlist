package GUI;

import Core.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;

/**
 * Created by Guillaume on 23/04/14.
 */
public class ItemUI extends JLabel{

    private Item item;

    public ItemUI(Item item, MouseListener ml){
        super(item.getName());
        //JLabel label = new JLabel(item.getName());
        this.setOpaque(true);
        this.setBackground(Color.RED);
        this.addMouseListener(ml);
    }
}
