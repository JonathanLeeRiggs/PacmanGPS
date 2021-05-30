package GIS;

import java.util.ArrayList;

import Geom.Point3D;

public class Meta_data_Layer implements Meta_data {

	/**
	 * this is a constructor.
	 */
	public Meta_data_Layer() {
		layerName=null;
		layerSize=0;
		layerColor="black";
	}

	/**
	 * this is a constructor.
	 */
	public Meta_data_Layer(ArrayList<GIS_element> alge, String layerName, String layerColor) {
		this.layerColor=layerColor;
		this.layerName=layerName;
		this.layerSize=alge.size();
	}

	/**
	 * this is a copy constructor.
	 */
	public Meta_data_Layer(Meta_data_Layer mdl) {
		this.layerColor=mdl.layerColor;
		this.layerName=mdl.layerName;
		this.layerSize=mdl.layerSize;
	}
	
	@Override
	public long getUTC() {
		
		return 0;
	}

	@Override
	public Point3D get_Orientation() {
		
		return null;
	}
	
	////////private////////////
	private int layerSize;
	private String layerName;
	private String layerColor;


}
