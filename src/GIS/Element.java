package GIS;

import Coords.myCoords;
import Geom.Geom_element;
import Geom.Point3D;

public class Element implements GIS_element {

	/**
	 * this function is constructor.
	 */
	public Element() {
		this.point=new Point3D(0, 0, 0);
		this.md=new Meta_data_Element();
	}
	/**
	 * this function is constructor.
	 */

	public Element(Point3D p,Meta_data_Element md) {
		this.point=p;
		this.md=md;
	}
	/**
	 * this function is a copy constructor.
	 */

	public Element(Element e) {
		this.point=e.point;
		this.md=e.md;
	}

	

	/**
	 * this function get the x,y,z values of an element.
	 */
	@Override
	public Geom_element getGeom() {
		Point3D res=new Point3D(this.point.x(),this.point.y(),this.point.x());
		return res;
	}

	
	/**
	 * this function represents all the data of the element, excluded the x,y,z values.
	 */
	@Override
	public Meta_data getData() {
		Meta_data_Element md=new Meta_data_Element(this.md);
		return md;
		
	}

	/**
	 * this function gets a vector and translate the x,y,z values to a new point.
	 */
	@Override
	public void translate(Point3D vec) {
		myCoords m = new myCoords();		
		this.point=m.add(this.point, vec);
		
	}
	
	
	////////////private//////////
	
	private Meta_data_Element md;
	private Point3D point;

}
