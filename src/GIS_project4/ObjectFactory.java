package GIS_project4;

import GIS.Fruit;
import GIS.Pacman;
import Geom.Point3D;

public class ObjectFactory {
	private final String PACMAN="P";
	private final String FRUIT="F";
	private final String BOX="B";
	private final String GHOST="G";

	/**
	 * this is a factory method. this method gets an object type as a string("P"/"F"/"B"/"G") and make a game object according to it such that P=pacman,F=fruit,B=box and G=ghost
	 * @param objectType =a string("P"/"F"/"B"/"G") represent the type of object to create
	 * @param objectData =the arguments that needed to be installed in the new objects.
	 * @return GameObject = a new object (pacman/fruit/box/ghost) with the relevant data
	 */
	public GameObject makeObject(String objectType,String [] objectData){
		if(!isCSVDataValid(objectData)) {
			throw new ArithmeticException("Object wasn't created, data isn't valid! "+ objectData[0]+""+objectData[1]);
		}
		double lat=Double.parseDouble(objectData[2]), lon=Double.parseDouble(objectData[3]), alt=Double.parseDouble(objectData[4]);
		double speed=Double.parseDouble(objectData[5]);
		double weight=speed;
		int id =Integer.parseInt(objectData[1]) ,score=0;
		switch (objectType) {
		case PACMAN:
			double radius=Double.parseDouble(objectData[6]);
			return new Pacman (id,lat,lon,alt,speed,radius, score);
		case FRUIT:
			return new Fruit (id,lat,lon,alt,weight);
		case BOX:
			double minLat=lat,minLon=lon;
			double maxLat=Double.parseDouble(objectData[5]), maxLon=Double.parseDouble(objectData[6]);
			Point3D minPoint = new Point3D(minLat,minLon);
			Point3D maxPoint = new Point3D(maxLat,maxLon);
			return new Box(id, minPoint, maxPoint);	
		case GHOST:
			double radius1=Double.parseDouble(objectData[6]);
			return new Ghost(id,lat,lon,alt,speed,radius1);
		default:
			System.out.println("Not A Valid Object!");
			return null;
		}
	}


	/**
	 * this method return true if the data received from the CSV file is vaild, otherwise return false
	 * @param objectData - the current object data
	 * @return true if data is what expected and valid, false otherwise.
	 */
	private boolean isCSVDataValid(String[] objectData) {
		/*
		try {
			int id=Integer.parseInt(objectData[1]);
			if(id<0) {
				System.out.println("id must be a positive integer!"+id);
				return false;
			}
		} catch (NumberFormatException nfe) {
			System.out.println("'id' field is not valid!");
			return false;
		}
		*/
		try {
			double lat=Double.parseDouble(objectData[2]), lon=Double.parseDouble(objectData[3]), alt=Double.parseDouble(objectData[4]);
			double speed=Double.parseDouble(objectData[5]);
			double weight=speed;
			if(lat>32.1058 || lat<32.101) {
				System.out.println("lat is out of boundaries!! " + lat);
				return false;
			}
			if(lon>35.213 || lon<35.202) {
				System.out.println("lon is out of boundaries!! "+lon);
				return false;
			}
			if(speed<0) {
				System.out.println("speed can't be negative! "+speed);
				return false;
			}
			if(weight<1) {
				System.out.println("weight must be a positive integer! "+weight);
				return false;
			}
			if(alt<0) {
				System.out.println("alt must be positive integer! "+ alt);
				return false;
			}
		}catch(NumberFormatException nfe) {
			System.out.println("'lat'/'lon'/'alt'/'speed'/'weight' field is not a valid type");
			return false;
		}
		if(objectData[0].equals (PACMAN)){
			try {
				double radius=Double.parseDouble(objectData[6]);
				if(radius<0) {
					System.out.println("radius must be a positive integer!");
					return false;
				}
			}catch(NumberFormatException nfe) {
				System.out.println("'radius' field is not a valid type");
				return false;
			}
		}
		else if(objectData[0].equals(BOX)){
			try {
				double maxLat=Double.parseDouble(objectData[5]), maxLon=Double.parseDouble(objectData[6]);
				if(maxLat>32.1058 || maxLat<32.101) {
					System.out.println("maxLat is out of boundaries!! " + maxLat +","+objectData[0]);
					return false;
				}
				if(maxLon>35.213 || maxLon<35.202) {
					System.out.println("maxLon is out of boundaries!! "+ maxLon);
					return false;
				}
			}catch(NumberFormatException nfe) {
				System.out.println("maxLat/maxLon field isn't a valid type! must be Double!");
				return false;
			}
		}
		else if(objectData[0].equals(GHOST)){
			try {
				double radius=Double.parseDouble(objectData[6]);
				if(radius<0) {
					System.out.println("radius must be a positive integer!");
					return false;
				}
			}catch(NumberFormatException nfe) {
				System.out.println("'radius' field is not a valid type");
				return false;
			}
		}
		return true;
	}

}
