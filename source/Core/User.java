package Core;

import java.util.HashSet;

public class User {

	int id;
	String name;
	HashSet<Board> boards;

	public User( int id, String name, HashSet<Board> boards) {

		this.id = id;
		this.boards = boards;
		this.name = name;
	}

	public User(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public HashSet<Board> getBoards() {

		return boards;
	}
}
