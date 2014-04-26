package Core;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Observer;

public class BoardDAO {

//	private final int user_id;
	private Connection link;

	public BoardDAO(Connection link) {

		this.link = link;
	}

	// Retourne les tableaux liés à un certain utilisateur
	// Les listes des tableaux ne sont pas chargées
	public ArrayList<Board> getUserBoards(int user_id) {

		ArrayList<Board> boards = new ArrayList<>();

		try {

			Statement query = link.createStatement();
			ResultSet res = query.executeQuery(
				"SELECT * " +
				"FROM board, (SELECT board_id FROM board_members WHERE user_id = '"+ user_id + "') temp " +
				"WHERE board.id = temp.board_id;"
			);

			while (res.next())
				boards.add(new Board(res.getInt("id"), res.getString("name")));


		} catch (SQLException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}

		return boards;
	}

	public Board get(int id) {

		try {

			Statement query = link.createStatement();

			ResultSet res = query.executeQuery(
					"SELECT * " +
					"FROM board " +
					"WHERE id ='" + id + "';"
			);

			// Si le tableau a été trouvé, on charge les listes rattachées
			if (res.next())
			{
				String name = res.getString(1);
				Board board = new Board(id, name);

				loadLists(board);

				return board;
			}

		} catch (SQLException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}

		return null;
	}

	public void add(Board board, int user_id) {

		try {
			Statement query = link.createStatement();

			// On enregistre la board dans la table board
			query.execute("" +
					"INSERT INTO board " +
					"VALUES(default, '"+board.getName()+"');", Statement.RETURN_GENERATED_KEYS);

			// On récupère l'id créé par MySQL
			ResultSet res = query.getGeneratedKeys();

			if (res.next())
				board.setId(res.getInt(1));

			// On enregistre l'user actuel comme membre de cette board
			query.execute("" +
					"INSERT INTO board_members " +
					"VALUES('" + board.getId() + "', '" + user_id + "', '1', '0');");


		} catch (SQLException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}

	public void addList(Board board, String list_name) {

		ListDAO dao = new ListDAO(BddConnection.getInstance());

		List list = dao.add(list_name, board.getLists().size() + 1, board.getId());

		board.add(list);
	}

	public void loadLists(Board board) {

		ListDAO dao = new ListDAO(link);
		board.setLists(dao.getBoardLists(board.getId()));

	}
}
