package Maps.Up.Left;
/**
* The Green enemy in level 3C
*/
public class Green extends ConcreteObject.Entity {
	/**
	* Name of NPC
	*/
	public String name="Green";
	/**
	* Constructor.  Must call psudoconstructors to initialize.
	*/
	public Green() {}
	/**
	* {inheritdoc}
	*/
	public void init() {}
	/**
	* {inheritdoc}
	*/
	public void loop(float time) {
		super.loop(time);
		if (colorDifference(D.field.interpolatedDensity((int)xLoc,(int)yLoc),(this.red<<16 | this.green<<8 | this.blue))<128) {
			if (this.killed==false) {
				this.killed=true;
				kill();
			}
		}
	}
	/**
	* Kills NPC and creates 10 shrapnel pieces of fluctuating color
	*/
	public void kill() {
		for (int i=0;i<10;i++) {
			float angle=(float)(314*Math.random());
			float magnitude=(float)(15*Math.random());
			Shrapnel s=new Shrapnel();
			s.Displacement(this.xLoc,this.yLoc);
			s.D=this.D;
			s.Velocity((float)(magnitude*Math.cos(angle)),(float)(magnitude*Math.sin(angle)));
			s.Acceleration((float)(magnitude*Math.cos(angle)/5),(float)(magnitude*Math.sin(angle)/5));
			s.owner=this;
			this.projectiles.add(s);
		}
	}

}