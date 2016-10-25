import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Visualization {

	public class DrawPanel extends JPanel {
//		@Override
//		public void paint(Graphics g) {
//			Graphics2D g2d = (Graphics2D) g;
//			g2d.setColor(Color.RED);
//			g2d.fillOval(0, 0, 30, 30);
//			g2d.drawOval(0, 50, 30, 30);
//			g2d.fillRect(50, 0, 30, 30);
//			g2d.drawRect(50, 50, 30, 30);
//
//			g2d.draw(new Ellipse2D.Double(0, 100, 30, 30));
//		}
		 @Override   
		   public void paintComponent(Graphics g) {
			 Graphics2D g2d = (Graphics2D) g;
		      super.paintComponent(g2d);  // paint background
		      setBackground(CANVAS_BACKGROUND);
		      
		      g2d.setStroke(new BasicStroke(3));
		      // Drawing edge
		      g2d.drawLine(170, CANVAS_HEIGHT/2 - 25, 320, CANVAS_HEIGHT/3 - 25);		// 0-1
		      g2d.drawLine(170, CANVAS_HEIGHT/2 - 25, 320, 2*CANVAS_HEIGHT/3 - 25);		// 0-2
		      g2d.drawLine(320, CANVAS_HEIGHT/3 - 25, 320, 2*CANVAS_HEIGHT/3 - 25);		// 1-2
		      g2d.drawLine(320, CANVAS_HEIGHT/3 - 25, 620, CANVAS_HEIGHT/3 - 25);		// 1-3
		      g2d.drawLine(320, 2*CANVAS_HEIGHT/3 - 25, 620, CANVAS_HEIGHT/3 - 25);		// 2-3
		      g2d.drawLine(320, 2*CANVAS_HEIGHT/3 - 25, 620, 2*CANVAS_HEIGHT/3 - 25);	// 2-4
		      g2d.drawLine(620, CANVAS_HEIGHT/3 - 25, 620, 2*CANVAS_HEIGHT/3 - 25);		// 3-4
		      g2d.drawLine(620, CANVAS_HEIGHT/3 - 25, 770, CANVAS_HEIGHT/2 - 25);		// 3-5
		      g2d.drawLine(620, 2*CANVAS_HEIGHT/3 - 25, 770, CANVAS_HEIGHT/2 - 25);		// 4-5
		      
		      // Drawing node
		      g2d.setColor(Color.WHITE);
		      g2d.fillOval(145, CANVAS_HEIGHT/2 - 50, 50, 50);
		      g2d.fillOval(295, CANVAS_HEIGHT/3 - 50, 50, 50);
		      g2d.fillOval(295, 2*CANVAS_HEIGHT/3 - 50, 50, 50);
		      g2d.fillOval(595, CANVAS_HEIGHT/3 - 50, 50, 50);
		      g2d.fillOval(595, 2*CANVAS_HEIGHT/3 - 50, 50, 50);
		      g2d.fillOval(745, CANVAS_HEIGHT/2 - 50, 50, 50);
		      
		      g2d.setColor(Color.BLACK);
		      g2d.drawOval(145, CANVAS_HEIGHT/2 - 50, 50, 50);
		      g2d.drawOval(295, CANVAS_HEIGHT/3 - 50, 50, 50);
		      g2d.drawOval(295, 2*CANVAS_HEIGHT/3 - 50, 50, 50);
		      g2d.drawOval(595, CANVAS_HEIGHT/3 - 50, 50, 50);
		      g2d.drawOval(595, 2*CANVAS_HEIGHT/3 - 50, 50, 50);
		      g2d.drawOval(745, CANVAS_HEIGHT/2 - 50, 50, 50);
		      
		      // Drawing node name and edge weight	
		      String type = "SansSerif"; 
		      int style = Font.BOLD;
		      Font fontName = new Font(type, style, 20);
		      g2d.setFont(fontName);
		      g2d.drawString("0", 165, CANVAS_HEIGHT/2-20);
		      g2d.drawString("1", 315, CANVAS_HEIGHT/3-20);
		      g2d.drawString("2", 315, 2*CANVAS_HEIGHT/3-20);
		      g2d.drawString("3", 615, CANVAS_HEIGHT/3-20);
		      g2d.drawString("4", 615, 2*CANVAS_HEIGHT/3-20);
		      g2d.drawString("5", 765, CANVAS_HEIGHT/2-20);
		      // Edge weight
		      g2d.setColor(Color.BLUE);
		      Font fontWeight = new Font(type, style, 16);
		      g2d.setFont(fontWeight);
		      g2d.drawString("4", 235, 225);
		      g2d.drawString("3", 235, 340);
		      g2d.drawString("5", 305, 285);
		      g2d.drawString("2", 465, 170);
		      g2d.drawString("2", 445, 285);
		      g2d.drawString("7", 465, 370);
		      g2d.drawString("1", 625, 285);
		      g2d.drawString("5", 695, 225);
		      g2d.drawString("3", 695, 340);
		   }
	}
	// Define constants for the various dimensions
	   public static final int CANVAS_WIDTH = 900;
	   public static final int CANVAS_HEIGHT = 600;
	   public static final Color LINE_COLOR = Color.BLACK;
	   public static final Color CANVAS_BACKGROUND = Color.CYAN;

	// GUI
	// --------------
	JFrame frame = new JFrame("Dijkstra's Algorithm");
	JButton nextButton = new JButton("Next");
	JButton previousButton = new JButton("Previous");
	JButton resetButton = new JButton("Reset");
	JPanel mainPanel = new JPanel();
	JPanel buttonPanel = new JPanel();
	JPanel drawPanel = new DrawPanel();
	ImageIcon icon;

	public Visualization() {
		// Setting up GUI
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		// Button
		buttonPanel.setLayout(new GridLayout(1, 0));
		buttonPanel.add(previousButton);
		buttonPanel.add(resetButton);
		buttonPanel.add(nextButton);
		previousButton.addActionListener(new previousButtonListener());
		resetButton.addActionListener(new resetButtonListener());
		nextButton.addActionListener(new nextButtonListener());

		// frame layout
		mainPanel.setLayout(null);
		mainPanel.add(drawPanel);
		mainPanel.add(buttonPanel);
		drawPanel.setBounds(0, 0, 900, 500);
		buttonPanel.setBounds(300, 500, 300, 50);

		frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
		frame.setSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
		frame.setVisible(true);

	}

	/**
	 * Main method
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		Visualization visualization = new Visualization();
	}

	// Handler for Previous button
	// -----------------------
	class previousButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

		}
	}

	// Handler for Next button
	// -----------------------
	class nextButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

		}
	}

	// Handler for Reset button
	// -----------------------
	class resetButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

		}
	}
}
