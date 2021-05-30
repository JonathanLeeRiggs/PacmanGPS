package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SelectWindow extends JFrame {
	private static final long serialVersionUID = -3321005460622062672L;

	/**
	 * this method is a constructor.
	 */
	public SelectWindow() {	
		initGUI();
	}
	
	/**
	 * this method initialize the GUI.
	 */
	private void initGUI() {
		JPanel jp = new JPanel();
		this.add(jp);
		JButton p3 = new JButton("Project 3 algorithm");        //button for the algorithm of project 3.
		JButton p4 = new JButton("Project 4 algorithm");        //button for the algorithm of project 4.
		p3.setPreferredSize(new Dimension(270, 50));            //sets the size of the button.
		p4.setPreferredSize(new Dimension(270, 50));            //sets the size of the button.
		jp.add(p3);
		jp.add(p4);
		ActionListener l = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand()=="Project 3 algorithm") {   //if the user clicked on the "project 3 algorithm", start the main of this algorithm.
					String[] args = {"123"};
					Main.main(args);
				}
				if(e.getActionCommand()=="Project 4 algorithm") {   //if the user clicked on the "project 4 algorithm", start the main of this algorithm.
					String[] args = {"1234"};
					Window4.main(args);
				}
				
			}
		};
		p3.addActionListener(l);
		p4.addActionListener(l);

	}
	
	/**
	 * this method is the paint of the GUI.
	 */
	@Override
	public void paint(Graphics g) {
		this.getContentPane().setBackground(Color.LIGHT_GRAY);
		super.paint(g);
	}

	public static void main(String[] args) {
		SelectWindow sw = new SelectWindow();
		sw.setLocation(600, 300);
		sw.setVisible(true);
		sw.setSize(300, 150);
		sw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
