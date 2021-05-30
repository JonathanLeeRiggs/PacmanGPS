package Algorithm;

import java.util.Iterator;

import GIS.Fruit;
import GIS.FruitsList;
import GIS.Pacman;
import GIS.Path;
import GIS.PathList;
import Geom.Point3D;

public class GameAlgo {

	/**
	 * this method finds the closest pacman's path to a given fruit, from a path list.
	 * by calculates the time from the last point in the path, to the fruit.
	 * @param fruit: the given fruit.
	 * @param pl: the given path list.
	 * @return ans: double[2]. [0]=pacId, [1]=time.
	 */
	public static double[] closesetPacToFruit(Fruit fruit, PathList pl) {
		double [] ans = new double [2];          //[0]=pacId, [1]=time.
		double minTime= Double.MAX_VALUE;
		int pacId=0;
		Iterator<Path> itpl = pl.Iterator();
		while(itpl.hasNext()) {
			Path tempPath= itpl.next();
			Point3D tempPoint=tempPath.lastPoint();	         //taking the last point of the current path.	
			double tempTime=fruit.distToPoint(tempPoint);       //calculates the distance between the fruit and the path's last point.
			double finalTime=(tempTime/tempPath.getPac().getSpeed())+tempPath.getOverallTime();       //calculate the actual time by considering the pacman's speed and the path's overall time.
			if(finalTime<minTime) {
				minTime=finalTime;
				pacId=tempPath.getId();
			}
		}	
		ans[0]=pacId;
		ans[1]=minTime;

		return ans;
	}
	/**
	 * this method finds the closest fruit to a pacman, from a given pacman's path and fruits list.
	 * @param path: the given pacman's path.
	 * @param fl: the given fruits list.
	 */
	public static Fruit closestFruitToPac(Path path, FruitsList fl) {
		Fruit ans= new Fruit();
		double minTime=Double.MAX_VALUE;
		Point3D lastPoint = path.lastPoint();         //taking the last point of the path.
		Pacman lastPos = new Pacman (path.getPac());  //copy the path's pacman.
		lastPos.setLat(lastPoint.x());                //set the copied pacman's latitude to be as the path's last point latitude.
		lastPos.setLon(lastPoint.y());                //set the copied pacman's longitude to be as the path's last point longitude.              
		lastPos.setAlt(lastPoint.z());                //set the copied pacman's altitude to be as the path's last point altitude.
		Iterator<Fruit> itf = fl.Iterator();
		while(itf.hasNext()) {
			Fruit tempFru = itf.next();
			double tempTime=lastPos.timePacToFruit(tempFru);          //calculates the time that takes to reach the fruit from the path's last point, by using the copied pacman.
			double finalTime=tempTime+path.getOverallTime();          //adds the path's overall time to the tempTime.
			if(finalTime<minTime) {
				minTime=finalTime;
				ans=tempFru;
			}

		}
		return ans;
	}
	/**
	 * this method arrange a path list, by adding the right fruit to his right path.
	 * the method finds the closest path's pacman from a path list to a given fruit, then finds the closest fruit to that pacman's path from a fruits list.
	 * if the given fruit and the closest fruit to the pacman are equals, it adds the fruit to the pacman's path.
	 * @param pl: the given path list.
	 * @param fl: the given fruits list.
	 */
	public static void pathArrange(PathList pl, FruitsList fl) {
		double [] closePacFru= new double [2];         //saves the closest pacman's id, and the time to the fruit.
		while(fl.getSize()>0) {                    	   //while there's still fruits in the fruits list.
			Iterator<Fruit> itf = fl.Iterator();
			while(itf.hasNext()) {
				Fruit tempFru = itf.next();
				closePacFru=closesetPacToFruit(tempFru, pl);              //finds the closest pacman's path to tempFru.
				Path currentPath = pl.searchPath((int)closePacFru[0]);    //currentPath = the closest path to the fruit.
				Fruit closeFru = closestFruitToPac(currentPath, fl);      //finds the closest fruit to the current path.
				if(tempFru==closeFru) {
					currentPath.fruitAdd(tempFru);				//adds the fruit to the current path.
					currentPath.getPac().setScore(currentPath.getPac().getScore()+1);       //adds one to the score of the pacman.
					fl.remove(tempFru); 				//remove the fruit from the fruits list.
					break;
				}
			}	
		}

	}
	/**
	 * this method finds the given pacman's position in a given time. the method finds the path of the given pacman from the
	 * path list, and then calculates the exact position of the pacman in that time.
	 * @param pac: the given pacman.
	 * @param pl: the given path list.
	 * @param time: the given time.
	 * @return the point of the pacman's position in the given time.
	 */
	public static Point3D pacPosSpecificTime(Pacman pac,PathList pl, double time) {
		double diffLat, diffLon, diffAlt, diffTime, totalTime = 0, prevTime;
		Point3D ans = pac.gameObjectToPoint3D();
		int pointIndex = 0;
		if(time<=0) {                   //if the given time is <=0 , returns the pacman first position.
			return pac.gameObjectToPoint3D();
		}
		int pacId=pac.getId();               //get the id of the given pacman.
		Path currentPath = pl.searchPath(pacId);        //finds the path of that pacman.
		if(time>currentPath.getOverallTime()) {         //if the given time is greater than the overall time of the path, returns the last point of the path.
			return currentPath.lastPoint();
		}
		Point3D prevPoint = currentPath.firstPoint();       //initialize prevPoint with the first point of the path (pacman's first position).
		Iterator<Point3D> itp = currentPath.Iterator();
		while(itp.hasNext()) {
			Point3D currentPoint = itp.next();
			pointIndex = currentPath.getIndexOfPoint(currentPoint);      //initialize pointIndex with the current point's index in the path.
			totalTime+=currentPath.getTimeOfIndex(pointIndex);           //total time is the overall time of the path until the current point.
			if(time==totalTime) {               //if the given time is equals to the total time, returns the current point.
				return currentPoint;
			}
			else if(time<totalTime) {
				diffLat=currentPoint.x()-prevPoint.x();            //the latitude value difference between the previous point and the current point.
				diffLon=currentPoint.y()-prevPoint.y();            //the longitude value difference between the previous point and the current point.
				diffAlt=currentPoint.z()-prevPoint.z();            //the altitude value difference between the previous point and the current point.
				prevTime=totalTime-currentPath.getTimeOfIndex(pointIndex);          //prevTime=total time until the previous point.
				diffTime=time-prevTime;                                             
				diffLat=(diffLat/ currentPath.getTimeOfIndex(pointIndex))*diffTime;     //divides the diffLat by the number of sections there are in between the previous point and the current point(by the time between them), then multiplies it with the diffTime.
				diffLon=(diffLon/ currentPath.getTimeOfIndex(pointIndex))*diffTime;     //divides the diffLon by the number of sections there are in between the previous point and the current point(by the time between them), then multiplies it with the diffTime.
				diffAlt=(diffAlt/ currentPath.getTimeOfIndex(pointIndex))*diffTime;     //divides the diffAlt by the number of sections there are in between the previous point and the current point(by the time between them), then multiplies it with the diffTime.
				ans = new Point3D(prevPoint.x()+diffLat, prevPoint.y()+diffLon, prevPoint.z()+diffAlt);    //adds the diffLat, diffLon and diffAlt to the previous point values.
				break;
			}
			prevPoint=currentPoint;
		}
		return ans;
		
		
	}
	

}

