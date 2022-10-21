package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.XChartPanel;

import libraries.Util;

public class goButton implements ActionListener {

	private JPanel panel, eastPanel;
	private GuiApp guiApp;

	public goButton(JPanel panel) {
		super();
		this.panel = panel;
		System.out.println(panel);

	}

	public goButton(JPanel eastPanel, GuiApp guiApp) {
		// TODO Auto-generated constructor stub
		this.guiApp = guiApp;
		this.eastPanel = this.guiApp.getEastPanel();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JList<String> universities = this.guiApp.getUniversities();
		List<String> selectedUniversities = universities.getSelectedValuesList();
		System.out.println(selectedUniversities);
		this.changeState(selectedUniversities);

		// hashmap for university name and uid - loop and store to following hashmaps
		// hashmap for university name and total deeds
		// hashmap for university name and deeds with privateData
		// 3 arrays for the diagram
		// 1 for listing the universities - names
		// 1 for the series of total deeds
		// 1 for the series of deeds with private data
		// extract them from hashmaps

	}

	private void changeState(List<String> selectedUniversities) {
		try {
			changeState();
		} finally {
			retrieveData(selectedUniversities);
		}

	}

	private void retrieveData(List<String> selectedUniversities) {
		SwingWorker sw1 = new SwingWorker() {
			HashMap<String, ArrayList<Integer>> infoForMultipleUniversities;
			List<Integer> selectedUniversitiesTotalDeeds;
			List<Integer> selectedUniversitiesDeedsWithPrivateData;
			
			// Method
			@Override
			protected String doInBackground() throws Exception {

				// Defining what thread will do here
//				for (int i = 10; i >= 0; i--) {
//					Thread.sleep(100);
//					System.out.println("Value in thread : " + i);
//					publish(i);
//				}
				infoForMultipleUniversities = Util.retrieveAndReturnForMultipleUniversitiesDeedsAndPrivateData(selectedUniversities);
				selectedUniversitiesTotalDeeds = infoForMultipleUniversities.get("selectedUniversitiesTotalDeeds");
				selectedUniversitiesDeedsWithPrivateData = infoForMultipleUniversities.get("selectedUniversitiesDeedsWithPrivateData");

				String res = "Finished Execution";
				return res;
			}

			// Method
			@Override
			protected void process(List chunks) {
				// define what the event dispatch thread
				// will do with the intermediate results
				// received while the thread is executing
//				int val = chunks.get(chunks.size() - 1);
//
//				statusLabel.setText(String.valueOf(val));
			}

			@Override
			protected void done() {
				// this method is called when the background
				// thread finishes execution
				try {
					//create the chart or send a signal and add to the panel
					System.out.println("Inside done function");
					System.out.println();
					System.out.println(selectedUniversitiesTotalDeeds);
					System.out.println();
					System.out.println(selectedUniversitiesDeedsWithPrivateData	);
					eastPanel.removeAll();
					eastPanel.updateUI();
					CategoryChart chart = new BarChartDeeds(selectedUniversities, selectedUniversitiesTotalDeeds, selectedUniversitiesDeedsWithPrivateData).getChart();
					JPanel chartPanel = new XChartPanel<CategoryChart>(chart);
					eastPanel.add(chartPanel);
//					CategoryChart chart = new BarChartDeedsVsDeedsPrivateDataUniversities().getChart();
//					JPanel chartPanel = new XChartPanel<CategoryChart>(chart);
				} finally {
					
				}
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				} catch (ExecutionException e) {
//					e.printStackTrace();
//				}
			}
		};

		// Executes the swingworker on worker thread
		sw1.execute();
	}

	public void changeState() {
		JPanel panel = this.guiApp.getEastPanel();
		panel.removeAll();
		panel.updateUI();
		ImageIcon imageIcon = new ImageIcon("images/progess-bar.gif");
		imageIcon = new ImageIcon("images/spinner.gif");
		JLabel loading = new JLabel(imageIcon);
		panel.add(loading);
		// this.panel.add(loading);

		// when results are retrieved
//		panel.removeAll();
//		panel.updateUI();
//		CategoryChart chart = new BarChartDeedsVsDeedsPrivateDataUniversities().getChart();
//		JPanel chartPanel = new XChartPanel<CategoryChart>(chart);
//		// panel = chartPanel;
//		panel.updateUI();
//		// this.guiApp.setEastPanel(chartPanel);
//		this.guiApp.validate();
//		// this.guiApp.getContentPane().remove(this.guiApp.getDefaultPanel());
//		// this.guiApp.remove(this.guiApp.getEastPanel());
//		// this.guiApp.getContentPane().add(chartPanel);
//		// this.guiApp.remove(this.guiApp.getDefaultPanel());
//		// this.guiApp.remove(this.guiApp.getEastPanel());
//		this.guiApp.validate();
//
//		// this.guiApp.add(chartPanel);
//		this.guiApp.validate();
//		panel.add(chartPanel);
//		this.guiApp.validate();

	}

}
