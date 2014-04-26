package GUI;

import Core.*;
import PluginSystem.PluginInterface;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Accueil implements Observer {

	private JFrame frame;
	private JButton creerUnProjetButton;
	private JPanel panel;
	private JPanel panel_list;
	private JLabel label;
	ArrayList<Board> boards;
	PluginInterface[] plugins;

	public Accueil(PluginInterface[] plugins) {

		frame = new JFrame("Accueil - Dashlist");
		this.plugins = plugins;

		panel_list = new RoundedPanel(1, true);
		panel = new JPanel();
		label = new JLabel("Mes projets");
		label.setFont(new Font("Arial", Font.BOLD, 16));

		panel.setLayout(new BoxLayout(panel , BoxLayout.X_AXIS));
		panel_list.setLayout(new BoxLayout(panel_list, BoxLayout.Y_AXIS));
		panel_list.setMaximumSize(new Dimension(200, 200));

		creerUnProjetButton = new JButton();
		creerUnProjetButton.setText("Créer un projet");

		panel_list.add(Box.createRigidArea(new Dimension(30, 10)));
		panel_list.add(label);
		panel.add(Box.createRigidArea(new Dimension(30, 0)));
		panel.add(panel_list);
		panel.add(Box.createRigidArea(new Dimension(50, 0)));
		panel.add(creerUnProjetButton);
		panel.add(Box.createRigidArea(new Dimension(10, 0)));
		panel_list.add(Box.createRigidArea(new Dimension(0, 10)));

		panel.setBackground(new Color(100,150,200));
		panel_list.setBackground(new Color(224, 224, 224));

		frame.setIconImage(frame.getToolkit().getImage("icon2.png"));
		frame.setContentPane(panel);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(450, 350));
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(false);
	}

	/**
	 *
	 */
	public void loadAccount() {

		BoardDAO dao = new BoardDAO(BddConnection.getInstance());

		boards = dao.getUserBoards(CurrentUser.getInstance().getId());

		for (Board board : boards)
			setupBoard(board);

		TogglableTextInput add_board = new TogglableTextInput("Nouveau tableau", new NewBoardActionListener());
		panel_list.add(Box.createRigidArea(new Dimension(0, 10)));
		panel_list.add(add_board);
		add_board.setAlignmentX(Component.LEFT_ALIGNMENT);

		frame.pack();
		frame.setVisible(true);
	}

	private void setupBoard(Board board) {

		JLabel tmp = new JLabel(board.getName());
		tmp.setCursor(new Cursor(Cursor.HAND_CURSOR));
		tmp.addMouseListener(new boardClicked());
		panel_list.add(Box.createRigidArea(new Dimension(0, 5)));
		panel_list.add(tmp);
	}

	public void launchBoard(Board board) {

		frame.setVisible(false);

		// On charge les listes du tableau avant de lancer la fenêtre tableau
		BoardDAO dao = new BoardDAO(BddConnection.getInstance());
		dao.loadLists(board);

		if (plugins != null)
		for (PluginInterface plugin : plugins)
			plugin.acquireBoard(board);

		BoardUI current = new BoardUI(board, plugins);
		current.addObserver(this);

		System.out.println("Board " + board.getName() + " Loaded");
	}

	/**
>>>>>>> 5fe83fa38ec11c70fd35e37e3840ec6d1d0946d6
	 * @noinspection ALL
	 */
	public JComponent $$$getRootComponent$$$() {

		return panel;
	}

	private class NewBoardActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			TogglableTextInput t = (TogglableTextInput) e.getSource();

			BoardDAO dao = new BoardDAO(BddConnection.getInstance());

			// On crée le nouveau tableau et on l'entre dans la base
			Board board = new Board(t.getText());
			boards.add(board);
			dao.add(board, CurrentUser.getInstance().getId());

			panel_list.remove(t);
			setupBoard(board);
			panel_list.add(t);

			panel_list.revalidate();
			panel_list.repaint();
		}
	}

	@Override
	public void update(Observable o, Object arg) {

		String sender = o.getClass().getName();

		if (sender.equals("GUI.Login"))
			loadAccount();
		if (sender.equals("GUI.BoardUI"))
			frame.setVisible(true);

	}

	private class boardClicked extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {

			String text = ((JLabel) (e.getSource())).getText();

			for (int i = 0; i < boards.size(); i++)
			{
				if (boards.get(i).getName().equals(text))
				{
					launchBoard(boards.get(i));
					break;
				}
			}
		}
	}
}
