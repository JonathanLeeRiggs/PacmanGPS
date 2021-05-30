package GUI;
import javax.swing.JFrame;

import GIS.FruitsList;


public class Main 
{
	public static void main(String[] args)
	{
		MainWindow window = new MainWindow();
		window.setVisible(true);
		window.setSize(window.myImage.getWidth()-8,window.myImage.getHeight()-8);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
