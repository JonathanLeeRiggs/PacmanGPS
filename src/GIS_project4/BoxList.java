package GIS_project4;

import java.util.ArrayList;
import java.util.Iterator;

import Geom.Point3D;

public class BoxList {
	/**
	 * this method is a constructor.
	 */
	public BoxList() {
		alb=new ArrayList<Box>();
	}
	/**
	 * this method is a copy constructor.
	 * @param gl: the given ghost list.
	 */
	public BoxList(BoxList bl) {
		this.alb=bl.alb;
	}
	
	/**
	 * this method adds a box to the boxes list.
	 * @param b: the given box.
	 */
	public void add(Box b) {
		this.alb.add(b);
	}
	public void add(GameObject b) {
		this.alb.add((Box) b);
	}
	
	/**
	 * this method returns the iterator of the boxes list.
	 */
	public Iterator<Box> Iterator() {
		return alb.iterator();
	}
	
	/**
	 * this method prints all the boxes in the list.
	 */
	public void Print() {
		Iterator<Box> it = alb.iterator();
		while(it.hasNext()) {
			Box temp= it.next();
			System.out.println(temp);
		}
	}
	
	/**
	 * this method clears the boxes list.
	 */
	public void clear() {
		alb.clear();
	}
	
	/**
	 * this method returns an array list of all the corners of all the boxes (not include the corners who's not visible).
	 */
	public ArrayList<Point3D> boxesCorners() {
		ArrayList<Point3D> ans = new ArrayList<>(); 
		Iterator<Box> itb = this.Iterator();
		while(itb.hasNext()) {
			Box temp = itb.next();
			Point3D minTemp =new Point3D(temp.getMin());      //down left corner point.
			minTemp.add(-0.00001, -0.00001);                  //adds to the point a bit.
			if(!minTemp.isInBox(this)) {                      //checks if the point is in a box.
				ans.add(minTemp);                             //adds the point to the array list.
			}
			
			Point3D maxTemp = new Point3D(temp.getMax());      //top right corner point.
			maxTemp.add(0.00001, 0.00001);                     //adds to the point a bit.
			if(!maxTemp.isInBox(this)) {                       //checks if the point is in a box.
				ans.add(maxTemp);                             //adds the point to the array list.
			}
			
			Point3D topLeftTemp = new Point3D(temp.getMax().x(), temp.getMin().y());     //top left corner point.
			topLeftTemp.add(0.00001, -0.00001);                                          //adds to the point a bit.
			if(!topLeftTemp.isInBox(this)) {                                             //checks if the point is in a box.
				ans.add(topLeftTemp);                                                    //adds the point to the array list.
			}
			
			Point3D downRightTemp = new Point3D(temp.getMin().x(), temp.getMax().y());   //down right corner point.
			downRightTemp.add(-0.00001, 0.00001);                                        //adds to the point a bit.
			if(!downRightTemp.isInBox(this)) {                                           //checks if the point is in a box.
				ans.add(downRightTemp);                                                  //adds the point to the array list.
			}
		}		
		return ans;
	}
	
	
	//////private/////
	private ArrayList<Box> alb = new ArrayList<Box>();


	
}
