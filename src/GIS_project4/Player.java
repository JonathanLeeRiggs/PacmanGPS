package GIS_project4;

import java.util.Iterator;

import Coords.myCoords;
import GIS.Fruit;
import GIS.FruitsList;

public class Player extends Character{
	/**
	 * this method is a constructor.
	 */
	public Player() {
		super();
		score=0;
	}
	/**
	 * this method is a constructor.
	 */
	public Player(int id, double lat, double lon, double alt, double speed, double radius, double score) {
		super(id,lat,lon,alt,speed,radius);
		this.score=score;
	}
	/**
	 * this method is a copy constructor.
	 */
	public Player(Player player) {
		super(player.id,player.lat,player.lon,player.alt,player.speed,player.radius);
	}
	
	/**
	 * this method returns the player's score.
	 */
	public double getScore() {
		return score;
	}
	/**
	 * this method sets the player's score.
	 */
	public void setScore(double score) {
		this.score = score;
	}
	/**
	 * this method checks if the player location is in a box.
	 * @param b: the given box.
	 * @return true if the player location is in the box, false otherwise.
	 */
	public boolean isInBox(Box box) {
		boolean isInside = false;
		if((this.getLat()>=box.getMin().x() && this.getLat()<=box.getMax().x()) &&
				(this.getLon()>=box.getMin().y() && this.getLon()<=box.getMax().y())) {
			isInside=true;
		}
		return isInside;
	}
	/**
	 * this method returns the fruit's index of the closest fruit to the player.
	 * @param fl: the given fruits list.
	 */
	public int closestFruitToPlayer(FruitsList freuitsList) {
		int fruitIndex=-1;
		double minDist=Double.MAX_VALUE;
		double dist=0;
		myCoords m = new myCoords();
		Iterator<Fruit> itf = freuitsList.Iterator();
		while(itf.hasNext()) {
			Fruit tempFruit = itf.next();
			dist=m.distance3d(tempFruit.gameObjectToPoint3D(), this.gameObjectToPoint3D());
			if(dist<minDist) {
				minDist=dist;
				fruitIndex=freuitsList.flArray().indexOf(tempFruit);//return the index of the fruit in the arrayList.
			}
		}
		return fruitIndex;
	}
	/**
	 * this method returns the azimuth from the player to a close ghost. if there is no ghost closer than 4 meters to the player,
	 * the azimuth remains -1. if there is a ghost less than 4 meters from the player, it calculates the azimuth between them,
	 * and return it.
	 * @param gl: the given ghosts list.
	 */
	public double isGhostClose(GhostsList ghostList) {
		double azimuth=-1;
		double dist=0;
		double [] aziElevDist = new double [2];     //for the azimuth_elevation_dist function.
		myCoords m = new myCoords();
		Iterator<Ghost> itg = ghostList.Iterator();
		while(itg.hasNext()) {   //calculates the distance between each ghost to the player to see if its too close
			Ghost tempGhost = itg.next();
			dist=m.distance3d(this.gameObjectToPoint3D(), tempGhost.gameObjectToPoint3D());
			if(dist<15) { //ghost is too close to player (less then 4 meters)
				aziElevDist=m.azimuth_elevation_dist(this.gameObjectToPoint3D(), tempGhost.gameObjectToPoint3D());
				azimuth=aziElevDist[0];
				break;
			}
		}
		return azimuth;
	}

	//////private///////
	private double score;
}
