package GIS;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Project implements GIS_project {

	/**
	 * this is a constructor.
	 */
	public Project() {
		this.alp= new ArrayList<>();
		this.mdp= new Meta_data_Project();
	}

	/**
	 * this is a constructor.
	 */
	public Project(ArrayList<GIS_layer> alp, Meta_data_Project mdp) {
		this.alp=alp;
		this.mdp=mdp;
	}

	/**
	 * this is a copy constructor.
	 */
	public Project(Project p) {
		this.alp=p.alp;
		this.mdp=p.mdp;
	}
	
	
	
	@Override
	public boolean add(GIS_layer e) {
		
		return alp.add(e);
	}

	@Override
	public boolean addAll(Collection<? extends GIS_layer> c) {
		
		return alp.addAll(c);
	}

	@Override
	public void clear() {
		alp.clear();
		
	}

	@Override
	public boolean contains(Object o) {
		
		return alp.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		
		return alp.containsAll(c);
	}

	@Override
	public boolean isEmpty() {

		return alp.isEmpty();
	}

	@Override
	public Iterator<GIS_layer> iterator() {
		
		return alp.iterator();
	}

	@Override
	public boolean remove(Object o) {
		
		return alp.remove(o);
	}

	@Override
	public boolean removeAll(Collection<?> c) {

		return alp.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {

		return alp.retainAll(c);
	}

	@Override
	public int size() {
		
		return alp.size();
	}

	@Override
	public Object[] toArray() {
		
		return alp.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {

		return alp.toArray(a);
	}

	/**
	 * this method return the meta_data of the layer.
	 */
	@Override
	public Meta_data get_Meta_data() {

		return this.mdp;
	}
	
	
	/////////////private/////////
	ArrayList<GIS_layer> alp;
	Meta_data_Project mdp;


	
}
