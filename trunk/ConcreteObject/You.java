package ConcreteObject;

/**
* This class contains player data.  Otherwise, identical to Entity.
* @see Entity
*/
public class You extends DynamicObject {
	/**
	* The currently selected jet.  Different jets have different
	* powers and colors.
	*/
	public int colorJet=1;
	/**
	* Whether or not the player is firing.
	*/
	boolean firing=false;
	
	/**
	* Null constructor.  For initialization, use psudoconstructors.
	*/
	public You() {}
	/**
	* {@inheritdoc}
	*/
	public void draw(java.awt.image.DataBuffer buffer) {
		int color=(int)(Math.random()*256*256*256);
		for (int j=0;j<10;j++) {
			for (int i=0;i<10;i++) {
				int x=(int)(i+this.xLoc-5f);
				int y=(int)(j+this.yLoc-5f);
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
		if (xLoc<20 && !(yLoc>350 && yLoc<450 && this.D.map.left!=null)) xVel=Math.abs(xVel);
		if (xLoc>780 && !(yLoc>350 && yLoc<450 && this.D.map.right!=null)) xVel=-Math.abs(xVel);
		if (yLoc<20 && !(xLoc>350 && xLoc<450 && this.D.map.up!=null)) yVel=Math.abs(yVel);
		if (yLoc>780 && !(xLoc>350 && xLoc<450 && this.D.map.down!=null)) yVel=-Math.abs(yVel);
		if (xLoc<00) xVel=Math.abs(xVel);
		if (xLoc>800) xVel=-Math.abs(xVel);
		if (yLoc<0) yVel=Math.abs(yVel);
		if (yLoc>800) yVel=-Math.abs(yVel);
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
		for (int i=0;i<this.projectiles.size();i++) {
			projectiles.get(i).loop(time);
		}
	}
	/**
	* {@inheritdoc}
	*/
	public void emit(DisplayObjects.FluidField field) {
		int stepLen=this.D.field.stepLen;
		int left=(int)xLoc/stepLen;
		int right=left+1;
		int up=(int)yLoc/stepLen;
		int down=up+1;
		if (D.mouse.leftDown) {
			field.dXVel[DisplayObjects.FluidSolver.IX(left,up)]=(D.mouse.mx-xLoc)*0.002f*(right*stepLen-xLoc)*(down*stepLen-yLoc);
			field.dYVel[DisplayObjects.FluidSolver.IX(left,up)]=(D.mouse.my-yLoc)*0.002f*(right*stepLen-xLoc)*(down*stepLen-yLoc);
			field.dXVel[DisplayObjects.FluidSolver.IX(right,up)]=(D.mouse.mx-xLoc)*0.002f*(xLoc-left*stepLen)*(down*stepLen-yLoc);
			field.dYVel[DisplayObjects.FluidSolver.IX(right,up)]=(D.mouse.my-yLoc)*0.002f*(xLoc-left*stepLen)*(down*stepLen-yLoc);
			field.dXVel[DisplayObjects.FluidSolver.IX(left,down)]=(D.mouse.mx-xLoc)*0.002f*(right*stepLen-xLoc)*(yLoc-up*stepLen);
			field.dYVel[DisplayObjects.FluidSolver.IX(left,down)]=(D.mouse.my-yLoc)*0.002f*(right*stepLen-xLoc)*(yLoc-up*stepLen);
			field.dXVel[DisplayObjects.FluidSolver.IX(right,down)]=(D.mouse.mx-xLoc)*0.002f*(xLoc-left*stepLen)*(yLoc-up*stepLen);
			field.dYVel[DisplayObjects.FluidSolver.IX(right,down)]=(D.mouse.my-yLoc)*0.002f*(xLoc-left*stepLen)*(yLoc-up*stepLen);
			if (this.colorJet==1) {
				field.rSource[DisplayObjects.FluidSolver.IX(left,up)]=20.0f*(right*stepLen-xLoc)*(down*stepLen-yLoc);
				field.rSource[DisplayObjects.FluidSolver.IX(right,up)]=20.0f*(xLoc-left*stepLen)*(down*stepLen-yLoc);
				field.rSource[DisplayObjects.FluidSolver.IX(left,down)]=20.0f*(right*stepLen-xLoc)*(yLoc-up*stepLen);
				field.rSource[DisplayObjects.FluidSolver.IX(right,down)]=20.0f*(xLoc-left*stepLen)*(yLoc-up*stepLen);
			}
			if (this.colorJet==2) {
				field.gSource[DisplayObjects.FluidSolver.IX(left,up)]=20.0f*(right*stepLen-xLoc)*(down*stepLen-yLoc);
				field.gSource[DisplayObjects.FluidSolver.IX(right,up)]=20.0f*(xLoc-left*stepLen)*(down*stepLen-yLoc);
				field.gSource[DisplayObjects.FluidSolver.IX(left,down)]=20.0f*(right*stepLen-xLoc)*(yLoc-up*stepLen);
				field.gSource[DisplayObjects.FluidSolver.IX(right,down)]=20.0f*(xLoc-left*stepLen)*(yLoc-up*stepLen);
			}
			if (this.colorJet==0) {
				field.bSource[DisplayObjects.FluidSolver.IX(left,up)]=20.0f*(right*stepLen-xLoc)*(down*stepLen-yLoc);
				field.bSource[DisplayObjects.FluidSolver.IX(right,up)]=20.0f*(xLoc-left*stepLen)*(down*stepLen-yLoc);
				field.bSource[DisplayObjects.FluidSolver.IX(left,down)]=20.0f*(right*stepLen-xLoc)*(yLoc-up*stepLen);
				field.bSource[DisplayObjects.FluidSolver.IX(right,down)]=20.0f*(xLoc-left*stepLen)*(yLoc-up*stepLen);
			}
		}
		if (D.mouse.rightDown) {
			this.firing=true;
		}
		if (!D.mouse.rightDown && this.firing) {
			Projectile p=new Projectile();
			p.Displacement(this.xLoc,this.yLoc);
			p.Velocity(this.xVel,this.yVel);
			p.Acceleration(this.xAccel,this.yAccel);
			p.D=D;
			this.projectiles.add(p);
			this.firing=false;
		}
	}

}