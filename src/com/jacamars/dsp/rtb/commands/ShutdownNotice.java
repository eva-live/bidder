package com.jacamars.dsp.rtb.commands;

import com.jacamars.dsp.rtb.bidder.Controller;

/**
 * A class that implements the shutdown notice sent to ZeroMQ if the bidder is getting ready to exit.
 * @author David Boulette
 *
 */
public class ShutdownNotice extends BasicCommand {
	/**
	 * Empty constructor 
	 */
	public ShutdownNotice() {
		this.cmd = Controller.SHUTDOWNNOTICE;
		msg = "Shutdown notice from this bidder";
		name = "ShutDownNotice";
	}
	
	/**
	 * Send a shutdown notice.
	 * @param who String. The name of the instance sending the message/
	 */
	public ShutdownNotice(String who) {
		this();
		from = who;
	}
	
}
