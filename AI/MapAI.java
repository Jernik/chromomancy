/*
 * This AI is more of a helper AI. It also may handle spawning and whatnot. It will keep track of 
 * bigger information than just one entity, so like relative positions and stuff. 
 * It also may interface with the entity AIs for coordinated attacks
 */
package AI;

/**
 *
 * @author Luke M
 */
public class MapAI {
    public Maps.Map owner=null;
    
    public int colorLevels[][]=new int[800][800]; 
    /**
     * this basic update method, updates everything. In implementations of this, this can be used to interface with 
     * the lower level AIs
     */
    public void update(){
    updateColorLevels();
    }
    
    /**
     * cycles through every cell and stores the color levels in a 2d array
     */
    public void updateColorLevels(){
        for(int i = 0;i<800;i++)
            for(int j=0;j<800;j++)
            colorLevels[i][j]=this.owner.D.field.interpolatedDensity((int)i,(int)j);
                    }
            
     public int colorDifference(int colorA,int colorB) {
        int redDifference=Math.abs(colorA/256/256%256-colorB/256/256%256);
        int greenDifference=Math.abs(colorA/256%256-colorB/256%256);
        int blueDifference=Math.abs(colorA%256-colorB%256);
        return redDifference+greenDifference+blueDifference;
    }
    
}
