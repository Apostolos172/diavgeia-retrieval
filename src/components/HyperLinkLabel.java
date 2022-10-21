package components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class HyperLinkLabel extends AppJLabel {
	private String text;
	private String href;

	private void makeHyperLink() {
		this.setForeground(Color.white.darker());
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));

		this.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Desktop.getDesktop().browse(new URI(href));
				} catch (IOException | URISyntaxException e1) {
					e1.printStackTrace();
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				Object source = e.getSource();

				if (source instanceof JLabel) {
					JLabel lbl = (JLabel) source;
					lbl.setText(text);
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				Object source = e.getSource();

				if (source instanceof JLabel) {
					JLabel lbl = (JLabel) source;
					lbl.setText("<html><a href=''>" + text + "</a></html>");
				}
			}

		});
	}

	public HyperLinkLabel(String text, String href) {
		super(text);
		this.text = text;
		this.href = href;
		makeHyperLink();
	}

}
