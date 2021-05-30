package File_format;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import GIS.Fruit;
import GIS.FruitsList;
import GIS.Pacman;
import GIS.PacmanList;

public class PacmanCsvReader {

	/**
	 * this method read csv file of pacman and fruits, insert it's data to an arrayList.
	 * @param fileName: the name of the csv file. 
	 * @return arrayList with the file's data.
	 */
	public static void csv(String fileName, PacmanList pl, FruitsList fl) {
		

		File file=new File(fileName);

		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line=br.readLine();

			while(line!=null) {
				String [] splitLine=line.split(",");
				if(splitLine[0].equals("P")) {
					Pacman p = new Pacman(Integer.parseInt(splitLine[1]), Double.parseDouble(splitLine[2]), Double.parseDouble(splitLine[3]),
							Double.parseDouble(splitLine[4]), Double.parseDouble(splitLine[5]), Double.parseDouble(splitLine[6]),0);
					pl.add(p); 	//adds this Pacman to the Pacman list.
				}
				if(splitLine[0].equals("F")) {
					Fruit f = new Fruit(Integer.parseInt(splitLine[1]), Double.parseDouble(splitLine[2]), Double.parseDouble(splitLine[3]),
							Double.parseDouble(splitLine[4]), Double.parseDouble(splitLine[5]));
					fl.add(f); 	//adds this Fruit to the Fruit list.
				}
				line=br.readLine();
			}
			br.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
