package GUI;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import Algorithm.GameAlgo;
import File_format.PacmanCsvReader;
import GIS.Fruit;
import GIS.FruitsList;
import GIS.Map;
import GIS.Pacman;
import GIS.PacmanList;
import GIS.Path;
import GIS.PathList;
import Geom.Point3D;

public class MainWindow extends JFrame implements ActionListener,MouseListener {
	private static final long serialVersionUID = -5431285052113509107L;
	private boolean pac=false;
	private boolean fru=false;
	private boolean def=true;
	double time=0;

	private boolean isFirstPac=true;
	private boolean isFirstFru=true;


	int []currentPointInPixels=new int[2];
	Point3D p= new Point3D(0, 0);
	private PacmanList mainPacmanList = new PacmanList();
	private FruitsList mainFruitsList = new FruitsList();
	private PathList pathlist = new PathList();

	public BufferedImage myImage;
	public BufferedImage myPac;
	public BufferedImage myFru1;
	public BufferedImage start;

	public MainWindow() 
	{
		initGUI();		
		this.addMouseListener(this);
	}
	/**
	 * this function is responsible for all the graphics in the game.
	 */
	private void initGUI() 
	{
		//***//menu bar buttons//***//
		MenuBar menuBar = new MenuBar();
		Menu file = new Menu("File"); 
		Menu options = new Menu("Options");
		MenuItem save = new MenuItem("Save");
		MenuItem kml = new MenuItem("Path2kml");
		MenuItem exit = new MenuItem("Exit");
		MenuItem pacman = new MenuItem("Pacman");
		MenuItem fruit = new MenuItem("Fruit");
		MenuItem run = new MenuItem("Run");
		MenuItem load = new MenuItem("Load");
		MenuItem clear = new MenuItem("Clear");

		menuBar.add(file);
		menuBar.add(options);
		file.add(kml);
		file.add(save);
		file.add(load);
		file.add(clear);
		file.add(exit);
		options.add(pacman);
		options.add(fruit);
		options.add(run);
		this.setMenuBar(menuBar);
		exit.setEnabled(true);

		//***//the role of each button//***//
		ActionListener l = new RunPac(this);


		exit.addActionListener(l);
		save.addActionListener(l);
		load.addActionListener(l);
		pacman.addActionListener(l);
		fruit.addActionListener(l);
		clear.addActionListener(l);
		run.addActionListener(l);
		kml.addActionListener(l);

		//importing the game pictures.
		try {
			myImage = ImageIO.read(new File("Pictures/Ariel1.png"));
			myPac = ImageIO.read(new File("Pictures/PacMan.png"));
			myFru1 = ImageIO.read(new File("Pictures/g1.png"));
			start = ImageIO.read(new File("Pictures/start.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	int x = -1;
	int y = -1;


	@Override
	//runs this method with every click of the mouse.
	public void mouseClicked(MouseEvent arg) {
		System.out.println("pixels point: ("+ arg.getX() + "," + arg.getY() +")");
		x = arg.getX();
		y = arg.getY();
		p=GIS.Map.pixelsToCoords(x, y, getWidth(), getHeight());     //insert the coordinates values given by the pixels to a point.
		currentPointInPixels=GIS.Map.coordsToPixels(p, getWidth(), getHeight());        //insert the pixels given by the coordinates values to an double[].	
		repaint();	
	}
	//////////********//////////
	/**
	 * this method returns the overall time of the longest path.
	 * @param pl: given path list.
	 */
	public double longestPathByTime(PathList pl) {
		double overallTime=0;
		Iterator<Path> itp = pl.Iterator();
		while(itp.hasNext()) {
			Path currentPath = itp.next();
			double currentTime = currentPath.getOverallTime();
			if(currentTime>overallTime) {
				overallTime=currentTime;
			}
		}
		return overallTime;
	}

	/**
	 * this method makes the all the pacman from the pacman's list to move by a given time.
	 * @param pacList: the given pacman's list.
	 * @param pathList: the given path list.
	 * @param time: the given time.
	 */
	public void pacmanMove(PacmanList pacList,PathList pathList, double time) {
		int pacIndex=0;
		Iterator<Pacman> itPac = pacList.Iterator();
		while(itPac.hasNext()) {
			Pacman currentPac = itPac.next();
			pacIndex=pacList.getPacIndex(currentPac);        //gets the pacman's index from the pacman's list.
			Path currentPath=pathList.getPath(pacIndex);     //finds the path of that pacman.
			if(time<=currentPath.getOverallTime()) {
				Point3D nextPoint = GameAlgo.pacPosSpecificTime(currentPac, pathList, time);     //returns the point with the pacman's position after the move.
				currentPac.setLat(nextPoint.x());              //sets the pacman's latitude to be as the new position latitude.
				currentPac.setLon(nextPoint.y());              //sets the pacman's longitude to be as the new position longitude.
				currentPac.setAlt(nextPoint.z());              //sets the pacman's altitude to be as the new position altitude.
			}
			else {
				currentPac.setLat(currentPath.lastPoint().x());      //sets the pacman's latitude to be as the path's last point latitude.
				currentPac.setLon(currentPath.lastPoint().y());      //sets the pacman's longitude to be as the path's last point longitude.
				currentPac.setAlt(currentPath.lastPoint().z());      //sets the pacman's altitude to be as the path's last point altitude.
			}

		}

	}

	public void paint(Graphics g)
	{

		g.drawImage(myImage, 0, 0, getWidth()-8, getHeight()-8, this);
		if(x!=-1 && y!=-1)
		{
			int r = 15;
			x = x - (r / 2);
			y = y - (r / 2);

		}
		Graphics2D g2d = (Graphics2D) g;
		if(pac) {      //if the user selected 'pacman' option.
			Pacman pacman = new Pacman(mainPacmanList.getSize(), p.x(), p.y(), p.z(), 1, 1, 0);  //creating pacman with the current point values.
			if(isFirstPac) {            //if this is the first pacman.
				mainPacmanList.add(pacman);       //adding the pacman to the pacman list.
				isFirstPac=false;
			}
			else if(mainPacmanList.lastPacman().getLat()!=p.x() && mainPacmanList.lastPacman().getLon()!=p.y()) {   //if this pacman already exists, don't add it.
				mainPacmanList.add(pacman);       //adding the pacman to the pacman list.
			}
		}

		if(fru) {
			Fruit fruit = new Fruit(mainFruitsList.getSize(), p.x(), p.y(), p.z(), 1);     //creating fruit with the current point values.
			if(isFirstFru) {          //if this is the first fruit.
				mainFruitsList.add(fruit);        //adding the fruit to the fruits list.
				isFirstFru=false;
			}
			else if(mainFruitsList.lastFruit().getLat()!=p.x() && mainFruitsList.lastFruit().getLon()!=p.y()) {   //if this fruit already exists, don't add it.
				mainFruitsList.add(fruit);        //adding the fruit to the fruits list.
			}
		}


		//**//drawing the fruits//**//
		Iterator<Fruit> itf = mainFruitsList.Iterator();
		while(itf.hasNext()) {
			Fruit temp = itf.next();		
			Point3D point1 = new Point3D(temp.getLat(), temp.getLon(), temp.getAlt());
			currentPointInPixels=Map.coordsToPixels(point1, getWidth(), getHeight());
			g2d.drawImage(myFru1, currentPointInPixels[0]-10, currentPointInPixels[1]-10, 20, 20, this); 
		}
		mainFruitsList.print();


		if(def) {         //neither pacman nor fruit were selected.
			g2d.setColor(Color.blue);
			currentPointInPixels=Map.coordsToPixels(p, getWidth(), getHeight());
			g2d.fillOval(currentPointInPixels[0]-5, currentPointInPixels[1]-5, 10, 10);      //drawing the current point.
		}


		//**//drawing the paths//**//
		Iterator<Path> itPathList = pathlist.Iterator();
		while(itPathList.hasNext()) {
			Path temp = itPathList.next();
			System.out.println("overall time: "+temp.getOverallTime());
			Point3D prevPoint = temp.firstPoint();
			Point3D startPoint = temp.firstPoint();
			int [] nextPointPixels= new int[2];
			int [] startPointPixels = new int [2]; 
			startPointPixels=Map.coordsToPixels(startPoint, getWidth(), getHeight());
			Stroke s = new BasicStroke(3.5f); //makes the lines and dots thicker.
			Iterator<Point3D> itPoint = temp.Iterator();
			while(itPoint.hasNext()) {
				Point3D tempPoint = itPoint.next();
				currentPointInPixels=Map.coordsToPixels(prevPoint, getWidth(), getHeight());
				nextPointPixels=Map.coordsToPixels(tempPoint, getWidth(), getHeight());
				g2d.setStroke(s);
				g2d.setColor(temp.SetColor());
				g2d.drawLine(currentPointInPixels[0], currentPointInPixels[1], nextPointPixels[0], nextPointPixels[1]); //draw a single section of a path with a line between two points. 
				g2d.setColor(Color.WHITE);  
				g2d.fillOval(currentPointInPixels[0]-5, currentPointInPixels[1]-5, 10, 10); //prints the eaten fruits with white dots.
				g2d.drawImage(start, startPointPixels[0]-14, startPointPixels[1]-40, 40, 40, this); //prints the start point for each pacman with a flag.
				prevPoint=tempPoint;
			}
		}
		//***//draws each pacman in it's current position //***//
		Iterator<Pacman> itp = mainPacmanList.Iterator();
		while(itp.hasNext()) {
			Pacman tempPac = itp.next();
			Point3D point = new Point3D(tempPac.getLat(), tempPac.getLon(), tempPac.getAlt());
			currentPointInPixels=Map.coordsToPixels(point, getWidth(), getHeight());
			g2d.drawImage(myPac, currentPointInPixels[0]-15, currentPointInPixels[1]-15, 30, 30, this);     //drawing the current pacman.     

		}
		mainPacmanList.Print();
	}


	public void actionPerformed(ActionEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		//System.out.println("mouse entered");
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		//System.out.println("mouse exited");		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}
	public PathList getPathList() {
		return pathlist;
	}
	public PacmanList getPacmanList() {
		return mainPacmanList;
	}
	/**
	 * this class responsible to makes the game run.
	 */
	public class RunPac implements ActionListener  {

		private MainWindow Mw;

		/**
		 * this method is a constructor.
		 */
		public RunPac(MainWindow mainWindow) {
			this.Mw=mainWindow;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand()=="Exit") {
				//clears the game board (clears 'PacmanList' and 'FruitsList')
				mainPacmanList.clear(); 
				mainFruitsList.clear();
				System.exit(0);
			}

			if(e.getActionCommand()=="Path2kml") {
				JFileChooser jfc = new JFileChooser();
				File dir = new File("data3/");
				jfc.setCurrentDirectory(dir);
				FileFilter fl = new FileNameExtensionFilter("KML file", "kml");
				jfc.setFileFilter(fl);
				int returnVal=jfc.showSaveDialog(getParent());
				if(returnVal==JFileChooser.APPROVE_OPTION) {
					File savedFile=jfc.getSelectedFile();
					String fileName = savedFile.toString()+".kml";
					File_format.Path2kml.path2kml(pathlist, fileName);
				}
			}

			if(e.getActionCommand()=="Save") {
				JFileChooser jfc = new JFileChooser();
				File dir = new File("data3/");
				jfc.setCurrentDirectory(dir);
				FileFilter fl = new FileNameExtensionFilter("CSV file", "csv");
				jfc.setFileFilter(fl);
				int returnVal=jfc.showSaveDialog(getParent());
				if(returnVal==JFileChooser.APPROVE_OPTION) {
					File savedFile=jfc.getSelectedFile();
					String fileName = savedFile.toString()+".csv";
					try {
						Algorithm.csvWriter.csvWriter(fileName, mainPacmanList, mainFruitsList);
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
				}				
			}

			//loads a .csv file
			if(e.getActionCommand()=="Load") {
				JFileChooser fc = new JFileChooser();
				File dir = new File("data3/");
				fc.setCurrentDirectory(dir);
				fc.showOpenDialog(getParent());
				if(fc.getSelectedFile()!=null && fc.getSelectedFile().getName().endsWith(".csv")) {
					PacmanCsvReader.csv(fc.getSelectedFile().getPath(), mainPacmanList, mainFruitsList); //reading the file and generates the PacmanList and the FruitsList 
					repaint();
				}

				else {
					System.out.println("ERROR: the file must be a csv file."); //if the file isn't a .csv file
				}
			}
			if(e.getActionCommand()=="Pacman") {
				pac=true;
				fru=false;
				def=false;
			}

			if(e.getActionCommand()=="Fruit") {
				fru=true;
				pac=false;
				def=false;
			}

			if(e.getActionCommand()=="Clear") {
				mainPacmanList.clear();
				mainFruitsList.clear();
				pathlist.clear();
				fru=false;
				pac=false;
				isFirstPac=true;
				isFirstFru=true;
				time=0;
				repaint();
			}
			//starts the game.
			if(e.getActionCommand()=="Run") {
				fru=false;
				pac=false;
				def=false;
				pathlist = new PathList (mainPacmanList);
				GameAlgo.pathArrange(pathlist, mainFruitsList);      //arrange the path of each pacman.
				Animation a = new Animation(Mw);
				Thread myThread = new Thread(a);
				myThread.start();

			}

		}

	}


}
