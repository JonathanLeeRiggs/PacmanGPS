package GIS;

import java.util.Arrays;

import Geom.Point3D;

public class Meta_data_Element implements Meta_data {

	/**
	 * this is a constructor.
	 */
	public Meta_data_Element() {
		this.MAC=null;
		this.AuthMode=null;
		this.FirstSeen=null;
		this.Channel=null;
		this.RSSI=null;
		this.SSID=null;
		this.AccuracyMeters=null;
		this.type=null;
	}

	/**
	 * this is a constructor.
	 */
	public Meta_data_Element(String MAC, String SSID, String AuthMode, String FirstSeen,
			String Channel, String RSSI, String AccuracyMeters, String type) {
		
		this.MAC=MAC;
		this.AuthMode=AuthMode;
		this.FirstSeen=FirstSeen;
		this.Channel=Channel;
		this.RSSI=RSSI;
		this.SSID=SSID;
		this.AccuracyMeters=AccuracyMeters;
		this.type=type;
	}

	/**
	 * this is a copy constructor.
	 */
	public Meta_data_Element(Meta_data_Element md) {
		this.MAC=md.MAC;
		this.AuthMode=md.AuthMode;
		this.FirstSeen=md.FirstSeen;
		this.Channel=md.Channel;
		this.RSSI=md.RSSI;
		this.SSID=md.SSID;
		this.AccuracyMeters=md.AccuracyMeters;
		this.type=md.type;
	}
	
	
	

	public String toString() {
		String res="MAC: "+this.MAC+"SSID: "+this.SSID+"AuthMode: "+this.AuthMode
				+"FirstSeen: "+this.FirstSeen+"Channel: "+this.Channel+"RSSI: "+this.RSSI
						+"AccuracyMeters: "+this.AccuracyMeters+"type: "+this.type;
		return res;		
	}


	/**
	 * this method return the time the element was created in UTC time.
	 */
	@Override
	public long getUTC() {
		String s= this.FirstSeen;
		int spaceIndex=s.indexOf(" ");
		s=s.substring(spaceIndex+1);
		s=s.replace(":","");
		long utc = Long.parseLong(s);
		if(utc<20000)   //changes GMT+2 to UTC
			utc+=220000;
		else 
			utc=utc-20000;
		return utc;
	}

	@Override
	public Point3D get_Orientation() {


		return null;
	}


	/////////private///////////
	private String MAC;
	private String SSID;
	private String AuthMode;
	private String FirstSeen;
	private String Channel;
	private String RSSI;
	private String AccuracyMeters;
	private String type;
	
	
	public static void main(String[] args) {
		Meta_data_Element md=new Meta_data_Element("1","2","3","29/06/93 01:23:56","5","6","7","8");
		System.out.println(md.getUTC());
		
	}

}
