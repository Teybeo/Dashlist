package Plugins.EventLog;

import Core.Board;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class EventLog extends Observable implements Observer {

	ArrayList<Event> events = new ArrayList<>();
	Board board;

	public EventLog(Board board) {
		this.board = board;
		this.board.addObserver(this);
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

	@Override
	public void update(Observable o, Object arg) {
		//To change body of implemented methods use File | Settings | File Templates.
	}
}
