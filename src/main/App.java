package main;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class App {
	// diavgeia retrieval App

	public App() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//BarChartDeedsVsDeedsPrivateDataUniversities chart = new BarChartDeedsVsDeedsPrivateDataUniversities();
	    //new SwingWrapper<>(chart.getChart()).displayChart();
	    //new GuiApp2(chart.getChart());
		
		changeLookAndFeel();		
		new GuiApp();

	}
	
	private static void changeLookAndFeel() {
		// TODO Auto-generated method stub
		try {
			UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
			Logger.getLogger(GuiApp.class.getName()).log(Level.SEVERE, null, ex);
		}
		JFrame.setDefaultLookAndFeelDecorated(true);

	}

}
