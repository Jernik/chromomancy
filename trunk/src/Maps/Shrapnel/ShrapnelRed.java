package Maps.Shrapnel;

/**
 *
 * Red Shrapnel superclass
 */
public class ShrapnelRed extends ConcreteObject.Shrapnel{
    int redXP=1;
    /**
     * Null constructor
     */
    public ShrapnelRed(){}
    
    public int getColor(){
    this.red=(int)(Math.random()*150)+105;
    this.blue=0;
    this.green=0;
    int color=this.red*256*256+this.green*256+this.blue;
    return color;
            }
    public void kill(){
    this.owner.projectiles.remove(this);
    }
    public void kill(ConcreteObject.Entity entity){
    this.D.you.blueXP+=this.blueXP;
        this.D. you.greenXP+=this.greenXP;
        this.D.you.redXP+=this.redXP;
       
        this.owner.projectiles.remove(this);
    }
    
}
