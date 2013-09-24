/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ConcreteObject;

/**
 *
 * @author Luke M
 */
public class BouncingBullet extends EnemyBullet{
    int bounceCount=0;
    int bounceNumber=0;
    
    public void loop(float time){
                int stepLen=D.field.stepLen;
		xAccel+=((D.field.xVel[DisplayObjects.FluidSolver.IX((int)(xLoc/stepLen),(int)(yLoc/stepLen))]+D.field.xVel[DisplayObjects.FluidSolver.IX((int)(xLoc/stepLen),(int)(yLoc/stepLen)+1)])*(stepLen-(xLoc-stepLen*(int)(xLoc/stepLen)))+(D.field.xVel[DisplayObjects.FluidSolver.IX((int)(xLoc/stepLen)+1,(int)(yLoc/stepLen))]+D.field.xVel[DisplayObjects.FluidSolver.IX((int)(xLoc/stepLen)+1,(int)(yLoc/stepLen)+1)])*(xLoc-stepLen*(int)(xLoc/stepLen)))/6000;
		yAccel+=((D.field.yVel[DisplayObjects.FluidSolver.IX((int)(xLoc/stepLen),(int)(yLoc/stepLen))]+D.field.yVel[DisplayObjects.FluidSolver.IX((int)(xLoc/stepLen)+1,(int)(yLoc/stepLen))])*(stepLen-(yLoc-stepLen*(int)(yLoc/stepLen)))+(D.field.yVel[DisplayObjects.FluidSolver.IX((int)(xLoc/stepLen),(int)(yLoc/stepLen)+1)]+D.field.yVel[DisplayObjects.FluidSolver.IX((int)(xLoc/stepLen)+1,(int)(yLoc/stepLen)+1)])*(xLoc-stepLen*(int)(xLoc/stepLen)))/6000;
		xLoc+=xVel*time;
		yLoc+=yVel*time;
		xVel+=xAccel*time;
		yVel+=yAccel*time;
		bounce();
		//xVel*=0.99; removing friction
		//yVel*=0.99;
		//xAccel*=0.99;
		//yAccel*=0.99;
		for (int i=0;i<this.projectiles.size();i++) {
			projectiles.get(i).loop(time);
		}
                //life--;
                if(bounceCount>bounceNumber)
                    kill();
    }
    
    public void bounce() {
		if (xLoc<20) xVel=Math.abs(xVel);
		if (xLoc>780) xVel=-Math.abs(xVel);
		if (yLoc<20) yVel=Math.abs(yVel);
		if (yLoc>780) yVel=-Math.abs(yVel);
                bounceCount++;
	}
    
    
}
