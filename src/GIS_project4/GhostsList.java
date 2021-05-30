package GIS_project4;

import java.util.ArrayList;
import java.util.Iterator;

public class GhostsList {
	
	/**
	 * this method is a constructor.
	 */
	public GhostsList() {
		alg=new ArrayList<Ghost>();
	}
	
	/**
	 * this method is a copy constructor.
	 * @param gl: the given ghost list.
	 */
	public GhostsList(GhostsList gl) {
		this.alg=gl.alg;
	}
	
	/**
	 * this method adds a ghost to the ghosts list.
	 * @param g: the given ghost.
	 */
	public void add(Ghost g) {
		this.alg.add(g);
	}
	public void add(GameObject g) {
		this.alg.add((Ghost) g);
	}
	
	/**
	 * this method returns the iterator of the ghosts list.
	 * @return
	 */
	public Iterator<Ghost> Iterator() {
		return alg.iterator();
	}
	
	/**
	 * this method prints the ghosts list.
	 */
	public void print() {
		Iterator<Ghost> it = alg.iterator();
		while(it.hasNext()) {
			Ghost temp= it.next();
			System.out.println(temp);
		}
	}
	
	/**
	 * this method clears the ghosts list.
	 */
	public void clear() {
		alg.clear();
	}
	
	/**this function updates each ghost location according to the game board. 
	 * 
	 * @param split :the relevant line in the board which represents the ghost.
	 */
	public void updateGhost(String [] split) {
		Iterator<Ghost> ghostIterator = this.Iterator();
		while(ghostIterator.hasNext()) {
			Ghost tempGhost = ghostIterator.next();
			if(tempGhost.getId()==Integer.parseInt(split[1])) {  
				double lat=Double.parseDouble(split[2]);
				double lon=Double.parseDouble(split[3]);
				tempGhost.updateObjectLocation(lat,lon);
			}
		}
	}
	
	//////private/////
	private ArrayList<Ghost> alg = new ArrayList<Ghost>();
}
