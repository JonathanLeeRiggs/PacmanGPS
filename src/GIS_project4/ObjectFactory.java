package GIS_project4;

import GIS.Fruit;
import GIS.Pacman;
import Geom.Point3D;

public class ObjectFactory {
	private final String PACMAN="P";
	private final String FRUIT="F";
	private final String BOX="B";
	private final String GHOST="G";
	
	
	public GameObject makeObject(String objectType,String [] ObjectData){
		if(objectType.equals(PACMAN)) 
			//creates a new pacman
			return new Pacman (Integer.parseInt(ObjectData[1]), Double.parseDouble(ObjectData[2]), Double.parseDouble(ObjectData[3]),
					Double.parseDouble(ObjectData[4]), Double.parseDouble(ObjectData[5]), Double.parseDouble(ObjectData[6]), 0);
		else if(objectType.equals(FRUIT))
			//creates a new fruit
			return new Fruit (Integer.parseInt(ObjectData[1]), Double.parseDouble(ObjectData[2]), Double.parseDouble(ObjectData[3]),
					Double.parseDouble(ObjectData[4]), Double.parseDouble(ObjectData[5]));
		else if(objectType.equals(BOX)) {
			Point3D minPoint = new Point3D(Double.parseDouble(ObjectData[2]), Double.parseDouble(ObjectData[3]));
			Point3D maxPoint = new Point3D(Double.parseDouble(ObjectData[5]), Double.parseDouble(ObjectData[6]));
			//creates a new box
			 return new Box(Integer.parseInt(ObjectData[1]), minPoint, maxPoint);	
		}
		else if(objectType.equals(GHOST)) 
			//creates a new ghost
			return new Ghost(Integer.parseInt(ObjectData[1]), Double.parseDouble(ObjectData[2]), Double.parseDouble(ObjectData[3]),
					Double.parseDouble(ObjectData[4]), Double.parseDouble(ObjectData[5]), Double.parseDouble(ObjectData[6]));
		else { 
			System.out.println("Not A Valid Object!");
			return null;
		}
	}

}
