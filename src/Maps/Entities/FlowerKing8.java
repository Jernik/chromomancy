package Maps.Entities;
/**
* NPC Data for Flower King at level 8
*/
public class FlowerKing8 extends ConcreteObject.Entity {

	/**
	* The name of the NPC
	*/
	public String name="Lizzie"; //Or use some flower name that symbolizes patience
	/**
	* Constructor, also initializes the dialogue tree.
	*/
	public FlowerKing8() {
		String[] options=null;
		Talking.Talk[] responses=null;
		this.talk=new Talking.Talk("Who are you?",null,null);
		this.saying=this.talk;
			options=new String[5];
			responses=new Talking.Talk[5];
			options[0]="Who are you?";
			responses[0]=new Talking.Talk("Tell me who you are first.",null,null);
			options[1]="I am a dot.";
			responses[1]=new Talking.Talk("What kind of dot?",null,null);
			options[2]="That is not your concern.";
			responses[2]=new Talking.Talk("I'll make it my concern.",null,null);
			options[3]="I am a slayer of flowers.";
			responses[3]=new Talking.Talk("Oh? Then slay this!",null,null);
			options[4]="An explorer";
			responses[4]=new Talking.Talk("How about you explore somewhere else?",null,null);
			this.talk.options=options;
			this.talk.responses=responses;
				options=new String[2];
				responses=new Talking.Talk[2];
				options[0]="What do you do?";
				responses[0]=new Talking.Talk("I rule over all of the flowers.  I also make sure nobody leaves this sacred ground alive.",null,null);
				options[1]="I passed by a lot of flowers.";
				responses[1]=new Talking.Talk("I am the monarch flower, ruler of all things with petals.",null,null);
				this.talk.responses[0].options=options;
				this.talk.responses[0].responses=responses;
					options=new String[1];
					responses=new Talking.Talk[1];
					options[0]="Does this mean you're going to kill me?";
					responses[0]=new Talking.Talk("Indeed.  I must.",null,null);	
					this.talk.responses[0].responses[0].options=options;
					this.talk.responses[0].responses[0].responses=responses;
						options=new String[1];
						responses=new Talking.Talk[1];
						options[0]="Okay";
						responses[0]=new Talking.Talk("What? Just Okay?",null,null);
						this.talk.responses[0].responses[0].responses[0].options=options;
						this.talk.responses[0].responses[0].responses[0].responses=responses;
							options=new String[1];
							responses=new Talking.Talk[1];
							options[0]="Did you want me to say thank you?";
							responses[0]=new Talking.Talk("No! You must cower in fear!",null,null);
							this.talk.responses[0].responses[0].responses[0].responses[0].options=options;
							this.talk.responses[0].responses[0].responses[0].responses[0].responses=responses;
								options=new String[2];
								responses=new Talking.Talk[2];
								options[0]="Why?";
								responses[0]=new Talking.Talk("Because I said so!",null,null);
								options[1]="Oh no, a big oversized flower wants to kill me!";
								responses[1]=new Talking.Talk("That's right I'm going to kill you!",null,null);
								this.talk.responses[0].responses[0].responses[0].responses[0].responses[0].options=options;
								this.talk.responses[0].responses[0].responses[0].responses[0].responses[0].responses=responses;
					options=new String[1];
					responses=new Talking.Talk[1];
					options[0]="Even bikes?";
					responses[0]=new Talking.Talk("Don't be a wise donkey with me.  You're treading on sacred ground.",null,null);
					this.talk.responses[0].responses[1].options=options;
					this.talk.responses[0].responses[1].responses=responses;
	}
	/**
	* {inheritdoc}
	*/
	public void init() {
		ConcreteObject.Text[] initialPhrases=new ConcreteObject.Text[5];
		for (int i=0;i<5;i++) {
			ConcreteObject.Text t=new ConcreteObject.Text();
			t.D=this.D;
			t.Words(this.talk.options[i]);
			t.Displacement(this.xLoc,this.yLoc);
			float angle=(float)(100*Math.random());
			t.Velocity((float)(10*Math.sin(angle)),(float)(10*Math.cos(angle)));
			t.Color(this.red,this.green,this.blue);
			t.talk=this.talk.responses[i];
			t.owner=this;
			this.projectiles.add(t);
			initialPhrases[i]=t;
		}
		for (int i=0;i<5;i++) {
			for (int j=0;j<5;j++) {
				if (i!=j) {
					initialPhrases[i].companions.add(initialPhrases[j]);
				}
			}
		}
	}
	
	public void talk() {}

}