package Maps;

import java.awt.*;

public class Pylon7 extends ConcreteObject.Pylon {

	public Pylon7() {}
	
	public void init() {
		actionFrame=new PylonScreen7();
		actionFrame.D=this.D;
		actionFrame.pylon=this;
	}
	
	public void paint(Graphics g) {
		if (activated) {
			actionFrame.paint(g);
		}
		if (!activated) {
			g.setColor(Color.RED);
			for (int i=0;i<=3;i++) {
				g.drawArc(388,425+50*i-32,64,64,129,102);
				g.drawArc(348,425+50*i-32,64,64,-51,102);
			}
		}
	}

}