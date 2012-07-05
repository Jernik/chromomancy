package Maps.Up.Right;
/**
* Level 3B map.  Contains Blue enemy.
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
		this.left=new Maps.Up.Map();
		ConcreteObject.Entity blue=new Maps.Up.Right.Blue();
		blue.Color(0,0,255);
		blue.Displacement(400,400);
		blue.D=this.D;
		this.entities.add(blue);
		((Maps.Up.Right.Blue)blue).init();
	}
	/**
	* {inheritdoc}
	*/
	public void draw(java.awt.image.DataBuffer buffer) {
		drawConnections(buffer);
	}

}