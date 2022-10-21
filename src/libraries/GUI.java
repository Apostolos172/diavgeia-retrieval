package libraries;

import java.awt.*;
import javax.swing.*;

public class GUI {
	
	/**
	 * Accepts a frame and puts to it size approximately to the screen size
	 */
	public static void setSizeOfTheWindow(JFrame frame)
	{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		width = Math.floor(width);
		//String w = Double.toString(width);
		double height = screenSize.getHeight();
		height = Math.floor(height);
		String h = Double.toString(height);
		h = Double.toString(820);
		//System.out.println((int)Double.parseDouble(w));
		frame.setSize((int)width,(int)Double.parseDouble(h));
	}
	
	public static Font getFont(String font)
	{
		switch(font) {
			case "serifFontBig":
			{
				Font serifFontBig = new Font("serif", Font.BOLD, 30);
				//break;
				return serifFontBig;
			}
			case "serifFontMedium":
			{
				Font serifFontMedium = new Font("serif", Font.BOLD, 22);
				return serifFontMedium;
			}
			case "serifFontSmall":
			{
				Font serifFontSmall = new Font("serif", Font.ITALIC|Font.BOLD, 17);
				return serifFontSmall;
			}
			case "sansserifFontBig":
			{
				Font sansserifFontBig = new Font("Sans Serif", Font.BOLD, 20);
				return sansserifFontBig;
			}
			case "sansserifFontMedium":
			{
				Font sansserifFontMedium = new Font("Sans Serif", Font.BOLD, 20);
				return sansserifFontMedium;
			}
			case "sansSerifFontSmall":
			{
				Font sansSerifFontSmall = new Font("Sans Serif", Font.ITALIC|Font.BOLD, 17);
				return sansSerifFontSmall;
			}
			default:
			{
				return new Font("Sans Serif", Font.PLAIN, 20);
			}
		}
		
	}
	
	public static void setPadding(JPanel panel)
	{
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
	}
	
	public static void setPadding(JComponent object) {
		object.setBorder(BorderFactory.createCompoundBorder(object.getBorder(), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
	}
	
	public static void setTopPadding(JPanel panel)
	{
		panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0)); 
	}
	
	public static void setPaddingAtJTextField(JTextField txtfield)
	{
		txtfield.setBorder(BorderFactory.createCompoundBorder(
				txtfield.getBorder(), 
		        BorderFactory.createEmptyBorder(15, 15, 15, 15)));
	}
	
	public static void setLeftPaddingAtJTextField(JTextField txtfield)
	{
		txtfield.setBorder(BorderFactory.createCompoundBorder(
				txtfield.getBorder(), 
		        BorderFactory.createEmptyBorder(0,15,0,0)));
	}
	
	public static void setPaddingAtJTextArea(JTextArea txtarea)
	{
		txtarea.setBorder(BorderFactory.createCompoundBorder(
				txtarea.getBorder(), 
		        BorderFactory.createEmptyBorder(15, 15, 15, 15)));
	}
	
	public static void setPaddingAtJLabel(JLabel label)
	{
		label.setBorder(BorderFactory.createCompoundBorder(
				label.getBorder(), 
		        BorderFactory.createEmptyBorder(15, 15, 15, 15)));
	}
	
}



