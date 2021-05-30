package File_format;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import GIS.Path;
import GIS.PathList;
import Geom.Point3D;

public class Path2kml {
	/**
	 * this method convert a paths list to a KML file with all those paths.
	 * @param pl: the given paths list.
	 * @param outputFileName: the output file name.
	 */
	public static void path2kml(PathList pl, String outputFileName) {
		PrintWriter pw=null;
		try {
			pw = new PrintWriter(new File(outputFileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		StringBuilder content = new StringBuilder();
		
		//**//the body of the kml file//**//
		String kmlstart = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
				"<kml xmlns=\"http://www.opengis.net/kml/2.2\">\n"; 
		content.append(kmlstart);
		content.append("<Document>\n");
		content.append("<name>Paths</name>\n");
		content.append("<description>each path from the paths list.</description>\n");
		content.append("<Style id=\"one\">\n");
		content.append("<LineStyle>\n"+"<color>7f00ffff</color>\n"+"<width>4</width>\n"+"</LineStyle>\n");
		content.append("<PolyStyle>\n"+"<color>7f00ff00</color>\n"+"</PolyStyle>\n");
		content.append("</Style>\n");
		
		//**//makes a path's name and description for every path.//**//
		Iterator<Path> itp = pl.Iterator();
		while(itp.hasNext()) {
			Path tempPath = itp.next();
			content.append("<Placemark>\n"+"<name>Path "+tempPath.getId()+"</name>\n"+
					"<description>Path</description>\n"+"<styleUrl>#one</styleUrl>\n"+"<LineString>\n"+
					"<extrude>1</extrude>\n"+"<tessellate>1</tessellate>\n"+"<altitudeMode>absolute</altitudeMode>\n"+
					"<coordinates>\n");
			//**//adds every point in the path to the kml file//**//
			Iterator<Point3D> itPoint = tempPath.Iterator();
			while(itPoint.hasNext()) {
				Point3D tempPoint = itPoint.next();
				content.append(tempPoint.y()+","+tempPoint.x()+","+(tempPoint.z()+690)+"\n");
			}
			content.append("</coordinates>\n"+"</LineString>\n"+"</Placemark>\n");
		}
		//**//end of the kml file.//**//
		content.append("</Document>\n"+"</kml>\n");

		pw.write(content.toString());
		pw.close();

		System.out.println("Path2kml convert successfully!!!");

	}

}
