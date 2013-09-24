package Music;

import javax.sound.midi.*;
import java.lang.Runnable;

public class MusicPlayer implements Runnable {

	public int channelInstruments[]=
	{2,10,12,13,14,15};//6 channels.  First 1 and 2 are percussion, 3 and 4 are wind, 5 and 6 are string.
	
	public int[] scoreLengths=
	{10,3,1,1,1,1};//6 channels.  These are the number of notes in each progression.
	
	public int[] maxDelays=
	{9000,1800,1400,1400,1400,1400};//6 channels.  These are the max milliseconds between similar progressions.
	
	public int[] minDelays=
	{3000,1300,1000,1000,1000,1000};//6 channels.  These are the min milliseconds between similar progressions.

	public int[][][] scores=
	{
	{
	{0,1,60,80},
	{200,1,58,60},
	{400,1,56,40},
	{800,1,62,115},
	{1200,1,64,115},
	{1600,1,60,80},
	{1800,1,58,60},
	{2000,1,56,40},
	{2400,1,62,115},
	{2800,1,64,115}
	},
	{
	{200,2,60,80},
	{200,2,59,85},
	{200,2,61,95}
	},
	{
	{400,3,60,80},
	{500,3,59,85},
	{600,3,61,95},
	{700,3,61,95},
	},
	{
	{600,4,60,80}
	},
	{
	{800,5,60,80}
	},
	{
	{1000,6,60,80}
	}
	}; //4 columns: Time (ms), Channel, Note, Velocity.  6 double arrays for 6 channels.
	
	public int[] notesPlayed=
	{0,0,0,0,0,0};//This array keeps track of the number of notes played on each channel.
	//Once this number reaches the corresponding number in scoreLengths,
	//The notes are set forward by a random number less than the corresponding
	//maxDelays and the number here is reset to 0.  It increments every time
	//a note is played on the corresponding channel.
	
	public MusicPlayer() {}
	
	public void run() {
		Synthesizer	synth = null;
		try {
			synth = MidiSystem.getSynthesizer();
			synth.open();
		} catch (Exception e){
                System.err.println("Cannot Open Synthesizer");
                }
		Soundbank soundbank = synth.getDefaultSoundbank();
		MidiChannel[] channels = synth.getChannels();
		MidiChannel	channel = channels[0];
		for (int i=0;i<channelInstruments.length;i++) {
			channels[i].programChange(channelInstruments[i]);
		}
		long startTime=System.currentTimeMillis();
		while(true) {
			int timeElapsed=(int)(System.currentTimeMillis()-startTime);
			int nextChannelToPlay=-1;
			int soonestTime=Integer.MAX_VALUE;
			for (int i=0;i<notesPlayed.length;i++) {
				if (scores[i][notesPlayed[i]][0]<soonestTime) {
					nextChannelToPlay=i;
					soonestTime=scores[i][notesPlayed[i]][0];
				}
			}
			try {
				Thread.sleep(soonestTime-(System.currentTimeMillis()-startTime));
			} catch (Exception e) {
                        //System.err.println("Cannot Sleep Music Thread");}
			channels[nextChannelToPlay].noteOn(scores[nextChannelToPlay][notesPlayed[nextChannelToPlay]][2],scores[nextChannelToPlay][notesPlayed[nextChannelToPlay]][3]);
			notesPlayed[nextChannelToPlay]=notesPlayed[nextChannelToPlay]+1;
			if (notesPlayed[nextChannelToPlay]==scoreLengths[nextChannelToPlay]) {
				notesPlayed[nextChannelToPlay]=0;
				int timeDelay=(int)(Math.random()*(maxDelays[nextChannelToPlay]-minDelays[nextChannelToPlay]))+minDelays[nextChannelToPlay];
				for (int i=0;i<scores[nextChannelToPlay].length;i++) {
					scores[nextChannelToPlay][i][0]=scores[nextChannelToPlay][i][0]+timeDelay;
				}
			}
		}
	}

}
}