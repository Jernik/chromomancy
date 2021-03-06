package Maps;
/**
* NPC Data for Reena Bowe at level 2
*/
public class Reena2 extends ConcreteObject.Entity {

	/**
	* The name of the NPC
	*/
	public String name="Reena Bowe";
	/**
	* Pseudo-Constructor
	*/
	public Reena2() {
            
        }
	/**
	* {inheritdoc}
	*/
	public void init() {
		ConcreteObject.Text t=new ConcreteObject.Text();
		t.D=this.D;
                if(this.talk.options!=null)
                    t.Words(this.talk.options[0]);
		t.Displacement(this.xLoc,this.yLoc);
		float angle=(float)(100*Math.random());
		t.Velocity((float)(10*Math.sin(angle)),(float)(10*Math.cos(angle)));
		t.Color(this.red,this.green,this.blue);
                if(this.talk.responses!=null)
                    t.talk=this.talk.responses[0];
		t.owner=this;
		this.projectiles.add(t);
	}
        /**
         * initializes the dialouge tree. set up this way so that the dialouge tree has access to D.plotFlags
         */
            public void talk(){
		String[] options=null;
		Talking.Talk[] responses=null;
                //this.D.plotFlags[0]=2;
              if(this.D.plotFlags[0]==0){
		this.talk=new Talking.Talk("Hello babies!",null,null);
		this.saying=this.talk;
			options=new String[1];
			responses=new Talking.Talk[1];
			options[0]="Um... Hello?";
			responses[0]=new Talking.Talk("Welcome to Sirenia",null,null);
			this.talk.options=options;
			this.talk.responses=responses;
				options=new String[1];
				responses=new Talking.Talk[1];
				options[0]="What is it like here?";
				responses[0]=new Talking.Talk("It's temperate in the summer and temperate in the winter",null,null);
				this.talk.responses[0].options=options;
				this.talk.responses[0].responses=responses;
					options=new String[1];
					responses=new Talking.Talk[1];
					options[0]="Go on...";
					responses[0]=new Talking.Talk("Its square and windy and sparse",null,null);	
					this.talk.responses[0].responses[0].options=options;
					this.talk.responses[0].responses[0].responses=responses;
						options=new String[1];
						responses=new Talking.Talk[1];
						options[0]="Okay";
						responses[0]=new Talking.Talk("At the outside babies you've got about eternity here",null,null);
						this.talk.responses[0].responses[0].responses[0].options=options;
						this.talk.responses[0].responses[0].responses[0].responses=responses;
							options=new String[1];
							responses=new Talking.Talk[1];
							options[0]="Okay";
							responses[0]=new Talking.Talk("There's only one rule that I know of babies",null,null);
							this.talk.responses[0].responses[0].responses[0].responses[0].options=options;
							this.talk.responses[0].responses[0].responses[0].responses[0].responses=responses;
								options=new String[2];
								responses=new Talking.Talk[2];
								options[0]="Okay";
								responses[0]=new Talking.Talk("Oh, you don't care?",null,null);
                                                                //this.D.plotFlags[0]=1;
                                                                
								options[1]="What is it?";
								responses[1]=new Talking.Talk("God damn it, you've got to be colorful! (And batman) #",null,null);
                                                                //this.D.plotFlags[0]=2;
                                                                
								this.talk.responses[0].responses[0].responses[0].responses[0].responses[0].options=options;
								this.talk.responses[0].responses[0].responses[0].responses[0].responses[0].responses=responses;
                                                                
	}
        if(this.D.plotFlags[0]==1){
                this.talk=new Talking.Talk("You made it quite clear you didn't want to talk.",null,null);
		this.saying=this.talk;
        }
        if(this.D.plotFlags[0]==2){
                this.talk=new Talking.Talk("#",null,null);//batman symbol. is very important.
		this.saying=this.talk;
        }
        
     }
}