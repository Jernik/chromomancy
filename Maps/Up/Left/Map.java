package Maps.Up.Left;
/**
* Level 3C map.  Contains Green enemy.
*/
public class Map extends Maps.Map {
	/**
	* {inheritdoc}
	*/
	public Map() {}
	/**
	* {inheritdoc}
	*/
	public void init() {
		this.right=new Maps.Up.Map();
		ConcreteObject.Entity green=new Maps.Up.Left.Green();
		green.Color(0,255,0);
		green.Displacement(400,400);
		green.D=this.D;
		this.entities.add(green);
		((Maps.Up.Left.Green)green).init();
	}
	/**
	* {inheritdoc}
	*/
	public void draw(java.awt.image.DataBuffer buffer) {
		drawConnections(buffer);
	}

}