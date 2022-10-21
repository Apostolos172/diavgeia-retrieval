package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import components.AppJLabel;
import components.AppJPanel;
import components.Button;
import components.HyperLinkLabel;
import libraries.GUI;
import libraries.Util;

public class GuiApp extends JFrame {
	/**
	 * main app window frame
	 */
	private static final long serialVersionUID = 1L;
	private JPanel defaultPanel, eastPanel, westPanel;
	private JList<String> universities;
	private DefaultListModel<String> listModel;

	public GuiApp() throws HeadlessException {
		// main app window frame constructor
		this.setTitle("Diavgeia-Retrieval");
		this.makeDefaultPanel();
		// this.setContentPane(defaultPanel);
		this.add(defaultPanel);
		GUI.setSizeOfTheWindow(this);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	private void makeDefaultPanel() {
		// default panel of the frame which contains all the other following
		this.defaultPanel = new JPanel(new BorderLayout(15, 15));
		GUI.setPadding(this.defaultPanel);
		// ! must order
		makeNorthPanel();
		makeEastPanel();
		makeWestPanel();
		makeSouthPanel();
	}

	private void makeNorthPanel() {
		// header of app
		JPanel northPanel = new AppJPanel();
		JLabel title = new AppJLabel("Diavgeia Retrieval", JLabel.CENTER);
		title.setFont(GUI.getFont("sansserifFontBig"));
		northPanel.add(title);
		this.defaultPanel.add(northPanel, BorderLayout.NORTH);
	}

	private void makeWestPanel() {
		// panel with the description of the app and the user interaction
		westPanel = new AppJPanel();
		westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));

		// description of app
		JTextArea description = new JTextArea("Η εφαρμογή αυτή επικοινωνεί με το API της Διαύγειας και "
				+ "αντλεί και προβάλει για τα πανεπιστήμια που θα επιλεγούν το πλήθος των πράξεων "
				+ "για διάστημα 180 ημερών από τις 2019-01-01 σε σχέση με το πλήθος των πράξεων αυτών "
				+ "που περιείχαν όμως προσωπικά δεδομένα", 5, 30);
		westPanel.add(customizeText(description));
		westPanel.add(Box.createRigidArea(new Dimension(0, 10)));

		// add list to choose universities - user interaction
		/*
		 * // alternative retrieval of universities String url =
		 * "https://diavgeia.gov.gr/opendata/organizations.json?category=UNIVERSITY";
		 * universities = new JList(((ArrayList<String>)
		 * Util.retrieveAndReturn(url)).toArray());
		 */
		// universities = new
		// JList(Util.retrieveAndReturnAllUniversitiesLabelsUids().values().toArray());
		listModel = new DefaultListModel<String>();
		listModel.add(0, " ");
		listModel.add(1, " Loading ... The available universities in Diavgeia' s API will be shown in a bit");
		universities = new JList<String>();
		universities.setModel(listModel);
		createJListInBackground();
		/*
		 * // for testing purposes String week[]= { "Monday","Tuesday","Wednesday",
		 * "Thursday","Friday","Saturday","Sunday"}; universities = new
		 * JList<String>(week);
		 */
		JScrollPane scrollUniverstities = new JScrollPane(universities);
		scrollUniverstities.setMaximumSize(new Dimension(800, 100));
		westPanel.add(scrollUniverstities);
		westPanel.add(Box.createRigidArea(new Dimension(0, 10)));

		// instructions
		JTextArea instructions = new JTextArea(
				"Επίλεξε ένα ή περισσότερα πανεπιστήμια και πάτησε το κουμπί Go, ούτως ώστε να ξεκινήσει "
						+ "η άντληση δεδομένων από το API της Διαύγειας. Μόλις ολοκληρωθεί η διαδικασία, η οποία μπορεί "
						+ "να κρατήσει αρκετά λεπτά λόγω του όγκου της πληροφορίας, αναλόγως και του πλήθους των "
						+ "ιδρυμάτων για τα οποία αιτείται πληροφορία, θα εμφανιστεί γράφημα με τα αποτελέσματα.",
				5, 30);
		westPanel.add(customizeText(instructions));
		westPanel.add(Box.createRigidArea(new Dimension(0, 10)));

		// button to fire the retrieval of data
		JButton go = new Button("Go", this.eastPanel, this);
		go.setMaximumSize(new Dimension(800, 30));
		JPanel goPane = new AppJPanel();
		goPane.add(go);
		westPanel.add(goPane);

		this.defaultPanel.add(westPanel, BorderLayout.CENTER);
	}

	private JList<String> createJListInBackground() {
		SwingWorker sw1 = new SwingWorker() {
			Collection<String> data;

			// Method
			@Override
			protected String doInBackground() throws Exception {

				// Defining what thread will do here
				// universitiess = new
				// JList(Util.retrieveAndReturnAllUniversitiesLabelsUids().values().toArray());
				// westPanel.add(universitiess);
				data = Util.retrieveAndReturnAllUniversitiesLabelsUids().values();
				// System.out.println(data);

				// DefaultListModel<String> listModel = new DefaultListModel<String>();
				// listModel.addAll(data);

				// listModel.addAll(data);
				// universities.setModel(listModel);

				String res = "Finished Execution";
				return res;
			}

			// Method
			@Override
			protected void done() {
				// this method is called when the background
				// thread finishes execution
				try {
					// String statusMsg = get();
					System.out.println(data);
					System.out.println("Inside done function");
					listModel.removeAllElements();
					listModel.addAll(data);
					universities.setModel(listModel);
					// statusLabel.setText(statusMsg);
				} finally {

				}
			}
		};

		// Executes the swingworker on worker thread
		sw1.execute();
		return universities;

	}

	private void makeEastPanel() {
		// panel for displaying the chart on the go
		eastPanel = new AppJPanel();
		this.createAndAddCredits(eastPanel);
		this.defaultPanel.add(eastPanel, BorderLayout.EAST);
	}

	private void makeSouthPanel() {
		// footer of app
		JPanel southPanel = new AppJPanel();
		createAndAddCredits(southPanel);
		this.defaultPanel.add(southPanel, BorderLayout.SOUTH);
	}

	private void createAndAddCredits(JPanel panel) {
		// createAndAddCredits
		JLabel credits1 = new AppJLabel("<html>Powered by ");
		JLabel apostolos172 = new HyperLinkLabel("Apostolos172", "https://github.com/Apostolos172/");
		JLabel credits2 = new AppJLabel("and");
		JLabel XChart = new HyperLinkLabel("XChart", "https://github.com/knowm/XChart");

		panel.add(credits1);
		panel.add(apostolos172);
		panel.add(credits2);
		panel.add(XChart);
	}

	private JScrollPane customizeText(JTextArea textAreaWithText) {
		// customize text area and add it to scrollpane and return it
		textAreaWithText.setEditable(false);
		textAreaWithText.setLineWrap(true);
		textAreaWithText.setWrapStyleWord(true);
		textAreaWithText.setMaximumSize(new Dimension(800, 190));
		GUI.setPadding(textAreaWithText);
		textAreaWithText.setFont(GUI.getFont("sansSerifFontSmall"));
		JScrollPane scrollPaneTextArea = new JScrollPane(textAreaWithText);
		scrollPaneTextArea.setMaximumSize(new Dimension(800, 190));
		GUI.setPadding(scrollPaneTextArea);
		return scrollPaneTextArea;
	}

	// getters and setters

	public JPanel getDefaultPanel() {
		return defaultPanel;
	}

	public void setDefaultPanel(JPanel defaultPanel) {
		this.defaultPanel = defaultPanel;
	}

	public void setEastPanel(JPanel eastPanel) {
		this.eastPanel = eastPanel;
	}

	public JPanel getEastPanel() {
		return eastPanel;
	}

	public JList<String> getUniversities() {
		return this.universities;
	}

}
