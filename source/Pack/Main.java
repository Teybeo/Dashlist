package Pack;

import javax.swing.*;

public class Main {

	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Core core = new Core();
		Interface gui = new Interface(core);

		Login login = new Login(core);
		login.addObserver(gui);
	}
}
