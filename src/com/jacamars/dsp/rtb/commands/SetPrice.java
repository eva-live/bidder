package com.jacamars.dsp.rtb.commands;

import com.jacamars.dsp.rtb.bidder.Controller;

/**
 * A class that is used to get the price in a creative.
 * @author David Boulette
 *
 */

public class SetPrice extends BasicCommand {
	
	/**
	 * Default constructor
	 */
	public SetPrice() {
		super();
		cmd = Controller.SET_PRICE;
		msg = "Set Price issued";
	}
	
	/**
	 * Set the price in a campaign creative.
	 * @param to String. The bidder that will host this command..
	 * @param campaign String. The campaign adid to set the price on.
	 * @param creative String. The creative impid to set the price on.
	 * @param price double. The new price to assign to the creative.
	 */
	public SetPrice(String to, String campaign, String creative, double price) {
		super(to);
		this.name = campaign;
		this.target = creative;
		this.msg = "Set Price issued";
		this.price = price;
		cmd = Controller.SET_PRICE;
	}
}
