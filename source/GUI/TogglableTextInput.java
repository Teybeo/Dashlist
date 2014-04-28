package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

class TogglableTextInput extends JPanel {

	private JButton button;
	private JTextArea text_area;
	private static final String keycode_enter = "ENTER";
	private static final String keycode_escape = "ESCAPE";
	private static final int BUTTON = 0;
	private static final int TEXTINPUT = 1;
	private int current_mode = BUTTON;
	private List<ActionListener> listenerList = new ArrayList<ActionListener>();

	private TogglableTextInput(String text) {

        super();
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
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
        text_area.setMaximumSize(new Dimension(200, 50));

		button.addActionListener(new ButtonListener());

        this.setBackground(new Color(200, 100, 150));
        this.setAlignmentY(Component.TOP_ALIGNMENT);

		add(button);
        button.setAlignmentY(Component.TOP_ALIGNMENT);
        revalidate();
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

	void addActionListener(ActionListener al)
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

	int getCurrent_mode() {

		return current_mode;
	}
}
