package GIS;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import Coords.myCoords;
import Geom.Point3D;

/**
 * this method is a constructor.
 */
public class Path {
	/**
	 * this method sets a pacman to the beginning of the path.
	 */
	public Path(Pacman pac) {
		alp = new ArrayList<>();
		pacPath=pac;
		alTime = new ArrayList<>();
		overallTime=0;
		Point3D pacPoint=new Point3D(pac.getLat(), pac.getLon(),pac.getAlt());
		alp.add(pacPoint);
		alTime.add(0.0);
	}

	/**
	 * this method adds a fruit to the path.
	 */
	public void fruitAdd(Fruit fruit) {
		myCoords m = new myCoords();
		double dist=m.distance3d(this.lastPoint(), fruit.gameObjectToPoint3D());
		double time=dist/this.getPac().getSpeed();
		this.alp.add(fruit.gameObjectToPoint3D());
		this.alTime.add(time);
		this.overallTime+=time;
	}
	/**
	 * this method is an iterator.
	 * @return the array of points iterator.
	 */
	public Iterator<Point3D> Iterator() {
		return alp.iterator();
	}
	/**
	 * this method is an iterator.
	 * @return the array of time iterator.
	 */
	public Iterator<Double> timeIterator() {
		return alTime.iterator();
	}

	/**
	 * this method adding the given time to the time list and sums it up with the overall time.
	 * @param time: the given time.
	 */
	public void adjustTime(double time) {
		alTime.add(time);
		double newTime=this.overallTime+time;
		this.overallTime=newTime;
	}
	/**
	 * this method returns the size of the points list of a path.
	 */
	public int getPointListSize() {
		return alp.size();
	}

	/**
	 * this method returns the last point in the points array.
	 */
	public Point3D lastPoint() {
		return alp.get(alp.size()-1);
	}
	/**
	 * this method returns the first point in the points array.
	 */
	public Point3D firstPoint() {
		return alp.get(0);
	}

	/**
	 * this method return the id of the path.
	 */
	public int getId() {
		return pacPath.getId();
	}
	/**
	 * this method return the radius of the path.
	 */
	public double getRadius() {
		return pacPath.getRadius();
	}
	/**
	 * this method return the overall time of the path.
	 */
	public double getOverallTime() {
		return overallTime;
	}

	/**
	 * this method returns the index of the given time in the time list.
	 * @param time: the given time.
	 */
	public int getIndexOfTime(double time) {		
		return alTime.indexOf(time);
	}
	/**
	 * this method returns a point from given index.
	 * @param index: the given index.
	 */
	public Point3D getPointByIndex(int index) {
		return alp.get(index);
	}
	/**
	 * this method returns a index from given point.
	 * @param p: the given point.
	 */
	public int getIndexOfPoint(Point3D p) {
		return alp.indexOf(p);
	}
	/**
	 * this method returns the time of a given index.
	 * @param index: the given index.
	 */
	
	public double getTimeOfIndex(int index) {		
		return alTime.get(index);
	}
	/**
	 * this method return the speed of the path's pacman.
	 */
	public double getSpeed() {
		return pacPath.getSpeed();
	}
	/**
	 * this method return the pacman of the path.
	 */
	public Pacman getPac() {		
		return pacPath;
	}
	/**
	 * this method returns a random color.
	 */
	public Color SetColor() {
		Color rndColor = new Color(red, green, blue);
		return rndColor;

	}
	/////private//////
	private ArrayList<Point3D> alp = new ArrayList<>();
	private ArrayList<Double> alTime = new ArrayList<>();
	private double overallTime;
	private Pacman pacPath;
	Random rnd = new Random();
	float red = rnd.nextFloat();
	float green = rnd.nextFloat();
	float blue = rnd.nextFloat();
	
	

}
