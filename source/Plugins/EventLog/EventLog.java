package Plugins.EventLog;

import Core.Board;

import java.util.ArrayList;

public class EventLog {

	ArrayList<Event> events = new ArrayList<>();
	Board board;

	public EventLog(Board board) {
		this.board = board;
	}

	public void setBoard(Board board) {

		this.board = board;
	}

	public ArrayList<Event> getEvents() {


		return events;
	}

	public Board getBoard() {

		return board;
	}

	public void add(Event event) {

		events.add(event);
	}
}
