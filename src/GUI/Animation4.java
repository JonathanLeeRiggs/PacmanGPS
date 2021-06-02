package GUI;

import Algorithm.GameAlgo4;

public class Animation4 implements Runnable {

	public Animation4(Window4 w4) {
		this.w4=w4;
	};
	@Override 	
	public void run() {
		String map_data = w4.play.getBoundingBox();	
		System.out.println(map_data);
		System.out.println();
		w4.play.setInitLocation(w4.player.getLat(),w4.player.getLon()); //the starting position of the player
		w4.timeLeft=100000;  //the maximum play time in ms.
		w4.play.start();  //starts the game
		w4.info = w4.play.getStatistics();  //the game current statistics.
		System.out.println(w4.info);
		while(w4.mainFruitsList.getSize()>0 && w4.timeLeft>0) {  //runs as long as there are fruits left in the game or the time isn't over
			w4.play.rotate(w4.angle);  //the angle in which the player is moving to
			w4.splitedInfo=w4.info.split(",");
			w4.timeLeft=Double.parseDouble(w4.splitedInfo[3].substring(11)); //the current time left
			w4.player.setScore(Double.parseDouble(w4.splitedInfo[2].substring(6)));  //the current player's score
			w4.board = w4.play.getBoard(); //the current game board situation
			w4.repaint();
			GameAlgo4.updateBoard(w4.board,w4.mainPacmanList, w4.mainFruitsList, w4.mainGhostsList, w4.player); //updates the game board.
			w4.info = w4.play.getStatistics();  //the game current statistics.
			System.out.println(w4.info);
			try {
				Thread.sleep(70); 
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		w4.play.rotate(w4.angle);  //the angle in which the player is moving to
		w4.info = w4.play.getStatistics();  //the game current statistics.
		w4.splitedInfo=w4.info.split(",");
		w4.player.setScore(Double.parseDouble(w4.splitedInfo[2].substring(6)));
		System.out.println(w4.info);
		w4.play.stop();  //stops the game.
		System.out.println("**** Game Over ****");
		w4.isOver=true;
		w4.dbc.setScore(w4.player.getScore());
		w4.rank=w4.dbc.rank();
		w4.repaint();
	}
	
	
	////private/////
	Window4 w4;





}
