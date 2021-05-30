package GIS_project4;

import Coords.myCoords;
import Geom.Point3D;

public class GameObject {

	public GameObject() {
		id=0;
		location= new Point3D(0,0,0);
	}
	public GameObject(int id,Point3D location) {
		this.id=id;
		this.location=location;
	}
	public GameObject(GameObject objectType) {
		objectType.id=id;
		objectType.location=location;
	}
	
	/**
	 * this method returns a point with the coordinates values of a gameObject.
	 */
	public Point3D gameObjectToPoint3D() {
		Point3D point = new Point3D(this.location);
		return point;
	}
	
	public void updateObjectLocation(double lat,double lon) {
		this.location.setX(lat);
		this.location.setY(lon);
	}
	 
	/**
	 * this method returns the distance between an gameObject and a given point.
	 * @param p: the given point.
	 */
	public double distToPoint(Point3D p) {
		myCoords m = new myCoords();
		double dist=m.distance3d(this.gameObjectToPoint3D(), p);
		return dist;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id=id;
	}

	/////////private////////
	protected int id;
	protected Point3D location;

}
