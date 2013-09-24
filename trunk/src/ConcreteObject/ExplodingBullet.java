package ConcreteObject;

/**
 *
 * @author Luke M
 */
public class ExplodingBullet extends ConcreteObject.Projectile{
    
    //int numOfChildren=4;
     int delay=250;
     //int power=0;
     
     
     public void loop(float time) {
		super.loop(time);
		if (this.delay>0) {
			this.delay--;
		}
		
                if(delay<=0)
                    kill();
	}
    
    public void kill(){
       // System.out.println("Killing bullet");
        //System.out.println(this.killed);
        SmallBullet a =new SmallBullet();
        if(this.killed==false){
        
        a.Displacement(this.xLoc, this.yLoc);
        a.Velocity(2f, 0f);
        a.D=this.D;
        this.projectiles.add(a);
        
        a =new SmallBullet();
        a.Displacement(this.xLoc, this.yLoc);
        a.Velocity(0f, 2f);
        a.D=this.D;
        this.projectiles.add(a);
        
        a =new SmallBullet();
        a.Displacement(this.xLoc, this.yLoc);
        a.Velocity(0f, -2f);
        a.D=this.D;
        this.projectiles.add(a);
        
        a =new SmallBullet();
        a.Displacement(this.xLoc, this.yLoc);
        a.Velocity(-2f, 0f);
        a.D=this.D;
        this.projectiles.add(a);
        
        a=new SmallBullet();
        a.Displacement(this.xLoc, this.yLoc);
        a.Velocity(1.4f, 1.4f);
        a.D=this.D;
        this.projectiles.add(a);
        
        a =new SmallBullet();
        a.Displacement(this.xLoc, this.yLoc);
        a.Velocity(1.4f, -1.4f);
        a.D=this.D;
        this.projectiles.add(a);
        
        a =new SmallBullet();
        a.Displacement(this.xLoc, this.yLoc);
        a.Velocity(-1.4f, 1.4f);
        a.D=this.D;
        this.projectiles.add(a);
        
        a =new SmallBullet();
        a.Displacement(this.xLoc, this.yLoc);
        a.Velocity(-1.4f, -1.4f);
        a.D=this.D;
        this.projectiles.add(a);
        
        this.killed=true;
        }
    }
//    public void emit(DisplayObjects.FluidField field){}
    
}
