package GIS;
import GIS_project4.GameObject;
import Geom.Point3D;

/**
 * this class represents a fruit. fruit has id, latitude, longitude, altitude and weight.
 */
public class Fruit extends GameObject{
	/**
	 * this method is a constructor.
	 */
	public Fruit() {
		super();
		weight=1;
	}
	
	/**
	 * this method is a constructor.
	 */
	public Fruit(int id, double lat, double lon, double alt, double weight) {
		super(id,new Point3D (lat,lon,alt));
		this.weight=weight;
	}
	/**
	 * this method is a copy constructor.
	 */
	public Fruit(Fruit f) {
		super(f.id,f.location);
		this.weight=f.weight;
	}
	public String toString() {
		String s="Fruit id: "+id+" ,lat: "+lat+" ,lon: "+lon+" ,alt: "+alt+" ,weight: "+weight;
		return s;
	}
	/**
	 * this method returns the latitude of the fruit.
	 */
	public double getLat() {
		return lat;
	}
	/**
	 * this method returns the longitude of the fruit.
	 */
	public double getLon() {
		return lon;
	}
	/**
	 * this method returns the altitude of the fruit.
	 */
	public double getAlt() {
		return alt;
	}
	public double getWeight() {
		return weight;
	}
	//////private//////
	double lat=this.location.x();
	double lon=this.location.y();
	double alt=this.location.z();
	double weight;
}
