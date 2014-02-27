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
	private void presentItemNameInput(JButton source) {

		source.setVisible(false);
		JPanel list = (JPanel)source.getParent();
		JTextArea text_input = new JTextArea(1, 2);
		text_input.setWrapStyleWord(true);
		text_input.setLineWrap(true);
		Action action = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {

				System.out.println("Enter pressed");
				JTextArea source = (JTextArea)e.getSource();
				String item_text = source.getText();
				String list_name = (source.getParent()).getName();
				System.out.println(list_name);

				ItemDAO dao = new ItemDAO(BddConnection.getInstance());

				List list = null;
				for (List l : board.getLists())
					if (l.getName().equals(list_name))
						list = l;

				int position = -1;
				position = list.getItems().size() + 1;

				dao.add(source.getText(), list.getId(), position);
			}

			};

		text_input.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {

				super.focusLost(e);    //To change body of overridden methods use File | Settings | File Templates.
				JTextArea source = (JTextArea)(e.getSource());
				JPanel panel_list = (JPanel)(source.getParent());

				panel_list.remove(source);

				JButton add_item = new JButton("Ajouter");
				add_item.addActionListener(new AjoutItemListener());
				panel_list.add(add_item);
			}
		});

		String keyStrokeAndKey = "ENTER";
		KeyStroke keyStroke = KeyStroke.getKeyStroke(keyStrokeAndKey);
		text_input.getInputMap(JComponent.WHEN_FOCUSED).put(keyStroke, keyStrokeAndKey);
		text_input.getActionMap().put(keyStrokeAndKey, action);
		list.add(text_input);
		text_input.requestFocusInWindow();

	}

	private class AjoutItemListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {

			System.out.println("New item");
			presentItemNameInput(((JButton) e.getSource()));
		}
	}

}
