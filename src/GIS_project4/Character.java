package GIS_project4;

import Geom.Point3D;

public class Character extends GameObject{
	
	public Character() {
		super();
		speed=1;
		radius=1;
	}
	public Character(int id,double lat, double lon, double alt, double speed, double radius) {
		super(id,new Point3D (lat,lon,alt));
		this.speed=speed;
		this.radius=radius;
	}
	
	public Character(Character playerType) {
		super(playerType.id,playerType.location);
		this.lat=playerType.lat;
		this.lon=playerType.lon;
		this.alt=playerType.alt;
		this.speed=playerType.speed;
		this.radius=playerType.radius;
	}
	
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	
	
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	
	  
	public double getAlt() {
		return alt;
	}
	public void setAlt(double alt) {
		this.alt = alt;
	}
	
	
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double alt) {
		this.alt = alt;
	}
	
	
	public double getRadius() {
		return radius;
	}
	public void setRadius(double radius) {
		this.radius=radius;
	}
	public String toString() {
		String s="id: "+this.id+" ,lat: "+this.lat+" ,lon: "+this.lon+" ,alt: "+this.alt+" ,speed: "+this.speed+" ,radius: "+this.radius;
		return s;
	}
	protected double lat=this.location.x();
	protected double lon=this.location.y();
	protected double alt=this.location.z();
	protected double speed;
	protected double radius;

}
