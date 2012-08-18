package Maps;

import java.awt.*;
import java.awt.event.KeyEvent;

public class PylonScreen7 extends DisplayObjects.PylonScreen {

	String message="";
	Utility.Console buffer=new Utility.Console(30);
	boolean acceptInput=false;

	public PylonScreen7() {
		super();
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(100,100,600,600);
		g.setColor(Color.RED);
		g.drawRect(100,100,600,600);
		g.setFont(new Font("Courier",Font.PLAIN,20));
		g.setColor(Color.BLACK);
		g.drawString("WELCOME TO THE GARDEN",350,150);
		g.setFont(new Font("Courier",Font.PLAIN,12));
		g.drawString(message,400-12*message.length()/3,675);
		for (int i=0;i<buffer.size;i++) {
			g.drawString(buffer.array[i],400-12*buffer.array[i].length()/3,200+15*i);
		}
	}
	
	public void keyReleased(KeyEvent e) {
		int code=e.getKeyCode();
		if (!acceptInput) {
			acceptInput=true;
			return;
		}
		if (code>=KeyEvent.VK_A && code<=KeyEvent.VK_Z) {
			message+=((char)('A'+code-KeyEvent.VK_A));
		}
		if (code>=KeyEvent.VK_0 && code<=KeyEvent.VK_9) {
			message+=((char)('0'+code-KeyEvent.VK_0));
		}
		if (code==KeyEvent.VK_SPACE) {
			message+=' ';
		}
		if (code==KeyEvent.VK_ENTER) {
			enter();
		}
		if (code==KeyEvent.VK_BACK_SPACE) {
			if (message.length()>=1) {
				message=message.substring(0,message.length()-1);
			}
		}
	}
	
	public void enter() {
		String[] args=message.split(" ");
		buffer.add(message);
		message="";
		if (args.length==0) {
			return;
		}
		if (args.length==1) {
			if (args[0].equals("")) {
				return;
			}
			if (args[0].equals("EXIT")) {
				D.deactivatePylon();
				return;
			}
			if (args[0].equals("CLOSE")) {
				D.deactivatePylon();
				return;
			}
			if (args[0].equals("DEACTIVATE")) {
				D.deactivatePylon();
				return;
			}
			if (args[0].equals("STOP")) {
				D.deactivatePylon();
				return;
			}
			if (args[0].equals("DIE")) {
                buffer.add("GOODBYE");
				D.you.killed=true;
				return;
			}
			if (args[0].equals("FLOWER") || args[0].equals("FLOWERS")) {
				buffer.add("THE FLOWERS ARE HAPPY THAT YOU ACKNOWLEDGE THEIR PRESENCE");
				buffer.add("THEY DONT TRY TO KILL YOU");
			}
			if (args[0].equals("HELP")) {
				buffer.add("THIS IS A GARDEN");
				buffer.add("TO EXIT, TYPE EXIT");
				buffer.add("TO GET HELP, TYPE HELP");
				buffer.add("THERE ARE MANY MANY FLOWERS IN THIS GARDEN");
				buffer.add("THE MORE YOU MOVE THE MORE FLOWERS YOU SEE");
				buffer.add("THEY MIGHT SHOOT POLLEN AT YOU AND TRY TO KILL YOU");
				buffer.add("BUT ONLY IF YOU TRY TO KILL THEM FIRST");
				buffer.add("DONT STEP ON THE FLOWERS");
				buffer.add("IF YOU DO THEN DODGE THE POLLEN");
				buffer.add("GOOD LUCK");
				return;
			}
		}
		if (args.length==2) {
			if (args[0].equals("GO")) {
				if (args[1].equals("NORTH")) {
					buffer.add("I CANT PRESS W FOR YOU");
					return;
				}
				if (args[1].equals("EAST")) {
					buffer.add("I CANT PRESS D FOR YOU");
					return;
				}
				if (args[1].equals("WEST")) {
					buffer.add("I CANT PRESS A FOR YOU");
					return;
				}
				if (args[1].equals("SOUTH")) {
					buffer.add("I CANT PRESS S FOR YOU");
					return;
				}
			}
			if (args[1].equals("FLOWER") || args[1].equals("FLOWERS")) {
				if (args[0].equals("STOMP") || args[0].equals("KILL") || args[0].equals("SNIFF") || args[0].equals("KISS") || args[0].equals("KICK") || args[0].equals("ATTACK") || args[0].equals("WATER") || args[0].equals("PICK") || args[0].equals("EAT")) {
					System.out.println("ACTIVING FLOWERS");
					buffer.add("YOU HAVE ENRAGED THE FLOWERS");
					buffer.add("WOE BE WITH YOU");
					for (ConcreteObject.Entity e:this.D.map.entities) {
						if (e instanceof Maps.Entities.Flower7) {
							((Maps.Entities.Flower7)(e)).active=true;
						}
					}
					return;
				}
			}
		}
		buffer.add("THE FLOWERS RUSTLE IN THE WIND IGNORING YOUR COMMAND");
		buffer.add("PERHAPS YOU SHOULD BE MORE CLEAR ABOUT WHAT YOU WANT TO DO");
		message="";
	}

}