package PluginSystem;

import Core.Board;

import javax.swing.*;
import java.awt.*;

public interface PluginInterface {

	/**
	 * Cette fonction est appellée au chargement d'une board et transmet une référence vers cette board au plugin
	 * @param board La board qui vient d'être chargée
	 */
	public void acquireBoard(Board board);

	/**
	 * Cette fonction sert à identifier dans quel conteneur de quelle classe le plugin shouaite s'insérer
	 * @return Une chaîne au format [Class]:[Field] où Field est le nom d'une variable membre de Class de type Container
	 */
	public String getTargetContainer();


	/**
	 * Cette fonction transmet au plugin un container dans lequel il pourra s'insérer
	 * @param container Le container auquel s'ajoutera le plugin
	 */
	void acquireContainer(Container container);
}
