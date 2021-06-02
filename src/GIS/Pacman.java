package GIS;

import GIS_project4.Character;
import Coords.myCoords;
import Geom.Point3D;

public class Pacman extends Character{
	/**
	 * this method is a constructor.
	 */
	public Pacman() {
		super();
		score=0;
	}
	/**
	 * this method is a constructor.
	 */
	public Pacman(int id, double lat, double lon, double alt, double speed, double radius, int score) {
		super(id,lat,lon,alt,speed,radius);
		this.score=score;
	}
	/**
	 * this method is a copy constructor.
	 */
	public Pacman(Pacman p) {
		super(p.id,p.lat,p.lon,p.alt,p.speed,p.radius);
		this.score=p.score;
	}
	/**
	 * this method returns the time from a pacman to a fruit
	 * by calculate the distance between them, and divides it by the pacman's speed.
	 * @param fru: the given fruit.
	 */
	public double timePacToFruit(Fruit fruit) {
		double time=0;
		Point3D point=fruit.gameObjectToPoint3D();
		myCoords m = new myCoords();
		double dist=m.distance3d(point, this.gameObjectToPoint3D());
		time=dist/this.getSpeed();
		return time;

	}

	/**
	 * this method sets the pacman's score.
	 */
	public void setScore(int score) {
		this.score = score;
	}
	public int getScore() {
		return score;
	}
	
	
	//////private//////
	private int score;

}
