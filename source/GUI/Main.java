package GUI;

import PluginSystem.PluginInterface;
import PluginSystem.PluginLoader;

import javax.swing.*;
import java.io.IOException;

public class Main {

	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		PluginInterface[] plugins = null;

		PluginLoader loader = new PluginLoader();
		try {
			plugins = loader.loadPlugins();
		} catch (IOException | IllegalAccessException | ClassNotFoundException | InstantiationException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}

		Accueil gui = new Accueil(plugins);

		Login login = new Login();
		login.addObserver(gui);
	}
}
