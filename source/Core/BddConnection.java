package Core;

import javax.swing.*;
import java.sql.*;

public class BddConnection {

	private static String mysql_url = "//localhost";
	private static String mysql_base = "dashlist";
	private static String mysql_user = "root";
	private static String mysql_pass = "";
	private static Connection link = null;

	/**
	 * Lors du premier appel, une connexion est créée et stockée de manière statique
	 * Tous les appels suivants retourneront cette connexion sans jamais en recréer une nouvelle
	 * @return Une connexion à la base de données
	 */
	public static Connection getInstance() {

		if (link == null)
			new BddConnection();

		return link;
	}

	/**
	 * Constructeur privé pour empêcher la création d'instances multiples
	 */
	private BddConnection() {
		try
		{
			// This will load the MySQL driver
			Class.forName("com.mysql.jdbc.Driver");
			link = DriverManager.getConnection("jdbc:mysql:"+mysql_url+"/"+mysql_base, mysql_user, mysql_pass);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "La connexion a la base de données n'a pas pu être établie", "Erreur", JOptionPane.ERROR_MESSAGE);
		}

	}

}
