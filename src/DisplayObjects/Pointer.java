package DisplayObjects;

/**
* This class is used to store mouse position and status
*/
public class Pointer {

	/**
	* Mouse location
	*/
	public static int mx,my;
	/**
	* Whether or not the left button is down
	*/
	public static boolean leftDown=false;
	/**
	* Whether or not the right button is down
	*/
	public static boolean rightDown=false;
	/**
	* Whether or not the center button is down
	*/
	public static boolean centerDown=false;
	/**
	* If the mouse wheel is moving up, this is 1, down is -1, and not moving is 0
	*/
	public static int mouseWheelDirection=0;
	
	/**
	* Constructor sets original mouse position
	* @param x X Location
	* @param y Y Location
	*/
	public Pointer(int x,int y) {
		mx=x;
		my=y;
	}
	
	/**
	* Setter for mouse location
	* @param x X Location
	* @param y Y Location
	*/
	public void setLoc(int x,int y) {
		mx=x;
		my=y;
	}

}