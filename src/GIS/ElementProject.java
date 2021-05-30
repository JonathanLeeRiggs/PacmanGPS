package GIS;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class ElementProject implements GIS_project {
	
	/**
	 * this is a constructor.
	 */
	public ElementProject() {
		algl=new ArrayList<>();
		mdp= new Meta_data_Project();
	}
	

	@Override
	public boolean add(GIS_layer e) {

		return algl.add(e);
	}

	@Override
	public boolean addAll(Collection<? extends GIS_layer> c) {
		
		return algl.addAll(c);
	}

	@Override
	public void clear() {
		
		algl.clear();
		
	}

	@Override
	public boolean contains(Object o) {
		
		return algl.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {

		return algl.containsAll(c);
	}

	@Override
	public boolean isEmpty() {

		return algl.isEmpty();
	}

	@Override
	public Iterator<GIS_layer> iterator() {

		return algl.iterator();
	}

	@Override
	public boolean remove(Object o) {
		
		return algl.remove(o);
	}

	@Override
	public boolean removeAll(Collection<?> c) {

		return algl.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		
		return algl.retainAll(c);
	}

	@Override
	public int size() {
		
		return algl.size();
	}

	@Override
	public Object[] toArray() {

		return algl.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		
		return algl.toArray(a);
	}

	/**
	 * this method return the meta_data of the project.
	 */
	@Override
	public Meta_data get_Meta_data() {
		
		return this.mdp;
	}
	
	//////////private/////////////
	
	private ArrayList<GIS_layer> algl;
	private Meta_data_Project mdp;
}
