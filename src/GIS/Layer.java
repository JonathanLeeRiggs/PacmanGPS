package GIS;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Layer implements GIS_layer {

	/**
	 * this is a constructor.
	 */
	public Layer() {
		alge=new ArrayList<>();
		mdl= new Meta_data_Layer();
	}

	/**
	 * this is a constructor.
	 */
	public Layer(ArrayList<GIS_element> alge, Meta_data_Layer mdl) {
		this.alge=alge;
		this.mdl=mdl;
	}

	/**
	 * this is a constructor.
	 */
	public Layer(Layer el) {
		this.alge=el.alge;
		this.mdl=el.mdl;
	}
	

	@Override
	public boolean add(GIS_element e) {

		return alge.add(e);
	}

	@Override
	public boolean addAll(Collection<? extends GIS_element> c) {
		
		return alge.addAll(c);
	}

	@Override
	public void clear() {
		
		alge.clear();
		
	}

	@Override
	public boolean contains(Object o) {
		
		return alge.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {

		return alge.containsAll(c);
	}

	@Override
	public boolean isEmpty() {

		return alge.isEmpty();
	}

	@Override
	public Iterator<GIS_element> iterator() {

		return alge.iterator();
	}

	@Override
	public boolean remove(Object o) {
		
		return alge.remove(o);
	}

	@Override
	public boolean removeAll(Collection<?> c) {

		return alge.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		
		return alge.retainAll(c);
	}

	@Override
	public int size() {
		
		return alge.size();
	}

	@Override
	public Object[] toArray() {

		return alge.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		
		return alge.toArray(a);
	}

	/**
	 * this method return the meta_data of the layer.
	 */
	@Override
	public Meta_data get_Meta_data() {
		
		return this.mdl;
	}
	
	//////////private/////////////
	
	private ArrayList<GIS_element> alge;
	private Meta_data_Layer mdl;

}
