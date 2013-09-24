package Maps;

import Maps.Pylon6;

/**
* Level 6 map.  Kills player immediately upon entry.
*/
public class Map6 extends Maps.Map {
	/**
	* {inheritdoc}
	*/
	public Map6() {}
	/**
	* {inheritdoc}
	*/
	public void init() {
		this.pylon=new Pylon6();
		this.pylon.D=this.D;
		this.pylon.init();
		this.up=new Maps.Map();
		this.up.D=this.D;
		this.down=new Maps.Map7();
		this.down.D=this.D;
        levelName="Map6";
	}
	/**
	* {inheritdoc}
	*/
	public void draw(java.awt.image.DataBuffer buffer) {
		drawConnections(buffer);
	}

}