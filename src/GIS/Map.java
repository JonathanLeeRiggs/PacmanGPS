package GIS;

import Geom.Point3D;

public class Map {
	
	/**
	 * this method converting x,y pixels to lat, lon coordinates.
	 * @param xPixel
	 * @param yPixel
	 * @param width
	 * @param height
	 */
	public static Point3D pixelsToCoords(int xPixel, int yPixel, int width, int height) {
		double ratioLat=ratioLatCoords(height);         //calculating the ratio between the height and the original coordinates difference.
		double ratioLon=ratioLonCoords(width);          //calculating the ratio between the width and the original coordinates difference.
		double newLat=upleftlat-(yPixel*ratioLat);      //calculating the lat coordinate.
		double newLon=downleftlon+(xPixel*ratioLon);    //calculating the lat coordinate.
		Point3D p = new Point3D(newLat, newLon);
		System.out.println("GPS coordinates: ("+newLat+","+newLon+")");
		return p;
		
	}
	
	/**
	 * this method converting lat, lon coordinates to x,y pixels.
	 * @param p: the point with the lat, lon values.
	 * @param width
	 * @param height
	 */
	public static int[] coordsToPixels(Point3D p, int width, int height) {
		int [] ans= new int[2];
		double lat=p.x();
		double lon=p.y();
		double xPixel=(lon-downleftlon)/(ratioLonCoords(width));
		double yPixel=(lat-upleftlat)/(-1*(ratioLatCoords(height)));
		ans[0]=(int)((xPixel+0.1));
		ans[1]=(int)((yPixel+0.1));
		return ans;
	}

	
	////private////

	  /*points*/
	
	 //top left latitude
	 private final static double upleftlat=32.105711;
	 //down left point
	 private final static double downleftlat=32.101893;
	 private final static double downleftlon=35.202438;
	 //down right longitude.
	 private final static double downrightlon=35.212454;
	 //latitude difference between the top left latitude and the down left latitude.
	 private final static double coordsDiffLat=upleftlat-downleftlat;
	//longitude difference between the down right longitude and the down left longitude.
	 private final static double coordsDiffLon=downrightlon-downleftlon+0.000080;
	 
	 
	 
		
	 
	 /**
	  * this method calculate latitude difference between two point/pixels height.
	  * @param pixHeight
	  */
	 private static double ratioLatCoords(int pixHeight) {
		 return coordsDiffLat/pixHeight;
	 }
	 /**
	  * this method calculate longitude difference between two point/pixels width.
	  * @param pixHeight
	  */
	 private static double ratioLonCoords(int pixWidth) {
		 return coordsDiffLon/pixWidth;
	 }
	
	 
}
