package GIS_project4;

public class Ghost extends Character{
	/**
	 * this method is a constructor.
	 */
	public Ghost() {
		super();
	}	
	/**
	 * this method is a constructor.
	 */
	public Ghost(int id, double lat, double lon, double alt, double speed, double radius) {
		super(id,lat,lon,alt,speed,radius);
	}	
	/**
	 * this method is a copy constructor.
	 */
	public Ghost(Ghost ghost) {
		super(ghost.id,ghost.lat,ghost.lon,ghost.alt,ghost.speed,ghost.radius);
	}
}
