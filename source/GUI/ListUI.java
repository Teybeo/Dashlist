package GUI;

import Core.*;
import Core.List;

import javax.swing.*;
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

    public ListUI(List list, MouseListener ml){

        this.list = list;
        this.ml = ml;
        this.setBorder(new BorderUIResource.TitledBorderUIResource(list.getName()));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setName(list.getName());
        //this.setFont();
        //this.setSize(new Dimension(300,0));
        this.setPreferredSize(new Dimension(200,0));

        //this.setMinimumSize(new Dimension(100, 0));
        for (Item item : list.getItems()){
            this.add(new ItemUI(item, ml));
            //this.add(Box.createVerticalGlue());
            this.add(Box.createRigidArea(new Dimension(0, 5)));
        }

        TogglableTextInput add_item = new TogglableTextInput("Ajouter item", new AjoutItemListener());
        this.add(add_item);
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
        add(t);

        revalidate();
        repaint();

    }

}
