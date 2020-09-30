package com.jacamars.dsp.rtb.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jacamars.dsp.rtb.bidder.Controller;

/**
 * A class that is used to delete a creative from a campaign.
 * @author David Boulette
 *
 */

public class DeleteCreative extends BasicCommand {
	public DeleteCreative() {
		super();
		cmd = Controller.DELETE_CREATIVE;
		msg = "Delete Creative issued";
	}
	
	/**
	 * Remove a creative from a campaign.
	 * @param to String. The bidder being directed to host the command.
	 * @param owner String. The owner of the campaign.
	 * @param campaign String. The adid of the campaign.
	 * @param creative String. The impid of the creative object to delete.
	 */
	public DeleteCreative(String to, String owner, String campaign, String creative) {
		super(to);
		this.name = campaign;
		this.target = creative;
		cmd = Controller.DELETE_CREATIVE;
		msg = "Delete Creative Issued";
	}
}
