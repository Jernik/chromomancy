package DisplayObjects;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.lang.Exception;
import java.lang.Runnable;
import java.io.*;
import javax.imageio.*;
import java.util.ArrayList;

/**
* This is the primary JFrame class that takes care of all macro-tasks
* including Listeners and Threads.
*/
public class Display extends JFrame implements KeyListener, MouseListener, MouseMotionListener, Runnable, WindowListener {

	/**
	* Thread to loop drawing to the frame
	*/
	public Thread drawingThread;
	/**
	* Fluid Field in charge of physics
	*/
	public FluidField field=null;
	/**
	* Image for double buffering
	*/
	public BufferedImage betterDoubleBuffer=null;
	/**
	* DataBuffer associated with the double buffering image
	*/
	public DataBuffer dbPlasma=null;
	/**
	* Mouse object to store current mouse status and location
	*/
	public Pointer mouse=new Pointer(0,0);
	/**
	* The player object
	*/
	public ConcreteObject.You you=new ConcreteObject.You();
	/**
	* The map of the current level the player is at
	*/
	public Maps.Map map=new Maps.Map();
	/**
	* Starting time of program in milliseconds
	*/
	public long iTime;
	/**
	* Number of frames that have passed
	*/
	public int frameCount=0;
	/**
	* Number of Euler loop calculations have passed
	*/
	public int loopCount=0;
	/**
	* Whether or not the screen is frozen.  This is used in level
	* transition and when other windows are opened.
	*/
	public boolean frameFreeze=false;
	/**
	* Shortcut used to end the program.  For debugging only.
	*/
	public int passwordTyped=0;
	/**
	* Whether or not to display the title.  This is only true when on the
	* first level.
	*/
	public boolean title=true;
	
	/**
	* Constructor.  This also contains the main loop and the JFrame setup
	* process, along with video-making code.
	*/
	public Display() {
		betterDoubleBuffer = new BufferedImage(800,800, 1);
	    dbPlasma = betterDoubleBuffer.getRaster().getDataBuffer(); 
		field=new FluidField(83,10);
		field.display=this;
		setSize(800,800);
		setUndecorated(true);
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		init();
		setVisible(true);
		iTime=System.currentTimeMillis();
		step();
		drawingThread=new Thread(this);
		System.out.println("THREAD");
		drawingThread.start();
		System.out.println("PRELOOP");
		LOOP:while (true) {
			loopCount++;
			//try {Thread.sleep(1);}catch(Exception e) {}
			/*if (frameCount%5==0) {
				try {
					File frameOut = new File((10000+(frameCount/5))+".png");
					ImageIO.write(betterDoubleBuffer, "png", frameOut);
					System.out.println(frameCount/5);
					if (frameCount/5>=2600) System.exit(0);
				} catch (IOException ex) {}
			}*/
			//Uncommment for video making
			step();
			if (you.xLoc<0 && map.left!=null) moveLeft();
			if (you.xLoc>800 && map.right!=null) moveRight();
			if (you.yLoc<0 && map.up!=null) moveUp();
			if (you.yLoc>800 && map.down!=null) moveDown();
			for (int i=0;i<field.rSource.length;i++) {
				field.rSource[i]=0.0f;
				field.gSource[i]=0.0f;
				field.bSource[i]=0.0f;
				field.dXVel[i]=0.0f;
				field.dYVel[i]=0.0f;
			}
			you.emit(field);
			for (int i=0;i<map.entities.size();i++) {
				map.entities.get(i).emit(field);
			}
			chill();
		}
	}
	/**
	* Method called to proceed to the northern map.  Freezes drawing during
	* the transition.
	*/
	public void moveUp() {
		fadeToBlack();
		this.map.up.D=this;
		this.map.up.init();
		this.map=this.map.up;
		frameFreeze=false;
		you.yLoc=700;
		drawingThread.start();
	}
	/**
	* Method called to proceed to the southern map.  Freezes drawing during
	* the transition.
	*/
	public void moveDown() {
		fadeToBlack();
		this.map.down.D=this;
		this.map.down.init();
		this.map=this.map.down;
		frameFreeze=false;
		you.yLoc=100;
		drawingThread.start();
	}
	/**
	* Method called to proceed to the western map.  Freezes drawing during
	* the transition.
	*/
	public void moveLeft() {
		fadeToBlack();
		this.map.left.D=this;
		this.map.left.init();
		this.map=this.map.left;
		frameFreeze=false;
		you.xLoc=700;
		drawingThread.start();
	}
	/**
	* Method called to proceed to the eastern map.  Freezes drawing during
	* the transition.
	*/
	public void moveRight() {
		fadeToBlack();
		this.map.right.D=this;
		this.map.right.init();
		this.map=this.map.right;
		frameFreeze=false;
		you.xLoc=100;
		drawingThread.start();
	}
	/**
	* Freezes the drawing, then splits the JFrame into 9 pieces and hurls
	* them in various directions, and then ending the program.
	*/
	public void deathAnimation() {
		fadeToBlack();
		DeathPanel[][] panels=new DeathPanel[3][3];
		for (int i=0;i<3;i++) {
			for (int j=0;j<3;j++) {
				panels[j][i]=new DeathPanel((int)(getLocation().getX()+266*i),(int)(getLocation().getY()+266*j));
			}
		}
		try {Thread.sleep(300);}catch (Exception e) {}
		this.setVisible(false);
		panels[2][1].xVel=0;
		panels[2][1].yVel=5;
		panels[2][1].fall();
		try {Thread.sleep(200);}catch (Exception e) {}
		panels[2][2].xVel=3;
		panels[2][2].yVel=3;
		panels[2][2].fall();
		try {Thread.sleep(200);}catch (Exception e) {}
		panels[1][2].xVel=5;
		panels[1][2].yVel=0;
		panels[1][2].fall();
		try {Thread.sleep(200);}catch (Exception e) {}
		panels[0][2].xVel=3;
		panels[0][2].yVel=-3;
		panels[0][2].fall();
		try {Thread.sleep(200);}catch (Exception e) {}
		panels[0][1].xVel=0;
		panels[0][1].yVel=-5;
		panels[0][1].fall();
		try {Thread.sleep(200);}catch (Exception e) {}
		panels[0][0].xVel=-3;
		panels[0][0].yVel=-3;
		panels[0][0].fall();
		try {Thread.sleep(200);}catch (Exception e) {}
		panels[1][0].xVel=-5;
		panels[1][0].yVel=0;
		panels[1][0].fall();
		try {Thread.sleep(200);}catch (Exception e) {}
		panels[2][0].xVel=-3;
		panels[2][0].yVel=3;
		panels[2][0].fall();
		try {Thread.sleep(200);}catch (Exception e) {}
		panels[2][1].xVel=0;
		panels[2][1].yVel=5;
		panels[2][1].fall();
		try {Thread.sleep(200);}catch (Exception e) {}
		panels[1][1].xVel=0;
		panels[1][1].yVel=0;
		panels[1][1].fall();
		try {Thread.sleep(2000);}catch (Exception e) {}
		System.exit(0);
	}
	/**
	* Freezes the program for level transition, clears the Fluid Field
	* data, then unfreezes, and creates a new drawing Thread.
	*/
	public void fadeToBlack() {
		title=false;
		frameFreeze=true;
		for (int i=0;i<800;i++) {
			for (int j=0;j<800;j++) {
				dbPlasma.setElem(i+j*800,0);
				if (j%800==0 && i%10==0) {
					try {Thread.sleep(1);}catch(Exception e) {}
					Graphics g=this.getGraphics();
					g.drawImage(betterDoubleBuffer,0,0,null);
				}
			}
		}
		for (int i=0;i<field.rSource.length;i++) {
			field.rDensity[i]=0.0f;
			field.gDensity[i]=0.0f;
			field.bDensity[i]=0.0f;
			field.rSource[i]=0.0f;
			field.gSource[i]=0.0f;
			field.bSource[i]=0.0f;
			field.dXVel[i]=0.0f;
			field.dYVel[i]=0.0f;
			field.xVel[i]=0.0f;
			field.yVel[i]=0.0f;
		}
		drawingThread=new Thread(this);
	}
	/**
	* This method sets up the initial position of the player and creates
	* the map.
	*/
	public void init() {
		you.Displacement(400,400);
		you.Velocity(0,0);
		you.Acceleration(0,0);
		you.D=this;
		map.D=this;
		map.init();
	}
	/**
	* This is a loop that repeatedly paints the game screen to the buffer.
	*/
	public void run() {
		while(!frameFreeze) {
			paint();
		}
	}
	/**
	* This method steps the system through time, calling all sublevel step
	* methods.  It also detects whether or not the player is dead.
	*/
	public void step() {
		field.step();
		you.loop(0.1f);
		for (int i=0;i<map.entities.size();i++) {
			map.entities.get(i).loop(0.1f);
		}
		if (you.killed) {
			deathAnimation();
		}
	}
	/**
	* This draw the game screen to the buffer.
	*/
	public void paint() {
		for (int i=0;i<800;i++) {
			for (int j=0;j<800;j++) {
				int color=field.interpolatedDensity(i,j);
				dbPlasma.setElem(i+j*800,color);
			}
		}
		you.draw(dbPlasma);
		for (int i=0;i<map.entities.size();i++) {
			map.entities.get(i).draw(dbPlasma);
		}
		map.draw(dbPlasma);
		Graphics g=betterDoubleBuffer.getGraphics();
		if (title) {
			g.setColor(java.awt.Color.WHITE);
			g.setFont(new Font("Ariel",Font.PLAIN,90));
			g.drawString("CHROMOMANCY",50,400);
		}
		g=this.getGraphics();
		g.drawImage(betterDoubleBuffer,0,0,null);
		frameCount++;
		/*	try {
					File frameOut = new File((10000+(frameCount))+".png");
					ImageIO.write(betterDoubleBuffer, "png", frameOut);
					//System.out.println(frameCount);
					//if (frameCount>=1460) System.exit(0);
			} catch (IOException ex) {}
		}*/
	}
	
	/*public int reflect256(int n) {
		if (n<256) return n;
		return 512-n;
	}
	
	public Color doubleToColor(double d) {
		if (d<0) d=-d;
		return new Color((int)(d*100)%256,(int)(d*10)%256,(int)(d*1)%256);
	}
	
	public void paint(Graphics g) {}*/
	
	public void chill() {
		long time=System.currentTimeMillis();
		int wait=30-(int)time%30;
		//try{Thread.sleep(wait);}catch(Exception e) {}
	}
	/**
	* Main method to create new Display object
	*/
	public static void main(String[] args) {
		Display d=new Display();
	}
	/**
	* KeyPressed handler.  This affects player controls.
	*/
	public void keyPressed(KeyEvent e) {
		int key=e.getKeyCode();
		if (key==KeyEvent.VK_UP) {
			setLocation((int)(getLocation().getX()),(int)(getLocation().getY()-2));
		}
		if (key==KeyEvent.VK_DOWN) {
			setLocation((int)(getLocation().getX()),(int)(getLocation().getY()+2));
		}
		if (key==KeyEvent.VK_LEFT) {
			setLocation((int)(getLocation().getX()-2),(int)(getLocation().getY()));
		}
		if (key==KeyEvent.VK_RIGHT) {
			setLocation((int)(getLocation().getX()+2),(int)(getLocation().getY()));
		}
		if (key==KeyEvent.VK_W) {
			you.Acceleration(null,-1);
		}
		if (key==KeyEvent.VK_A) {
			you.Acceleration(-1,null);
		}
		if (key==KeyEvent.VK_S) {
			you.Acceleration(null,1);
		}
		if (key==KeyEvent.VK_D) {
			you.Acceleration(1,null);
		}
		if (key==KeyEvent.VK_R) {
			you.colorJet=1;
		}
		if (key==KeyEvent.VK_G) {
			you.colorJet=2;
		}
		if (key==KeyEvent.VK_B) {
			you.colorJet=0;
		}
	}
	/**
	* KeyReleased handler.  This affects player controls.
	*/
	public void keyReleased(KeyEvent e) {
		int key=e.getKeyCode();
		if (key==KeyEvent.VK_ESCAPE || key==KeyEvent.VK_BACK_SPACE) {
			System.exit(0);
		}
		if (key==KeyEvent.VK_W) {
			you.Acceleration(null,0);
		}
		if (key==KeyEvent.VK_A) {
			you.Acceleration(0,null);
		}
		if (key==KeyEvent.VK_S) {
			you.Acceleration(null,0);
		}
		if (key==KeyEvent.VK_D) {
			you.Acceleration(0,null);
		}
	}
	/**
	* KeyTyped handler.  This is for debugging and includes autodeath and
	* forcing a quit.
	*/
	public void keyTyped(KeyEvent e) {
		if (e.getKeyChar()==' ') {
			deathAnimation();
			//System.out.println(field.density[FluidSolver.IX(20,20)]);
			/*try {
				File frameOut = new File("LogisticInterpolation.png");
				ImageIO.write(betterDoubleBuffer, "png", frameOut);
				} catch (IOException ex) {}*/ //Uncomment if you want to enable screenies
		}
		if (e.getKeyChar()=='e' && passwordTyped==0) {
			passwordTyped=1;
		}
		if (e.getKeyChar()=='n' && passwordTyped==1) {
			passwordTyped=2;
		}
		if (e.getKeyChar()=='d' && passwordTyped==2) {
			System.exit(0);
		}
	}
	/**
	* This sends mouse actions to the Pointer object
	*/
	public void mousePressed(MouseEvent e) {
		int x=e.getX(),y=e.getY();
		if (e.getButton()==MouseEvent.BUTTON1) {
			mouse.leftDown=true;
		}
		if (e.getButton()==MouseEvent.BUTTON3) {
			mouse.rightDown=true;
		}
	}
	/**
	* Used for debug.  Outputs frame rates.
	*/
	public void mouseClicked(MouseEvent e) {
		System.out.println("FPS: "+(1000*frameCount/(System.currentTimeMillis()-iTime)));
		System.out.println("CPS: "+(1000*loopCount/(System.currentTimeMillis()-iTime)));
	}
	/**
	* This sends mouse actions to the Pointer object
	*/
	public void mouseReleased(MouseEvent e) {
		if (e.getButton()==MouseEvent.BUTTON1) {
			mouse.leftDown=false;
		}
		if (e.getButton()==MouseEvent.BUTTON3) {
			mouse.rightDown=false;
		}
	}
	/**
	* Does nothing.
	*/
	public void mouseEntered(MouseEvent e) {}
	/**
	* Does nothing.
	*/
	public void mouseExited(MouseEvent e) {}
	/**
	* This sends mouse actions to the Pointer object
	*/
	public void mouseMoved(MouseEvent e) {
		mouse.setLoc(e.getX(),e.getY());
	}
	/**
	* This sends mouse actions to the Pointer object
	*/
	public void mouseDragged(MouseEvent e) {
		mouse.setLoc(e.getX(),e.getY());
	}
	/**
	* Does nothing.
	*/
	public void windowActivated(WindowEvent e) {}
	/**
	* Does nothing.
	*/
	public void windowClosed(WindowEvent e) {}
	/**
	* Exits when the window is closed.
	*/
	public void windowClosing(WindowEvent e) {
		System.exit(0);
	}
	/**
	* Does nothing.
	*/
	public void windowDeactivated(WindowEvent e) {}
	/**
	* Does nothing.
	*/
	public void windowDeiconified(WindowEvent e) {}
	/**
	* Does nothing.
	*/
	public void windowIconified(WindowEvent e) {}
	/**
	* Does nothing.
	*/
	public void windowOpened(WindowEvent e) {}

}