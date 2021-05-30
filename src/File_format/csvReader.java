package File_format;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * this class is used to read from a csv file.
 */
public class csvReader {

	/**
	 * this method read csv file and insert it's data to an arrayList.
	 * @param fileName: the name of the csv file. 
	 * @return arrayList with the file's data.
	 */
	public static ArrayList<String[]> csv(String fileName) {
		
		ArrayList<String[]> array = new ArrayList<>();

		File file=new File(fileName);
		boolean isFirstLine=true;
		boolean isSecondLine=true;
		
		try {
			
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line=br.readLine();

			while(line!=null) {
				String [] splitLine=line.split(",");
				array.add(splitLine);            //adding the string to the array list.
				if(isFirstLine) {                //if this is the first line of the csv file.
					array.remove(splitLine);
					isFirstLine=false;
				}
				else if(isSecondLine && isFirstLine==false) {      //if this is the second line of the csv file.
					array.remove(splitLine);
					isSecondLine=false;
				}
				line=br.readLine();
			}
			br.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return array;	
	}
}
