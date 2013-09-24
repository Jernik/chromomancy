package Maps;
/**
* The Red enemy in level 3A
*/
public class Red3A extends ConcreteObject.Entity {
	/**
	* The name of the NPC
	*/
	public String name="Red";
	/**
	* Constructor.  Must call psudoconstructors to initialize.
	*/
	public Red3A() {}
	/**
	* {inheritdoc}
	*/
	public void init() {
        resistance=128;
        }
	/**
	* {inheritdoc}
	*/
	public void loop(float time) {
		super.loop(time);
		if (colorDifference(D.field.interpolatedDensity((int)xLoc,(int)yLoc),(this.red<<16 | this.green<<8 | this.blue))<resistance) {
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
		for (int i=0;i<5;i++) {
			float angle=(float)(314*Math.random());
			float magnitude=(float)(15*Math.random());
			ShrapnelGreen s=new ShrapnelGreen();
            ShrapnelBlue p=new ShrapnelBlue();
			s.Displacement(this.xLoc,this.yLoc);
			s.D=this.D;
			s.Velocity((float)(magnitude*Math.cos(angle)),(float)(magnitude*Math.sin(angle)));
			s.Acceleration((float)(magnitude*Math.cos(angle)/5),(float)(magnitude*Math.sin(angle)/5));
			s.owner=this;
			this.projectiles.add(s);
                        
                        angle=(float)(314*Math.random());
                        magnitude=(float)(15*Math.random());
			p.Displacement(this.xLoc,this.yLoc);
			p.D=this.D;
			p.Velocity((float)(magnitude*Math.cos(angle)),(float)(magnitude*Math.sin(angle)));
			p.Acceleration((float)(magnitude*Math.cos(angle)/5),(float)(magnitude*Math.sin(angle)/5));
			p.owner=this;
			this.projectiles.add(p);
		}
	}

}