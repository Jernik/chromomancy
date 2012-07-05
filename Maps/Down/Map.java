package Maps.Down;
/**
* Level 6 map.  Kills player immediately upon entry.
*/
public class Map extends Maps.Map {
	/**
	* {inheritdoc}
	*/
	public Map() {
		this.up=new Maps.Map();
	}
	/**
	* {inheritdoc}
	*/
	public void init() {
		D.you.killed=true;
	}
	/**
	* {inheritdoc}
	*/
	public void draw(java.awt.image.DataBuffer buffer) {}

}