package components;

import java.awt.Color;
import java.awt.LayoutManager;

import javax.swing.JPanel;

import libraries.GUI;

public class AppJPanel extends JPanel {

	public AppJPanel() {
		// TODO Auto-generated constructor stub
		this.customizePanel();

	}

	public AppJPanel(LayoutManager layout) {
		super(layout);
		// TODO Auto-generated constructor stub
		this.customizePanel();
	}

	private void customizePanel() {
		// TODO Auto-generated method stub
		GUI.setPadding(this);
		this.setBackground(Color.decode("#37526d"));
	}
	
	

}
