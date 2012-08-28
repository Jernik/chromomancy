package ConcreteObject;

/**
 *
 * @author Luke M
 */
public class ExplodingBullet extends ConcreteObject.PlasmaBomb{
    
    int numOfChildren=4;
    
    public void kill(){
        SmallBullet a =new SmallBullet();
        a.Displacement(this.xLoc, this.yLoc);
        a.Velocity(2, 0);
        a.D=this.D;
        this.owner.projectiles.add(a);
        a =new SmallBullet();
        a.Displacement(this.xLoc, this.yLoc);
        a.Velocity(0, 2);
        a.D=this.D;
        this.owner.projectiles.add(a);
        a =new SmallBullet();
        a.Displacement(this.xLoc, this.yLoc);
        a.Velocity(0, -2);
        a.D=this.D;
        this.owner.projectiles.add(a);
        a =new SmallBullet();
        a.Displacement(this.xLoc, this.yLoc);
        a.Velocity(-2, 0);
        a.D=this.D;
        this.owner.projectiles.add(a);
        this.owner.projectiles.remove(this);
    }
    public void emit(DisplayObjects.FluidField field){}
    
}
