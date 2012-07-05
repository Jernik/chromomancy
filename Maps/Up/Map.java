package Maps.Up;
/**
* Level 2 map.  Contains Reena Bowe, who introduces the player to the game.
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
		this.right=new Maps.Up.Right.Map();
		this.up=new Maps.Up.Up.Map();
		this.left=new Maps.Up.Left.Map();
		this.down=new Maps.Map();
		ConcreteObject.Entity reena=new Maps.Up.Reena();
		reena.Color(127,127,127);
		reena.Displacement(400,400);
		reena.D=this.D;
		this.entities.add(reena);
		((Maps.Up.Reena)reena).init();
	}
	/**
	* {inheritdoc}
	*/
	public void draw(java.awt.image.DataBuffer buffer) {
		drawConnections(buffer);
	}

}