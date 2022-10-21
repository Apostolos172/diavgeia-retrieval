package components;

import java.awt.Color;

import javax.swing.Icon;
import javax.swing.JLabel;

public class AppJLabel extends JLabel {

	public AppJLabel() {
		// TODO Auto-generated constructor stub
		this.customizeLabel();
	}

	private void customizeLabel() {
		// TODO Auto-generated method stub
		this.setForeground(Color.white);
	}

	public AppJLabel(String text) {
		super(text);
		// TODO Auto-generated constructor stub
		this.customizeLabel();

	}

	public AppJLabel(Icon image) {
		super(image);
		// TODO Auto-generated constructor stub
	}

	public AppJLabel(String text, int horizontalAlignment) {
		super(text, horizontalAlignment);
		// TODO Auto-generated constructor stub
		this.customizeLabel();

	}

	public AppJLabel(Icon image, int horizontalAlignment) {
		super(image, horizontalAlignment);
		// TODO Auto-generated constructor stub
	}

	public AppJLabel(String text, Icon icon, int horizontalAlignment) {
		super(text, icon, horizontalAlignment);
		// TODO Auto-generated constructor stub
	}
	
	

}
