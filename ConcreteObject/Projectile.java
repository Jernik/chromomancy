package ConcreteObject;
/**
* This is the superclass for every transient DynamicObject.
* They are usually in the form of bullets and shrapnel, but can take other
* Forms as well, such as items and text.
*/
public class Projectile extends DynamicObject {

	/**
	* The object that created this Projectile.
	*/
	public DynamicObject owner;
	/**
	* {@inheritdoc}
	*/
	public void draw(java.awt.image.DataBuffer buffer) {
		int color=(int)(Math.random()*256*256*256);
		for (int j=0;j<4;j++) {
			for (int i=0;i<4;i++) {
				int x=(int)(i+this.xLoc-2f);
				int y=(int)(j+this.yLoc-2f);
				if (!(x<0 || x>799 || y<0 || y>799)) {
					buffer.setElem(800*y+x,color);
				}
			}
		}
		for (Projectile p:this.projectiles) {
			p.draw(buffer);
		}
	}
	/**
	* {@inheritdoc}
	*/
	public void bounce() {
		if (xLoc<20) xVel=Math.abs(xVel);
		if (xLoc>780) xVel=-Math.abs(xVel);
		if (yLoc<20) yVel=Math.abs(yVel);
		if (yLoc>780) yVel=-Math.abs(yVel);
	}
	/**
	* {@inheritdoc}
	*/
	public void loop(float time) {
		int stepLen=D.field.stepLen;
		xLoc+=xVel*time;
		yLoc+=yVel*time;
		xVel+=xAccel*time;
		yVel+=yAccel*time;
		xAccel+=((D.field.xVel[DisplayObjects.FluidSolver.IX((int)(xLoc/stepLen),(int)(yLoc/stepLen))]+D.field.xVel[DisplayObjects.FluidSolver.IX((int)(xLoc/stepLen),(int)(yLoc/stepLen)+1)])*(stepLen-(xLoc-stepLen*(int)(xLoc/stepLen)))+(D.field.xVel[DisplayObjects.FluidSolver.IX((int)(xLoc/stepLen)+1,(int)(yLoc/stepLen))]+D.field.xVel[DisplayObjects.FluidSolver.IX((int)(xLoc/stepLen)+1,(int)(yLoc/stepLen)+1)])*(xLoc-stepLen*(int)(xLoc/stepLen)))/6000;
		yAccel+=((D.field.yVel[DisplayObjects.FluidSolver.IX((int)(xLoc/stepLen),(int)(yLoc/stepLen))]+D.field.yVel[DisplayObjects.FluidSolver.IX((int)(xLoc/stepLen)+1,(int)(yLoc/stepLen))])*(stepLen-(yLoc-stepLen*(int)(yLoc/stepLen)))+(D.field.yVel[DisplayObjects.FluidSolver.IX((int)(xLoc/stepLen),(int)(yLoc/stepLen)+1)]+D.field.yVel[DisplayObjects.FluidSolver.IX((int)(xLoc/stepLen)+1,(int)(yLoc/stepLen)+1)])*(xLoc-stepLen*(int)(xLoc/stepLen)))/6000;
		bounce();
		xVel*=0.99;
		yVel*=0.99;
		xAccel*=0.99;
		yAccel*=0.99;
		for (int i=0;i<this.projectiles.size();i++) {
			projectiles.get(i).loop(time);
		}
	}

}