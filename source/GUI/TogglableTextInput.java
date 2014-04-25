package GUI;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class TogglableTextInput extends JPanel {

	JButton button;
	JTextArea text_area;
	static final String keycode_enter = "ENTER";
	static final String keycode_escape = "ESCAPE";
	static final int BUTTON = 0;
	static final int TEXTINPUT = 1;
	int current_mode = BUTTON;
	List<ActionListener> listenerList = new ArrayList<ActionListener>();

	public TogglableTextInput(String text) {

		super();
		setFocusable(true);
		button = new JButton(text);
		text_area = new JTextArea(1, 2);
		text_area.setVisible(false);
		text_area.setWrapStyleWord(true);
		text_area.setLineWrap(true);
		text_area.addFocusListener(new TogglableTextInputFocusAdapter());
		text_area.addMouseListener(new TogglableTextInputMouseAdapter());

		text_area.getInputMap().put(KeyStroke.getKeyStroke(keycode_enter), "enter");
		text_area.getInputMap().put(KeyStroke.getKeyStroke(keycode_escape), "escape");
		text_area.getActionMap().put("enter", new ValidateAction());
		text_area.getActionMap().put("escape", new UnfocusAction());

		button.addActionListener(new ButtonListener());

		add(button);
	}

	public TogglableTextInput(String text, ActionListener action_listener) {

		this(text);
		this.addActionListener(action_listener);

	}

	private void setTextInputMode() {

		if (current_mode == TEXTINPUT)
			return;

		remove(button);
		add(text_area);

		text_area.setVisible(true);
		text_area.setText("");
		text_area.requestFocusInWindow();
		button.setVisible(false);

		revalidate();
		current_mode = TEXTINPUT;
	}

	private void setButtonMode() {

		if (current_mode == BUTTON)
			return;

		remove(text_area);
		add(button);

		text_area.setVisible(false);
		button.setVisible(true);

		revalidate();
		current_mode = BUTTON;

	}

	// Lorsqu'on clique sur le bouton, celui-ci disparaît et est remplacé par un champ de texte
	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			setTextInputMode();
			getParent().repaint();
		}
	}

	// Si le champ de texte perd le focus, le bouton revient
	private class TogglableTextInputFocusAdapter extends FocusAdapter {
		@Override
		public void focusLost(FocusEvent e) {

			super.focusLost(e);
			if (getCurrent_mode() != BUTTON)
			{
				setButtonMode();
				getParent().repaint();
			}
		}
	}

	// Lorsque l'utilisateur appuie sur Entrée après avoir entré du texte
	private class ValidateAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {

			// Si pas de texte n'a été rentré, on ne valide pas
			if (((JTextArea)e.getSource()).getText().isEmpty() == true)
				return;

			setButtonMode();
			getParent().repaint();
			notifyListeners();
		}
	}

	// TODO: Cette classe est inutile car les clics en dehors du textarea n'y sont pas envoyés
	private class TogglableTextInputMouseAdapter extends MouseAdapter {

		@Override
		public void mouseReleased(MouseEvent e) {

			super.mouseReleased(e);    //To change body of overridden methods use File | Settings | File Templates.
			if (((JTextArea)e.getSource()).getBounds().contains(e.getPoint()) == false)
				dispatchEvent(new FocusEvent(text_area, FocusEvent.FOCUS_LOST));
		}
	}

	// Génère un event de perte de focus du text_area
	private class UnfocusAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {

			// On redirige vers le focus listener
			dispatchEvent(new FocusEvent(text_area, FocusEvent.FOCUS_LOST));
		}
	}

	public void addActionListener(ActionListener al)
	{
		listenerList.add(al);
	}

	public void removeActionListener(ActionListener al)
	{
		listenerList.remove(al);
	}

	public void actionPerformed(ActionEvent e)
	{
	}

	private void notifyListeners()
	{
		ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "wtf");
		for (ActionListener action : listenerList) {
			action.actionPerformed(event);
		}
	}

	public String getText() {
		return text_area.getText();
	}

	public int getCurrent_mode() {

		return current_mode;
	}
}
