package GUI;

import java.util.ArrayList;
import Algorithm.GameAlgo4;
import Geom.Point3D;
import graph.Graph;
import graph.Graph_Algo;
import graph.Node;

public class Simulation implements Runnable {
	private Window4 gameBoard;
	public Simulation(Window4 w4) {
		this.gameBoard=w4;
	}

	@Override
	public void run() {
		String map_data = gameBoard.play.getBoundingBox();	
		System.out.println(map_data);
		System.out.println();
		gameBoard.play.setInitLocation(gameBoard.player.getLat(),gameBoard.player.getLon()); //the starting position of the player
		gameBoard.timeLeft=100000;  //the maximum play time in ms.
		gameBoard.play.start();  //starts the game
		gameBoard.info = gameBoard.play.getStatistics();  //the game current statistics.
		System.out.println(gameBoard.info);

		while(gameBoard.mainFruitsList.getSize()>0 && gameBoard.timeLeft>0) {  //runs as long as there are fruits left in the game or the time isn't over
			double aziPlayerGhost=gameBoard.player.isGhostClose(gameBoard.mainGhostsList);    //calculates the azimuth between the player and a ghost less than 4 meters away.
			if(aziPlayerGhost!=-1) {            //if there is a ghost less than 4 meters away
				gameBoard.play.rotate(aziPlayerGhost+135);  //the angle in which the player is moving to. to avoid ghosts.
			}
			else {   //if there isn't a ghost less than 4 meters away
				int closestFruitId=GameAlgo4.quickestPath(gameBoard.mainFruitsList, gameBoard.mainBoxList, gameBoard.player);    //finds the quickest path from the player to a fruit.
				if(gameBoard.player.gameObjectToPoint3D().isDirect(gameBoard.mainFruitsList.flArray().get(closestFruitId).gameObjectToPoint3D(),
						gameBoard.mainBoxList)) {    //if the player has a direct path to the closest fruit.
					gameBoard.play.rotate(GameAlgo4.movePlayer(gameBoard.player.gameObjectToPoint3D(),
							gameBoard.mainFruitsList.flArray().get(closestFruitId).gameObjectToPoint3D()));  //the angle in which the player is moving to
				}
				else {  //the player doesn't have a direct path to the closest fruit.
					Graph graph=new Graph();
					graph = GameAlgo4.buildGraph(gameBoard.player.gameObjectToPoint3D(),
							gameBoard.mainFruitsList.flArray().get(closestFruitId).gameObjectToPoint3D(), gameBoard.mainBoxList);   //creating a graph when the source is the player and the taeget is the closest not-direct fruit.
					Graph_Algo.dijkstra(graph, "a");     //using dijkstra algorithm.
					Node b=new Node("b");
					b = graph.getNodeByName("b");
					ArrayList<String> shortestPath = b.getPath();
					Point3D directionPoint = gameBoard.boxCorners.get(Integer.parseInt(shortestPath.get(1))-1);
					gameBoard.play.rotate(GameAlgo4.movePlayer(gameBoard.player.gameObjectToPoint3D(), directionPoint));  //the angle in which the player is moving to
				}
			}
			gameBoard.splitedInfo=gameBoard.info.split(",");
			gameBoard.timeLeft=Double.parseDouble(gameBoard.splitedInfo[3].substring(11)); //the current time left
			gameBoard.player.setScore(Double.parseDouble(gameBoard.splitedInfo[2].substring(6)));  //the current player's score
			gameBoard.board = gameBoard.play.getBoard(); //the current game board situation
			gameBoard.repaint();
			GameAlgo4.updateBoard(gameBoard.board, gameBoard.mainPacmanList, gameBoard.mainFruitsList, gameBoard.mainGhostsList,gameBoard.player); //updates the game board.
			gameBoard.info = gameBoard.play.getStatistics();  //the game current statistics.
			System.out.println(gameBoard.info);
			try {
				Thread.sleep(70); 
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		gameBoard.play.rotate(gameBoard.angle);  //the angle in which the player is moving to
		gameBoard.info = gameBoard.play.getStatistics();  //the game current statistics.
		gameBoard.splitedInfo=gameBoard.info.split(",");
		gameBoard.player.setScore(Double.parseDouble(gameBoard.splitedInfo[2].substring(6)));
		System.out.println(gameBoard.info);
		gameBoard.play.stop();  //stops the game.
		System.out.println("**** Game Over ****");
		gameBoard.dbc.setScore(gameBoard.player.getScore());
		gameBoard.rank=gameBoard.dbc.rank();
		gameBoard.isOver=true;
		gameBoard.repaint();
	};

}
