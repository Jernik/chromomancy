package Talking;

import java.lang.Runnable;

/**
* Tree structure used to hold dialogue.
*/
public class Talk {

	/**
	* Root output phrase
	*/
	public String talk;
	/**
	* List of input options
	*/
	public String[] options;
	/**
	* List of responses corresponding to input options.
	*/
	public Talk[] responses;
	/**
	* The method that should be run when this talk object is executed.
	* By default this does nothing.
	*/
	public Runnable method=new Runnable() {
		public void run() {}
	};

	/**
	* Constructor.
	* @param talk Root output phrase
	* @param options List of input options
	* @param responses List of responses correponding to input options.
	*/
	public Talk(String talk,String[] options,Talk[] responses) {
		this.talk=talk;
		this.options=options;
		this.responses=responses;
	}

}