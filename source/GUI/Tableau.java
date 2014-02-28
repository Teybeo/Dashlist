package GUI;

import Core.*;
import Core.List;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Date;

public class Tableau {

	private JPanel main;
	private JPanel lists_zone;
	private JScrollPane horizontal_scroll;
	private JButton button1;
	private JButton button2;
	private JPanel north;
	private JPanel center;
	private JPanel east;
	private JLabel board_title;
	private JFrame frame;
	Board board;
	User user;

	public Tableau(User user, Board board) {

		this.board = board;
		this.user = user;

		lists_zone = new JPanel();

		for (Core.List list : board.getLists())
			lists_zone.add(setup_list(list));

		horizontal_scroll = new JScrollPane(lists_zone);

		JFrame frame = new JFrame(board.getName() + " - Dashlist");
		frame.add(horizontal_scroll, BorderLayout.CENTER);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(800, 500));
		frame.setLocationRelativeTo(null);
		frame.pack();
		frame.setVisible(true);
	}

	private JPanel setup_list(List list) {

		JPanel panel_list = new JPanel();
		panel_list.setBorder(new BorderUIResource.TitledBorderUIResource(list.getName()));
		panel_list.setLayout(new BoxLayout(panel_list, BoxLayout.Y_AXIS));
		panel_list.setName(list.getName());

		for (Item item : list.getItems())
			panel_list.add(new JLabel(item.getName()));

		JButton add_item = new JButton("Ajouter");
		add_item.addActionListener(new AjoutItemListener());
		panel_list.add(add_item);

		return panel_list;
	}

	// Lorsqu'on clique sur le bouton Ajouter, celui-ci disparaît et est remplacé par un champ de texte
	// Un nouvel item est créé lorsque l'utilisateur rentre du texte et appuie sur Entrée
	// Si le champ de texte perd le focus, aucun nouvel item n'est créé et le bouton Ajouter revient
	private void presentItemNameInput(JButton btn_additem) {

		btn_additem.setVisible(false);
		JPanel list = (JPanel)btn_additem.getParent();
		JTextArea text_input = new JTextArea(1, 2);
		text_input.setWrapStyleWord(true);
		text_input.setLineWrap(true);

		// Quand on perd le focus, on supprime le textarea et on remet un bouton Ajouter
		text_input.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {

				super.focusLost(e);
				System.out.println("Textarea lost focus");
				JTextArea textarea = (JTextArea)(e.getSource());
				JPanel panel_list = (JPanel)(textarea.getParent());

				// Quand la création est validée, le textarea est enlevé du panel et une perte de focus est lancée
				// Si on est dans ce cas-là on s'arrête tout de suite
				if (panel_list == null)
					return;

				panel_list.remove(textarea);

				JButton add_item = new JButton("Ajouter");
				add_item.addActionListener(new AjoutItemListener());
				panel_list.add(add_item);
			}
		});

		String keyStrokeAndKey = "ENTER";
		KeyStroke keyStroke = KeyStroke.getKeyStroke(keyStrokeAndKey);
		text_input.getInputMap(JComponent.WHEN_FOCUSED).put(keyStroke, keyStrokeAndKey);
		text_input.getActionMap().put(keyStrokeAndKey, new AddItemAction());
		list.add(text_input);
		text_input.requestFocusInWindow();

	}

	private class AddItemAction	extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {

			System.out.println("Enter pressed");
			JTextArea textarea = (JTextArea)e.getSource();
			JPanel panel_list = (JPanel)textarea.getParent();
			String item_text = textarea.getText();
			String list_name = panel_list.getName();

			ItemDAO dao = new ItemDAO(BddConnection.getInstance());

			List list = null;
			for (List l : board.getLists())
				if (l.getName().equals(list_name))
					list = l;

			int position = -1;
			position = list.getItems().size() + 1;


			Item item = new Item(textarea.getText(), position);
			list.getItems().add(item);
			dao.add(item, list.getId());

			panel_list.remove(textarea);
			panel_list.add(new JLabel(item.getName()));

			JButton add_item = new JButton("Ajouter");
			add_item.addActionListener(new AjoutItemListener());
			panel_list.add(add_item);

			panel_list.revalidate();
			panel_list.repaint();
		}

	};

	private class AjoutItemListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {

			System.out.println("Ajouter button pressed");
			presentItemNameInput(((JButton) e.getSource()));
		}
	}

}
