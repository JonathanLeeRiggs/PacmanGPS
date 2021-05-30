package GIS;

import java.util.ArrayList;
import java.util.Iterator;


public class PathList {
	/**
	 * this method is a constructor.
	 */
	public PathList() {
		alPath = new ArrayList<>();
	}
	/**
	 * this method is a constructor. creating path lists for each pacman from the pacman list.
	 * @param pl: the given pacman list.
	 */
	public PathList (PacmanList pl) {
		Iterator<Pacman> itp = pl.Iterator();
		while(itp.hasNext()) {
			Pacman tempPac = itp.next();
			Path newPath = new Path(tempPac);
			alPath.add(newPath);
		}
		
	}
	
	/**
	 * this method adds path to the paths list.
	 * @param path
	 */
	public void add(Path path) {
		alPath.add(path);
	}
	
	/**
	 * this method returns the path which is matches the given pacman's id.
	 * @param pacId: the given pacman's id.
	 * @return
	 */
	public Path searchPath(int pacId) {
		Iterator<Path> itp = this.Iterator();
		while(itp.hasNext()) {
			Path temp = itp.next();
			if(temp.getId()==pacId) {
				return temp;
			}
		}
		return null;
	}
	
	/**
	 * this method clear the list.
	 */
	public void clear() {
		alPath.clear();
	}
	/**
	 * this method returns a path from given index.
	 * @param index: the given index.
	 * @return
	 */
	public Path getPath(int index) {
		return alPath.get(index);
	}
	
	/**
	 * this method returns an iterator.
	 */
	public Iterator<Path> Iterator() {
		return alPath.iterator();
	}
	
	/**
	 * this method returns the number of paths in the list.
	 * @param pathlist: the given path list.
	 */
	public int numOfPaths(PathList pathlist) {
		return alPath.size();
	}
	
	
	///private///
	private ArrayList<Path> alPath = new ArrayList<>();
	

}
