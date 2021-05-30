package Algorithm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Iterator;

import GIS.Fruit;
import GIS.FruitsList;
import GIS.Pacman;
import GIS.PacmanList;

public class csvWriter {
	/**
	 * this method write a csv file from pacman list and fruits list.
	 * @param fileName: the name of the saved file.
	 * @param pl: the given pacman list.
	 * @param fl: the given fruits list.
	 * @throws FileNotFoundException
	 */
	public static void csvWriter(String fileName, PacmanList pl, FruitsList fl) throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(new File(fileName));
		StringBuilder sb = new StringBuilder();
		//**//adds the categories to the csv file.//**//
		sb.append("Type");
		sb.append(',');
		sb.append("id");
		sb.append(',');
		sb.append("Lat");
		sb.append(',');
		sb.append("Lon");
		sb.append(',');
		sb.append("Alt");
		sb.append(',');
		sb.append("Speed/Weight");
		sb.append(',');
		sb.append("Radius");
		sb.append(',');
		sb.append(pl.getSize());
		sb.append(',');
		sb.append(fl.getSize());
		sb.append('\n');
		//**//writes every pacman from the pacman list.//**//
		Iterator<Pacman> itp = pl.Iterator();
		while(itp.hasNext()) {
			Pacman tempPac = itp.next();
			sb.append("P");
			sb.append(',');
			sb.append(tempPac.getId());
			sb.append(',');
			sb.append(tempPac.getLat());
			sb.append(',');
			sb.append(tempPac.getLon());
			sb.append(',');
			sb.append(tempPac.getAlt());
			sb.append(',');
			sb.append(tempPac.getSpeed());
			sb.append(',');
			sb.append(tempPac.getRadius());
			sb.append('\n');

		}
		//**//writes every fruit from the fruits list.//**//
		Iterator<Fruit> itf = fl.Iterator();
		while(itf.hasNext()) {
			Fruit tempFruit = itf.next();
			sb.append("F");
			sb.append(',');
			sb.append(tempFruit.getId());
			sb.append(',');
			sb.append(tempFruit.getLat());
			sb.append(',');
			sb.append(tempFruit.getLon());
			sb.append(',');
			sb.append(tempFruit.getAlt());
			sb.append(',');
			sb.append(tempFruit.getWeight());
			sb.append('\n');

		}
		pw.write(sb.toString());
		pw.close();		
		System.out.println("csv written succesfully!");
	}
}
