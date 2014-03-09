package Core;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
				"FROM board " +
				"WHERE id_user = '"+ user_id + "';"
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

			query.execute("" +
					"INSERT INTO board " +
					"VALUES(default, "+user_id+",'"+board.getName()+"');");

			ResultSet res = query.executeQuery("SELECT id " +
												"FROM board " +
												"WHERE name='"+board.getName()+"' AND " +
												"id_user="+user_id+";");
			// On récupère l'id créé par MySQL
			if (res.next())
			{
				board.setId(res.getInt("id"));
			}

		} catch (SQLException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}

	public void loadLists(Board board) {

		ListDAO dao = new ListDAO(link);
		board.setLists(dao.getBoardLists(board.getId()));

	}
}
