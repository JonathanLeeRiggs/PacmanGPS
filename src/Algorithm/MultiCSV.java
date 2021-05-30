package Algorithm;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MultiCSV {

	/**
	 * this method reads multiple CSV files recursively into one arrayList.
	 * @param array: the given arrayList.
	 * @param dir: the directory the method starts from.
	 * @throws IOException
	 */
	public static void multiCsv(ArrayList<String[]> array,File dir) throws IOException {
		File[] files = dir.listFiles();
		for (File file : files) {
			if(file.isDirectory()) {            //if file is a directory.
				String s=file.getPath();
				File currentFile= new File(s);
				multiCsv(array, currentFile);         //the recursive call.
			}
			else if (file.getName().endsWith(".csv")) {         //if it's a csv file.
				MultiCSVReader.mCsvReader(array, file.getPath());      //add the csv file's data to the array.
				
			} 
		}
	}
}
