package Maps;

/**
* Level 7 map.  The Meadow of Innocence.
*/
public class Map7 extends Maps.Map {
	
	int flowers;
	
	/**
	* {inheritdoc}
	*/
	public Map7() {
		this.flowers=1;
	}
	
	public Map7(int numFlowers) {
		flowers=numFlowers;
	}
	/**
	* {inheritdoc}
	*/
	public void init() {
		this.pylon=new Pylon7();
		this.pylon.D=this.D;
		this.pylon.init();
		this.up=new Maps.Map7(flowers+1);
		this.up.D=this.D;
		this.down=new Maps.Map7(flowers+1);
		this.down.D=this.D;
		this.left=new Maps.Map7(flowers+1);
		this.left.D=this.D;
		this.right=new Maps.Map7(flowers+1);
		this.right.D=this.D;
		for (int i=0;i<flowers;i++) {
			Maps.Entities.Flower7 flower=new Maps.Entities.Flower7();
			flower.D=this.D;
			flower.Displacement((float)(800*Math.random()),(float)(800*Math.random()));
			flower.Velocity(0,0);
			flower.Acceleration(0,0);
			flower.Color(150,150,150);
			this.entities.add(flower);
		}
        levelName="Meadow of Innocence";
	}
	/**
	* {inheritdoc}
	*/
	public void draw(java.awt.image.DataBuffer buffer) {
		drawConnections(buffer);
		if (entities.get(0).killed==true) {
			this.up=new Maps.Map6();
			this.up.D=this.D;
		}
                if(flowers>=42){
                    this.up= new Maps.Map8();
                    this.down= new Maps.Map8();
                    this.left= new Maps.Map8();
                    this.right= new Maps.Map8();
                }
	}

}