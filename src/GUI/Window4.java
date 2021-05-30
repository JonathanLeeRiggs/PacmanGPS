package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import Algorithm.GameAlgo4;
import GIS.Fruit;
import GIS.FruitsList;
import GIS.Map;
import GIS.Pacman;
import GIS.PacmanList;
import GIS_project4.Box;
import GIS_project4.BoxList;
import GIS_project4.Ghost;
import GIS_project4.GhostsList;
import GIS_project4.ObjectFactory;
import GIS_project4.GameObject;
import GIS_project4.Player;
import Geom.Point3D;
import Robot.Play;

public class Window4 extends JFrame implements MouseListener,ActionListener {
	private final String PACMAN="GIS.Pacman";
	private final String GHOST="GIS_project4.Ghost";
	private final String FRUIT="GIS.Fruit";
	private final String BOX="GIS_project4.Box";

	private static final long serialVersionUID = 3751863602564448836L;

	PacmanList mainPacmanList = new PacmanList();        //the game pacman list.
	FruitsList mainFruitsList = new FruitsList();        //the game fruits list.
	BoxList mainBoxList = new BoxList();                 //the game boxes list.
	GhostsList mainGhostsList = new GhostsList();        //the game ghosts list.

	ArrayList<Point3D> boxCorners= new ArrayList<Point3D>();

	Player player = new Player();           //the game player.
	boolean isFileSelected=false;           //checks if the user already selected a gameboard.
	boolean isPlayerInit=false;             //checks if the user already created the player.
	boolean isOver=false;
	boolean areThereBoxes=false;
	boolean isSimMode=false;
	Point3D initSim= new Point3D(0, 0);
	double angle=0;                //angle of the player's direction.
	double timeLeft;               //time left for the game.
	int timeLeftPaint;             //the time left in int, so it looks better in the paint.
	DBconnection dbc = new DBconnection();              //database info.
	int rank=1;

	String file_name; //the file name of the game board.
	Play play;
	ArrayList<String> board =new ArrayList<String>();   //array list for the game board characters.
	String info;     //string for the game board statistics.
	String [] splitedInfo=null;      //array for the splited statistics.

	int []currentPointInPixels=new int[2];
	int []boxMaxPointInPixels=new int[2];
	Point3D p= new Point3D(0, 0);
	Point3D currentPlayerPos=new Point3D(0, 0);

	///the images decelerations///
	public BufferedImage mapImage;      //picture of the map.
	public BufferedImage pacmanImage;      //picture of the pacman.
	public BufferedImage fruitImage;      //picture of the fruit.
	public BufferedImage ghostImg;      //picture of the ghost.
	public BufferedImage goku;      //picture of the player.
	public BufferedImage goku2;      //picture of the player.
	public BufferedImage goku3;      //picture of the player.
	public BufferedImage playerInBox;      //picture of player's initialization error.
	public BufferedImage gameOver;      //picture of the map.

	public Window4() 
	{
		initGUI();		
		this.addMouseListener(this);
	}
	/**
	 * this function is the GUI initialization .
	 * this function creates the main window,the menu and all the buttons.
	 */
	private void initGUI() {
		MenuBar menuBar = new MenuBar();  //creates the menu bar

		//creates the "File" menu bar item
		Menu file = new Menu("File");  
		menuBar.add(file);
		MenuItem exit = new MenuItem("Exit");
		MenuItem clear = new MenuItem("Clear");
		MenuItem run = new MenuItem("Run");
		MenuItem sim = new MenuItem("Simulation");
		MenuItem setPlayerPos = new MenuItem("Set Initial Player Position");

		file.add(exit);
		file.add(clear);
		file.add(run);
		file.add(sim);
		file.add(setPlayerPos);

		//creates the "Gameboard Options" menu bar item
		Menu options = new Menu("Gameboard Options");
		menuBar.add(options);
		MenuItem example_1=new MenuItem("Example 1");
		MenuItem example_2=new MenuItem("Example 2");
		MenuItem example_3=new MenuItem("Example 3");
		MenuItem example_4=new MenuItem("Example 4");
		MenuItem example_5=new MenuItem("Example 5");
		MenuItem example_6=new MenuItem("Example 6");
		MenuItem example_7=new MenuItem("Example 7");
		MenuItem example_8=new MenuItem("Example 8");
		MenuItem example_9=new MenuItem("Example 9");

		options.add(example_1);
		options.add(example_2);
		options.add(example_3);
		options.add(example_4);
		options.add(example_5);
		options.add(example_6);
		options.add(example_7);
		options.add(example_8);
		options.add(example_9);

		this.setMenuBar(menuBar);

		ActionListener l = new Run(this);


		///activate the "file" buttons///

		run.addActionListener(l);
		sim.addActionListener(l);
		clear.addActionListener(l);
		exit.addActionListener(l);
		setPlayerPos.addActionListener(l);

		////activates the examples ("Gameboard Options") files///
		example_1.addActionListener(l);
		example_2.addActionListener(l);
		example_3.addActionListener(l);
		example_4.addActionListener(l);
		example_5.addActionListener(l);
		example_6.addActionListener(l);
		example_7.addActionListener(l);
		example_8.addActionListener(l);
		example_9.addActionListener(l);

		readAllImages(); //loads all the images, if something wrong, throws an error
	}

	int x = -1;
	int y = -1;

	/**
	 * this function is responsible for painting the game-board and all the characters
	 */
	public void paint(Graphics g)
	{
		g.drawImage(mapImage, 0, 0, getWidth()-8, getHeight()-8, this);
		if(x!=-1 && y!=-1) {
			int r = 15;
			x = x - (r / 2);
			y = y - (r / 2);
		}
		Graphics2D g2d = (Graphics2D) g; 	
		
		///draws the mainBoxList according to the selected file///
		Iterator<Box> boxIterator = mainBoxList.Iterator();
		boxListPainter(boxIterator, g2d,areThereBoxes);

		///draws the mainPacmanList according to the selected file///
		Iterator<Pacman> pacmanIterator = mainPacmanList.Iterator();
		pacmanPainter(pacmanIterator, g2d);

		///draws the mainFruitsList according to the selected file///
		Iterator<Fruit> fruitIterator = mainFruitsList.Iterator();
		fruitPainter(fruitIterator, g2d);

		///draws the mainGhostsList according to the selected file///
		Iterator<Ghost> ghostIterator = mainGhostsList.Iterator();
		ghostPainter(ghostIterator, g2d);

		//**//**//player//**//**//
		if(isSimMode) {
			this.player=new Player(0,initSim.x(),initSim.y(),0,20.0,1,0);
			isSimMode=false;
		}
		///creates the player according to the user location selection///
		if (isPlayerInit && mainFruitsList.getSize()>0){
			Player player1 = new Player(0,p.x(),p.y(),0,20.0,1,0) ; //creates a new player
			boolean isInBox=false;
			Iterator<Box> itBox = mainBoxList.Iterator();
			while(itBox.hasNext()) {
				Box tempBox=itBox.next();
				isInBox=player1.isInBox(tempBox);
				if(isInBox==true) {
					break;
				}
			}
			if(isInBox==false) {
				this.player=player1;  //copies it's address to the main player address
				repaint();
			}
			else {
				System.out.println("cannot put the player in a box!");
				g2d.drawImage(playerInBox, (getWidth()/2)-150, (getHeight()/2)-10, 300, 20, this);
			}
			isPlayerInit=false; //does this loop only one time after each "Set Initial Player Position" selection
			isSimMode=false;
		}
		int fontSize=30;
		scoreDrawing(g2d,player.getScore(),fontSize); //**score drawing**//
		currentPlayerPos=player.gameObjectToPoint3D();  //converting the player to Point3D .
		currentPointInPixels=Map.coordsToPixels(currentPlayerPos, getWidth(), getHeight()); //converting the Point3D to pixels.
		playerFigurePainter(player.getScore(),g2d,currentPointInPixels); //paints the correct player figure (goku/goku2/goku3)
		
		timeLeftPainter(timeLeft,g2d,fontSize); //paints the time left on the clock
		if(isOver) gameOverPainter(g2d,rank); //paints the "game over" and the rank at the end of the game
	}















	@Override
	/**
	 * this function is executed for each click of a mouse
	 */
	public void mouseClicked(MouseEvent arg) {
		System.out.println("pixels point: ("+ arg.getX() + "," + arg.getY() +")");
		x = arg.getX();
		y = arg.getY();
		p=GIS.Map.pixelsToCoords(x, y, getWidth(), getHeight());     //insert the coordinates values given by the pixels to a point.
		currentPointInPixels=GIS.Map.coordsToPixels(p, getWidth(), getHeight());        //insert the pixels given by the coordinates values to an double[].			
		angle=GameAlgo4.movePlayer(currentPlayerPos, p); //calculates the angle in which the player will move to
		repaint();

	}

	@Override
	public void mousePressed(MouseEvent arg) {	
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	public class Run implements ActionListener {
		private Window4 w4;
		/**
		 * this method is a constructor.
		 */
		public Run(Window4 window) {
			this.w4=window;
		}
		@Override
		///the operation for each of the "Gameboard Options" buttons///
		public void actionPerformed(ActionEvent e) {

			if(e.getActionCommand()=="Example 1") {
				file_name = "data4/Ex4_OOP_example1.csv";
				isFileSelected=true; 
				dbc.clear();
				dbc.setGameHash(2.12825983E9);
				initSim = new Point3D(32.105455859188545,35.209387956129035);
			}
			if(e.getActionCommand()=="Example 2") {
				file_name = "data4/Ex4_OOP_example2.csv";	
				isFileSelected=true;	
				dbc.clear();
				dbc.setGameHash(1.149748017E9);
				initSim = new Point3D(32.10493646539379,35.20907530580645);
			}
			if(e.getActionCommand()=="Example 3") {
				file_name = "data4/Ex4_OOP_example3.csv";	
				isFileSelected=true;
				dbc.clear();
				dbc.setGameHash(-6.8331707E8);
				initSim = new Point3D(32.103027465393794,35.20701051096774);
			}
			if(e.getActionCommand()=="Example 4") {
				file_name = "data4/Ex4_OOP_example4.csv";	
				isFileSelected=true;
				dbc.clear();
				dbc.setGameHash(1.193961129E9);
				initSim = new Point3D(32.103027465393794,35.20701051096774);
			}
			if(e.getActionCommand()=="Example 5") {

				file_name = "data4/Ex4_OOP_example5.csv";	
				isFileSelected=true;
				dbc.clear();
				dbc.setGameHash(1.577914705E9);
				initSim = new Point3D(32.103027465393794,35.20701051096774);
			}
			if(e.getActionCommand()=="Example 6") {
				file_name = "data4/Ex4_OOP_example6.csv";	
				isFileSelected=true;
				dbc.clear();
				dbc.setGameHash(-1.315066918E9);
				initSim = new Point3D(32.105378405727926,35.208202490322584);
			}
			if(e.getActionCommand()=="Example 7") {
				file_name = "data4/Ex4_OOP_example7.csv";	
				isFileSelected=true;
				dbc.clear();
				dbc.setGameHash(-1.377331871E9);
				initSim = new Point3D(32.102822441527444,35.21133550709678);
			}
			if(e.getActionCommand()=="Example 8") {
				file_name = "data4/Ex4_OOP_example8.csv";	
				isFileSelected=true;
				dbc.clear();
				dbc.setGameHash(3.06711633E8);
				initSim = new Point3D(32.10390678997613,35.203825385806454);
			}
			if(e.getActionCommand()=="Example 9") {
				file_name = "data4/Ex4_OOP_example9.csv";
				isFileSelected=true;
				dbc.clear();
				dbc.setGameHash(9.19248096E8);
				initSim = new Point3D(32.10390678997613,35.203825385806454);

			}


			//++//creates the game according to the relevant file.//++//
			if (isFileSelected) {
				dbc.setMyId(308545151);
				dbc.setMyId2(308224450);
				mainBoxList.clear();
				mainFruitsList.clear();
				mainGhostsList.clear();
				mainPacmanList.clear();
				boxCorners.clear();
				areThereBoxes=true;
				play = new Play(file_name); //creates the game according to the relevant file 
				play.setIDs(308545151,308224450); //the id's of the players.
				board =play.getBoard();     //gets the board's characters.
				creatingGameCharacters(board);  //creates all the characters according to the relevant file board.
				isSimMode=true;
				isFileSelected=false;
				repaint();
			}
			//the run button//
			if(e.getActionCommand()=="Run") {
				if(mainFruitsList.getSize()>0 && player.getLat()>0) {
					Animation4 a4 = new Animation4(w4); 
					Thread myThread = new Thread(a4);
					myThread.start();	//starts the animation thread
					isSimMode=false;
				}
			}
			if(e.getActionCommand()=="Simulation") {
				if(mainFruitsList.getSize()>0 && player.getLat()>0) {
					isSimMode=true;
					Simulation sim = new Simulation(w4); 
					Thread myThread = new Thread(sim);
					myThread.start();	//starts the animation thread
				}
			}
			if(e.getActionCommand()=="Exit") {
				System.exit(0);
			}
			if(e.getActionCommand()=="Clear") {
				mainBoxList.clear();
				mainFruitsList.clear();
				mainGhostsList.clear();
				mainPacmanList.clear();
				boxCorners.clear();
				dbc.clear();
				player = new Player();
				isOver=false;
				isSimMode=false;
				repaint();
			}
			//the Set Initial Player Position button//
			if(e.getActionCommand()=="Set Initial Player Position") {
				isPlayerInit=true;
			}
		}

	}

	////this method creates the Characters according to the given board//// 
	public void creatingGameCharacters(ArrayList<String> initialGameBoard) {

		for(int i=1;i<initialGameBoard.size();i++) { // creates an object for each character
			String temp=initialGameBoard.get(i); //the current objects info
			String [] splitedRow = temp.split(",");
			String objectTypeName=splitedRow[0]; //the type of the object ("P"/"B"/"F"/"G")
			ObjectFactory objectFactory= new ObjectFactory();
			GameObject objectType=objectFactory.makeObject(objectTypeName,splitedRow); //makes the relevant object
			insertObjectToList(objectType,mainPacmanList,mainFruitsList,mainBoxList,mainGhostsList);	
		}
	}

	////this method inserts the Characters to the relevant arrayList according to the object type//// 
	public void insertObjectToList(GameObject objectType,PacmanList mainPacmanList, FruitsList mainFruitsList, BoxList mainBoxList,
			GhostsList mainGhostsList) {

		if(objectType.getClass().getName().equals(PACMAN)) 
			mainPacmanList.add(objectType);  //inserts the pacman to the pacmans list                 
		else if(objectType.getClass().getName().equals(FRUIT))
			mainFruitsList.add(objectType);  //inserts the fruit to the fruits list  
		else if(objectType.getClass().getName().equals(GHOST))
			mainGhostsList.add(objectType);  //inserts the ghost to the ghost list  
		else if(objectType.getClass().getName().equals(BOX))
			mainBoxList.add(objectType);     //inserts the box to the box list  
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {

	}


	
	private void readAllImages() {
		try {
			///reads all the images///
			mapImage = ImageIO.read(new File("Pictures/Ariel1.png"));
			pacmanImage = ImageIO.read(new File("Pictures/PacMan.png"));
			fruitImage = ImageIO.read(new File("Pictures/g1.png"));
			ghostImg = ImageIO.read(new File("Pictures/ghost.png"));
			goku = ImageIO.read(new File("Pictures/goku.png"));
			goku2 = ImageIO.read(new File("Pictures/goku2.png"));
			goku3 = ImageIO.read(new File("Pictures/goku3.png"));
			playerInBox = ImageIO.read(new File("Pictures/playerinbox.png"));
			gameOver = ImageIO.read(new File("Pictures/game-over.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}			

	}

	
	//////**private painter  methods**///////
	

	/**
	 * this method gets the boxList iterator and draws the boxes on the map according to it.
	 * @param areThereBoxes 
	 * @param itb- box iterator
	 * @param g2d- graphics
	 */
	private void boxListPainter(Iterator<Box> itb,Graphics2D g2d, boolean areThereBoxes) {
		while(itb.hasNext()) {
			Box temp = itb.next();
			currentPointInPixels=Map.coordsToPixels(temp.getMin(), getWidth(), getHeight());    //converting the min Point3D to pixels.
			boxMaxPointInPixels=Map.coordsToPixels(temp.getMax(), getWidth(), getHeight());     //converting the max Point3D to pixels.
			int widthSize=boxMaxPointInPixels[0]-currentPointInPixels[0];      //the box's width from the min point to max point.
			int heightSize= currentPointInPixels[1]-boxMaxPointInPixels[1];     //the box's height from the min point to max point.
			g2d.setColor(Color.black);
			g2d.fillRect(currentPointInPixels[0],boxMaxPointInPixels[1], widthSize, heightSize);  //drawing the current box.
			if(areThereBoxes) {
				boxCorners = mainBoxList.boxesCorners();
				areThereBoxes=false;
			}
		}
	}

	/**
	 * this method gets the pacman list iterator and draws the pacman on the map according to it.
	 * @param pacmanIterator
	 * @param g2d
	 */
	private void pacmanPainter(Iterator<Pacman> pacmanIterator, Graphics2D g2d) {
		while(pacmanIterator.hasNext()) {
			Pacman tempPac = pacmanIterator.next();
			Point3D pacPoint3D = tempPac.gameObjectToPoint3D(); //converting the pacman to Point3D.
			currentPointInPixels=Map.coordsToPixels(pacPoint3D, getWidth(), getHeight());       //converting the Point3D to pixels.
			g2d.drawImage(pacmanImage, currentPointInPixels[0]-15, currentPointInPixels[1]-15, 30, 30, this);     //drawing the current pacman.     
		}

	}

	/**
	 * this method gets the fruits list iterator and draws the fruits on the map according to it
	 * @param fruitIterator
	 * @param g2d
	 */
	private void fruitPainter(Iterator<Fruit> fruitIterator, Graphics2D g2d) {
		while(fruitIterator.hasNext()) {
			Fruit tempFruit = fruitIterator.next();		
			Point3D point1 = new Point3D(tempFruit.getLat(), tempFruit.getLon(), tempFruit.getAlt()); //converting the fruit to Point3D.
			currentPointInPixels=Map.coordsToPixels(point1, getWidth(), getHeight());   //converting the Point3D to pixels.
			g2d.drawImage(fruitImage, currentPointInPixels[0]-10, currentPointInPixels[1]-10, 20, 20, this);  //drawing the fruit on the screen.
		}
	}
	/**
	 * this method gets the ghost list iterator and draws the ghosts on the map according to it
	 * @param ghostIterator
	 * @param g2d
	 */
	private void ghostPainter(Iterator<Ghost> ghostIterator, Graphics2D g2d) {
		while(ghostIterator.hasNext()) {
			Ghost tempGhost = ghostIterator.next();		
			Point3D ghostPoint3D =  tempGhost.gameObjectToPoint3D(); //converting the ghost to a Point3D.
			currentPointInPixels=Map.coordsToPixels(ghostPoint3D, getWidth(), getHeight());  //converting the Point3D to pixels.
			g2d.drawImage(ghostImg, currentPointInPixels[0]-10, currentPointInPixels[1]-10, 20, 20, this);   
		}	
		while(ghostIterator.hasNext()) {
			Ghost tempGhost = ghostIterator.next();		
			Point3D ghostPoint3D =  tempGhost.gameObjectToPoint3D(); //converting the ghost to a Point3D.
			currentPointInPixels=Map.coordsToPixels(ghostPoint3D, getWidth(), getHeight());  //converting the Point3D to pixels.
			g2d.drawImage(ghostImg, currentPointInPixels[0]-10, currentPointInPixels[1]-10, 20, 20, this);   
		}
	}
	/**
	 * this method gets the player's score and shows it on the screen on the left bottom corner
	 * @param g2d -grapics
	 * @param score- current player's score
	 */
	private void scoreDrawing(Graphics2D g2d, double score,int fontSize) {
		g2d.setColor(Color.black);
		g2d.setFont(new Font("ariel", Font.PLAIN, fontSize)); 
		for(int i=7;i<10;i++) g2d.drawString("score: "+player.getScore(), i, getHeight()-i);

		g2d.setColor(Color.GREEN);
		g2d.setFont(new Font("ariel", Font.PLAIN, fontSize)); 
		g2d.drawString("score: "+player.getScore(), 10, getHeight()-10);
	}
	/**
	 * this method gets the player's score and paint the correct figure of player
	 * @param currentPointInPixels2 
	 * @param score-players current score
	 * @param g2d-graphics
	 */
	private void playerFigurePainter(double score, Graphics2D g2d, int[] currentPointInPixels2) {
		if(player.getScore()<5) {  //if the score is under 5 points
			g2d.drawImage(goku, currentPointInPixels[0]-30, currentPointInPixels[1]-30, 60, 60, this); 
		}
		if(player.getScore()>=5 && player.getScore()<10) { //if the score is between 5 or 9
			g2d.drawImage(goku2, currentPointInPixels[0]-30, currentPointInPixels[1]-30, 60, 60, this); 
		}
		if(player.getScore()>=10) { //if the score is greater than 9
			g2d.drawImage(goku3, currentPointInPixels[0]-30, currentPointInPixels[1]-30, 60, 60, this); 
		}
	}
	/**
	 * this method gets the player's rank at the end of the game and display it on the screen
	 * @param g2d-graphics
	 * @param rank -players rank
	 */
	private void gameOverPainter(Graphics2D g2d, int rank) {
		g2d.drawImage(gameOver, (getWidth()/2)-300, (getHeight()/2)-300, 600, 600, this);
		////draws of the rank////
		g2d.setColor(Color.black);
		int fontSize=70;
		g2d.setFont(new Font("ariel", Font.PLAIN, fontSize)); 
		for(int i=267;i<273;i++)  g2d.drawString("Rank: "+rank, (getWidth()/2)-100, (getHeight()/2)+i);
		for(int i=97;i<103;i++)   g2d.drawString("Rank: "+rank, (getWidth()/2)-i, (getHeight()/2)+270);
		g2d.setColor(Color.ORANGE);
		fontSize=70;
		g2d.setFont(new Font("ariel", Font.PLAIN, fontSize)); 
		g2d.drawString("Rank: "+rank, (getWidth()/2)-100, (getHeight()/2)+270);
	}
	/**
	 * this method gets the the time left to play and display it on the bottom right corner
	 * @param timeLeft
	 * @param g2d
	 * @param fontSize
	 */
	private void timeLeftPainter(double timeLeft, Graphics2D g2d, int fontSize) {
		timeLeft = timeLeft/100;
		timeLeftPaint =(int)timeLeft;
		
		g2d.setFont(new Font("ariel", Font.PLAIN, fontSize));
		g2d.setColor(Color.black);
		for(int i=7;i<14;i++) g2d.drawString("time left: "+timeLeftPaint, getWidth()-(195+i), getHeight()-i);
		g2d.setColor(Color.GREEN);
		g2d.drawString("time left: "+timeLeftPaint, getWidth()-205, getHeight()-10);		
	}

	
	public static void main(String[] args) {
		Window4 window = new Window4();
		window.setVisible(true);
		window.setSize(window.mapImage.getWidth()-8,window.mapImage.getHeight()-8);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


	}
}
