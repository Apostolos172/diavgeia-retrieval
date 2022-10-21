package components;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import main.GuiApp;
import main.goButton;

public class Button extends JButton {

	public Button(String string, JPanel eastPanel) {
		// TODO Auto-generated constructor stub
		super();
		this.setText(string);
		ActionListener goButton = new goButton(eastPanel); 
		this.addActionListener(goButton);
	}

	public Button(String string, JPanel eastPanel, GuiApp guiApp) {
		// TODO Auto-generated constructor stub
		super();
		this.setText(string);
		ActionListener goButton = new goButton(eastPanel, guiApp); 
		this.addActionListener(goButton);
	}

}
