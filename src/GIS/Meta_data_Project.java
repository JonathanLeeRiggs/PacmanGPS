package GIS;

import java.util.ArrayList;

import Geom.Point3D;

public class Meta_data_Project implements Meta_data{

	/**
	 * this is a constructor.
	 */
	public Meta_data_Project() {
		this.projectColor="red";
		this.projectName=null;
		this.size=0;
		
	}

	/**
	 * this is a constructor.
	 */
	public Meta_data_Project(ArrayList<GIS_layer> alp, String projectColor, String projectName){
		this.projectColor=projectColor;
		this.projectName=projectName;
		this.size=alp.size();
	}

	/**
	 * this is a copy constructor.
	 */
	public Meta_data_Project(Meta_data_Project mdp) {
		this.projectColor=mdp.projectColor;
		this.projectName=mdp.projectName;
		this.size=mdp.size;
	}
	
	
	@Override
	public long getUTC() {

		return 0;
	}

	@Override
	public Point3D get_Orientation() {

		return null;
	}

	///////private//////////
	private String projectName;
	private String projectColor;
	private int size;
}
