package Algorithm;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class MultiCSVReader {

	/**
	 * this method read CSV files, and insert their data to an arrayList.
	 * @param array: the given arrayList.
	 * @param fileName: the name of the CSV file.
	 */
	public static void mCsvReader(ArrayList<String[]> array,String fileName) {
		File file=new File(fileName);
		boolean isFirstLine=true;
		boolean isSecondLine=true;

		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line=br.readLine();

			while(line!=null) {
				String [] splitLine=line.split(",");
				array.add(splitLine);            //adding the string to the arrayList.
				if(isFirstLine) {                //if this is the first line of the csv file.
					array.remove(splitLine);
					isFirstLine=false;
				}
				else if(isSecondLine && isFirstLine==false) {            //if this is the second line of the csv file.
					array.remove(splitLine);
					isSecondLine=false;
				}
				line=br.readLine();
			}
			br.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
