package GUI;

import Core.Board;
import Core.Item;
import Core.List;
import Core.User;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;
import java.awt.*;

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
		JFrame frame = new JFrame(board.getName() + " - Dashlist");
//		main = new JPanel(new BorderLayout());
		lists_zone = new JPanel();

		for (Core.List list : board.getLists())
			lists_zone.add(setup_list(list));

		horizontal_scroll = new JScrollPane(lists_zone);
		frame.add(horizontal_scroll, BorderLayout.CENTER);

//		frame.setContentPane(main);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(800, 500));
		frame.setLocationRelativeTo(null);
		frame.pack();
		frame.setVisible(true);
	}

	private JPanel setup_list(List list) {

		JPanel panel_list = new JPanel();
		panel_list.setBorder(new BorderUIResource.TitledBorderUIResource(list.getName()));
//		panel_list.add(new JLabel(list.getName()));
		panel_list.setLayout(new BoxLayout(panel_list, BoxLayout.Y_AXIS));

		for (Item item : list.getItems())
			panel_list.add(new JLabel(item.getName()));

		return panel_list;
	}

}
