package Plugins;

import Core.Board;
import PluginSystem.PluginInterface;

public class PluginTest implements PluginInterface {


	@Override
	public void ReferenceBoard(Board board) {

		System.out.println("PluginTest acquired board: " + board.getName());
	}
}