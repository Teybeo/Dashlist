package PluginSystem;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarFile;

public class PluginLoader {

	private static final String folder = "plugins";

	/**
	 * Liste des classes à instancier
	 */
	private ArrayList<Class> plugins = new ArrayList<>();

	public PluginInterface[] loadPlugins() throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {

		// On liste les fichiers dans le dossier
		String[] files = new File(folder).list();

		if (files == null) {
			System.out.println("Folder ["+ folder +"] is empty (No plugin detected!)");
			return null;
		}

		// On récupère les class implémentant notre interface
		for (String file : files)
			loadPlugin(file);

		PluginInterface[] loaded_plugins = new PluginInterface[plugins.size()];

		// On instancie chacun des plugins
		for (int i = 0; i < plugins.size(); i++)
		{
			loaded_plugins[i] = (PluginInterface)(plugins.get(i)).newInstance();
			System.out.println("Plugin ["+plugins.get(i)+"] loaded");
		}

		return loaded_plugins;
	}

	private void loadPlugin(String file) throws IOException, ClassNotFoundException {

		File path = new File(folder+"/"+file);

        // On teste la validité
		if(path.exists() == false || path.getAbsolutePath().endsWith(".jar") == false)
			return;

        // On découpe le nom comme une URL
		URL u = path.toURI().toURL();

        // Permet de charger un jar par rapport à un tableau d'URL
		URLClassLoader loader = new URLClassLoader(new URL[] {u});

        // Permet de lire le contenu d'un fichier JAR
		Enumeration<?> enumeration = new JarFile(path.getAbsolutePath()).entries();

        // Pour chacun des fichiers du jar
		while(enumeration.hasMoreElements()) {

            // Nom du fichier du jar
			String tmp = enumeration.nextElement().toString();

            // On vérifie qu'il s'agit bien d'un .class
			if(tmp.length() > 6 && tmp.substring(tmp.length()-6).compareTo(".class") == 0) {

                // On normalise le nom du fichier en package/class
				tmp = tmp.substring(0,tmp.length()-6);
				tmp = tmp.replaceAll("/",".");

                // On crée la class avec son nom, et son emplacement
				Class tmpClass = Class.forName(tmp, true, (ClassLoader)loader);

                // On vérifie si cette classe implémente notre interface
				for(int j = 0; j < tmpClass.getInterfaces().length; j++) {
					if(tmpClass.getInterfaces()[j].getName().equals("PluginSystem.PluginInterface")) {
						this.plugins.add(tmpClass);
					}
				}

			}

		}

	}

}

