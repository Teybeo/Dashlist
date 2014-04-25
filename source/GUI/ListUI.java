package GUI;

import Core.*;
import Core.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

/**
 * Created by Guillaume on 23/04/14.
 */
public class ListUI extends JPanel{

    private List list;
    private MouseListener ml;
    private JPanel panelTitle;
    private JLabel title;

    public ListUI(List list, MouseListener ml){

        this.list = list;
        this.ml = ml;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setPreferredSize(new Dimension(200, 50));
        this.setMinimumSize(new Dimension(200, 50));
        this.setBackground(new Color(224, 224, 224));
        this.setAlignmentY(Component.TOP_ALIGNMENT);
        this.setBorder(new EmptyBorder(8, 8, 8, 8));

        panelTitle = new JPanel();
        panelTitle.setLayout(new BoxLayout(panelTitle, BoxLayout.Y_AXIS));
        panelTitle.setAlignmentX(Component.RIGHT_ALIGNMENT);
        panelTitle.setBackground(this.getBackground());
        panelTitle.setMaximumSize(new Dimension(200, 30));

        title = new JLabel(list.getName());
        title.setFont(new Font("Arial", Font.BOLD, 16));
        panelTitle.add(title);
        this.add(panelTitle);

        for (Item item : list.getItems()){
            this.add(new ItemUI(item, ml));
            this.add(Box.createRigidArea(new Dimension(0, 5)));
        }

        TogglableTextInput add_item = new TogglableTextInput("Ajouter item", new AjoutItemListener());
        this.add(add_item);

        this.revalidate();
    }

    private class AjoutItemListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {

            AddItemAction((TogglableTextInput)e.getSource());
        }
    }

    /* Est appellée lorsque le togglableTextInput est validé
        Rajoute le nouvel item dans la base et dans l'interface
    */
    private void AddItemAction(TogglableTextInput t) {

        ItemDAO dao = new ItemDAO(BddConnection.getInstance());

        int position = -1;
        position = list.getItems().size() + 1;

        Item item = new Item(t.getText(), position);
        list.getItems().add(item);
        dao.add(item, list.getId(), CurrentUser.getInstance().getId());
        //event_log.refresh();

        remove(t);
        add(new ItemUI(item, ml));
        this.add(Box.createRigidArea(new Dimension(0, 5)));
        add(t);

        revalidate();
        repaint();

    }

    @Override protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
    }

}
