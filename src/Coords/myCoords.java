package Coords;

import Geom.Point3D;
/**
 * this class is implementation of the interface coords_converter.
 *
 */
public class myCoords implements coords_converter {

	private double earthRadius=6371000;


	/**
	 * this function computes a new point which is the gps point transformed by a 3D vector (in meters).
	 */
	@Override
	public Point3D add(Point3D gps, Point3D local_vector_in_meter) {
		double longtitudeNormal=Math.cos(Math.toRadians(gps.x()));
		double z=gps.z()+local_vector_in_meter.z();
		double deltaX=Math.asin((local_vector_in_meter.x()/earthRadius)*180/Math.PI);  //computing the delta x value of the new point.
		double x=gps.x()+deltaX;

		double deltaY=Math.asin((local_vector_in_meter.y()/(earthRadius*longtitudeNormal))*180/Math.PI);  //computing the delta y value of the new point.
		double y=gps.y()+deltaY;

		Point3D newPoint=new Point3D(x,y,z);

		return newPoint;
	}

	/**
	 * this function computes the 3D distance (in meters) between the two gps points.
	 */
	@Override
	public double distance3d(Point3D gps0, Point3D gps1) {

		double dist;
		Point3D vector3d = vector3D(gps0, gps1);    //finding the vector between the two points.

		dist=Math.sqrt((vector3d.x()*vector3d.x())+(vector3d.y()*vector3d.y())+(vector3d.z()*vector3d.z()));

		return dist;
	}

	/**
	 * this function computes the 3D vector (in meters) between two gps points.
	 */
	@Override
	public Point3D vector3D(Point3D gps0, Point3D gps1) {
		double longtitudeNormal=Math.cos(Math.toRadians(gps0.x()));
		double deltaX=gps1.x()-gps0.x();
		double deltaY=gps1.y()-gps0.y();
		double vectorZ=gps1.z()-gps0.z();

		double xRadian=(deltaX*Math.PI)/180;
		double yRadian=(deltaY*Math.PI)/180;

		double vectorX=Math.sin(xRadian)*earthRadius;
		double vectorY=Math.sin(yRadian)*earthRadius*longtitudeNormal;

		Point3D vector3D=new Point3D(vectorX,vectorY,vectorZ);
		return vector3D;
	}

	/**
	 * this function computes the polar representation of the 3D vector be gps0-->gps1 
	 * this method return an azimuth, elevation (pitch), and distance.
	 */
	@Override
	public double[] azimuth_elevation_dist(Point3D gps0, Point3D gps1) {

		//res[0]=azimuth, res[1]=elevation, res[2]=distance.
		double[] res=new double[3];
		myCoords m=new myCoords();
		
		Point3D vector=new Point3D(vector3D(gps0, gps1));         //creating a vector between gps0 and gps1.

		res[0]=m.azimuth(vector);     //calculate azimuth.		
		res[2]=m.distance(vector);  //calculate the distance.	
		res[1]=m.elevation(vector, res[2]);   //calculate elevation.

		return res;
	}




	/**
	 * this function return true iff this point is a valid lat, lon , alt coordinate: [-180,+180],[-90,+90],[-450, +inf]
	 */
	@Override
	public boolean isValid_GPS_Point(Point3D p) {
		if(p.x()<-180 || p.x()>180) {
			return false;
		}		
		if(p.y()<-90 || p.y()>90) {
			return false;
		}		
		if(p.z()<-450) {
			return false;
		}	

		return true;
	}


	//////////////private methods////////////////////

	
	private double distance(Point3D vector) {
		double radius;
		radius=Math.sqrt((vector.x()*vector.x())+(vector.y()*vector.y()));
	
		return radius;
	}
	
	
	/**
	 * this function calculate the elevation of points.
	 * @param gps0: starting point.
	 * @param gps1: ending point.
	 */
	private double elevation(Point3D vector, double vectorRadius) {
		double theta;
		double elevation;
		double radius=vectorRadius;
		
		theta=Math.acos(Math.toRadians(vector.z()) / Math.toRadians(radius));
		elevation=90-Math.toDegrees(theta);
		
		return elevation;
	}

	/**
	 * this function calculate the azimuth between two points.
	 * @param gps0: starting point.
	 * @param gps1: ending point.
	 */
	private double azimuth(Point3D vector) {
		double azi;
		double phi;
		phi=Math.atan2(vector.y(), vector.x());     //arctan(y/x).
		
		azi=Math.toDegrees(phi);


		if(azi<0) {
			azi+=360;
		}

		return azi;
	}

}
