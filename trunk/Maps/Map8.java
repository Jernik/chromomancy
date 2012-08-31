package Maps;

/**
 * After getting to 42 flowers, Map8 appears which has the master flower
 * @author Luke M
 */
public class Map8 extends Map{
    
	public Map8() {}
	/**
	* {inheritdoc}
	*/
	public void init() {
		levelName="Map8";
		ConcreteObject.Entity flowerKing=new Maps.Entities.FlowerKing8();
		flowerKing.Color(127,127,127);
		flowerKing.Displacement(400,400);
		flowerKing.D=this.D;
		this.entities.add(flowerKing);
		((Maps.Entities.FlowerKing8)flowerKing).init();
	}
	/**
	* {inheritdoc}
	*/
	public void draw(java.awt.image.DataBuffer buffer) {
		drawConnections(buffer);
	}
	
}
