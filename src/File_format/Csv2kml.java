package File_format;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Csv2kml {
	
	/**
	 * this method write a kml file from a csv file.
	 * @param a: the given arrayList with the csv file's data.
	 * @param output: the kml file's name.
	 */
	  public static void writeFileKML(ArrayList<String[]> a, String output) {
		    ArrayList<String> content = new ArrayList<String>();
		    String kmlstart = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
		            "<kml xmlns=\"http://www.opengis.net/kml/2.2\">\n";        //the start of the kml file string.
		    content.add(kmlstart);
		    content.add("<Document>");

		    try{
		        FileWriter fw = new FileWriter(output);
		        BufferedWriter bw = new BufferedWriter(fw);
		        for (int i = 0; i < a.size(); i++) {
		            String[] s = a.get(i);
		            String kmlelement ="<Placemark>\n" +
		                    "<name>"+s[1]+"</name>\n" +
		                    "<description>"+"<b>"+s[2]+"</b>"+"</description>\n" +
		                    "<Point>\n" + "\n"+
		                    "<coordinates>"+s[7]+","+s[6]+"</coordinates>" +
		                    "</Point>\n" +
		                    "</Placemark>\n";
		            content.add(kmlelement);           //adding the kml element to the kml file.
		        }
		        content.add("</Document>");
		        content.add("</kml>");
		        String csv = content.toString().replace("[", "").replace("]", "");
		        bw.write(csv);
		        bw.close();
		        
		        System.out.println("CSV to KML convert successfully!!!");
		    } 
		    catch (IOException e) {
		        e.printStackTrace();
		    }
		}
}
