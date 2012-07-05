package Maps.Up.Up;
/**
* Level 3A map.  Contains Red NPC.
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
		this.down=new Maps.Up.Map();
		ConcreteObject.Entity red=new Maps.Up.Up.Red();
		red.Color(255,0,0);
		red.Displacement(400,400);
		red.D=this.D;
		this.entities.add(red);
		((Maps.Up.Up.Red)red).init();
	}
	/**
	* {inheritdoc}
	*/
	public void draw(java.awt.image.DataBuffer buffer) {
		drawConnections(buffer);
	}

}