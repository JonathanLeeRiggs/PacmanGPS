package Algorithm;

import java.util.ArrayList;
import java.util.Iterator;

import Coords.myCoords;
import GIS.Fruit;
import GIS.FruitsList;
import GIS.PacmanList;
import GIS_project4.BoxList;
import GIS_project4.GhostsList;
import GIS_project4.Player;
import Geom.Point3D;
import graph.Graph;
import graph.Graph_Algo;
import graph.Node;

public class GameAlgo4 {
	/**
	 * this function gives the azimuth between two points.
	 * @param playerPoint : the first point that represents the player current position
	 * @param destPoint : the destination point.
	 * @return azimuth.
	 */
	public static double movePlayer(Point3D playerPoint,Point3D destPoint) {
		double[]azi=new double[3];
		myCoords m = new myCoords();
		azi=m.azimuth_elevation_dist(playerPoint, destPoint); //the calculation of the azimuth
		return azi[0];
	}

	/**
	 * this method returns a graph with all the edges between all the points in it.
	 * @param start: the source point.
	 * @param end: the target point.
	 * @param bl: the given boxes list.
	 */
	public static Graph buildGraph(Point3D start,Point3D end,BoxList bl) {
		ArrayList<Point3D> boxCorners=bl.boxesCorners();	   //inserts all the corners of all the boxes from the boxList.
		myCoords m= new myCoords();
		///**///builds the Graph ///**///
		Graph graph= new Graph();
		String source = "a",target = "b"; 
		Node a=new Node(source);
		a.set_id(0);
		graph.add(a);

		Point3D[] graphPoints = new Point3D[boxCorners.size()+2];   //array with all the graph point. source, target and all the boxes corners.
		graphPoints[0]=start;                         //puts the start point in the first place of the array.
		graphPoints[graphPoints.length-1]=end;        //puts the end point in the last place of the array.
		for(int i=0;i<boxCorners.size();i++) {        //as long as there's points in the corners array, add them to the graph.
			Node temp = new Node(""+(i+1));
			temp.set_id(i+1);
			graph.add(temp);
			graphPoints[i+1] = new Point3D(boxCorners.get(i));      //adds the boxes corners to the graphPoint array.
		}
		Node b=new Node(target);      //creating the target node.
		b.set_id(graph.size()); 
		graph.add(b);                 //adds the target point to the graph.

		///add edges///
		for(int i=0;i<graphPoints.length;i++) {
			for(int j=i+1;j<graphPoints.length;j++) {
				if(graphPoints[i].isDirect(graphPoints[j], bl)) {    //if there is a direct line between the two point
					if(i==0) {                                       //if it's the source point.
						graph.addEdge(source,""+j, m.distance3d(graphPoints[i], graphPoints[j]));	
					}
					else if(j==graphPoints.length-1) {               //if it's the target point.
						graph.addEdge(""+i,target, m.distance3d(graphPoints[i], graphPoints[j]));
					}
					else {
						graph.addEdge(""+i,""+j, m.distance3d(graphPoints[i], graphPoints[j]));
					}
				}
			}
		}
		return graph;
	}

	/**
	 * this method finds the quickest path from a player to a fruit.
	 * @param fl: the given fruits list.
	 * @param bl: the gives boxes list.
	 * @param player: the given player.
	 * @return the closest fruit index.
	 */
	public static int quickestPath(FruitsList fruitsList, BoxList boxList, Player player) {
		int closestFruitIndex=0;
		double currentDist=0;
		double minDist=Double.MAX_VALUE;
		myCoords m = new myCoords();
		Iterator<Fruit> fruitsIterator = fruitsList.Iterator();
		while(fruitsIterator.hasNext()) {
			Fruit tempFruit = fruitsIterator.next();
			if(player.gameObjectToPoint3D().isDirect(tempFruit.gameObjectToPoint3D(), boxList)) { // if there is a direct line between the player and the fruit
				currentDist=m.distance3d(player.gameObjectToPoint3D(), tempFruit.gameObjectToPoint3D());     //calculates the distance between them.
			}
			else { //the path from player to the current fruit isn't direct;
				Graph g=new Graph();
				g = GameAlgo4.buildGraph(player.gameObjectToPoint3D(),tempFruit.gameObjectToPoint3D(), boxList);
				Graph_Algo.dijkstra(g, "a");
				Node b=new Node("b");
				b = g.getNodeByName("b");
				currentDist=b.getDist();
			}
			if(currentDist<minDist) {
				minDist=currentDist;
				closestFruitIndex=fruitsList.flArray().indexOf(tempFruit);
			}
		}
		return closestFruitIndex;
	}

	/**
	 * this function updates the board according to current game situation.
	 * if a fruit or a pacman were eaten, they get removed from the game.
	 * @param board: the game board
	 */
	public static void updateBoard(ArrayList<String> board,PacmanList pl, FruitsList fl, GhostsList gl, Player player) {
		ArrayList<Integer> fruitsLeft=new ArrayList<>();  //the array list of the indexes of the fruits
		ArrayList<Integer> pacmanLeft=new ArrayList<>(); //the array list of the indexes of the pacman
		for(int i=0;i<board.size();i++) {
			String temp=board.get(i);
			String [] splitedRow = temp.split(",");
			if(splitedRow[0].equals("P")) {
				pl.updatePacman(splitedRow); //sets the pacman's location to it's new location.
				pacmanLeft.add(Integer.parseInt(splitedRow[1])); //adds the pacman to the pacmanLeft array. 
			}
			else if(splitedRow[0].equals("M")) {
				player.updateObjectLocation(Double.parseDouble(splitedRow[2]), Double.parseDouble(splitedRow[3])); //sets the player's location to it's new location.
			}
			else if(splitedRow[0].equals("F")) {
				fruitsLeft.add(Integer.parseInt(splitedRow[1])); //adds the fruit to the fruitsLeft array.
			}
			else if(splitedRow[0].equals("G")) {
				gl.updateGhost(splitedRow); //sets the ghost's location to it's new location.
			}
		}
		fl.updateFruitsList(fruitsLeft);  //updates the fruits list according to the fruits that weren't eaten. 
		pl.updatePacmanList(pacmanLeft);  //updates the pacman list according to the pacman that weren't eaten. 


	}
}

