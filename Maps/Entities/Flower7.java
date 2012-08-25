package Maps.Entities;

public class Flower7 extends ConcreteObject.Entity {

	public boolean active=false;
	public int ammo=4;
	public long firingTime=0;
	
	public Flower7() {}
	
	public void draw(java.awt.image.DataBuffer buffer) {
		for (int i=0;i<this.projectiles.size();i++) {
			this.projectiles.get(i).draw(buffer);
		}
		for (int i=0;i<100;i++) {
			int x=(int)(this.xLoc+10*Math.cos(2*i/1.0)*Math.cos(i/1.0));
			int y=(int)(this.yLoc+10*Math.cos(2*i/1.0)*Math.sin(i/1.0));
			buffer.setElem(800*y+x,255*256);
		}
	}
	
	public void loop(float time) {
		super.loop(time);
		if (active && firingTime==0) {
			firingTime=System.currentTimeMillis();
		}
		if (active && System.currentTimeMillis()>firingTime) {
			firingTime=firingTime+2000;
			if (ammo%2==0 && ammo>0) {
				fireBullet(0,5);
				fireBullet(0,-5);
				fireBullet(5,0);
				fireBullet(-5,0);
				fireBullet(3.5f,3.5f);
				fireBullet(3.5f,-3.5f);
				fireBullet(-3.5f,-3.5f);
				fireBullet(-3.5f,3.5f);
			}
			if (ammo%2==1 && ammo>0) {
				fireBullet(3,4);
				fireBullet(-3,4);
				fireBullet(-3,-4);
				fireBullet(3,-4);
				fireBullet(4,3);
				fireBullet(-4,3);
				fireBullet(-4,-3);
				fireBullet(4,-3);
			}
			ammo--;
			if (ammo==0) {
				this.killed=true;
			}
		}
	}

}