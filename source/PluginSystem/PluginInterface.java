package PluginSystem;

import Core.Board;

import javax.swing.*;
import java.awt.*;

public interface PluginInterface {

	public void acquireBoard(Board board);

	public String getTargetContainer();

	void acquireContainer(Container container);
}
