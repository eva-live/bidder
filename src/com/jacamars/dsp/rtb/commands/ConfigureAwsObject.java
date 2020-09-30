package com.jacamars.dsp.rtb.commands;

import com.jacamars.dsp.rtb.bidder.Controller;
import com.jacamars.dsp.rtb.common.Campaign;

/**
 * A class that is used to encapsulate a 0MQ command for adding a campaign to the bidder.
 * Jackson will be used to create the structure.
 * @author David Boulette
 *
 */
public class ConfigureAwsObject extends BasicCommand {
			
	/**
	 * Empty constructor for Jackson
	 */
	public ConfigureAwsObject() {
		super();
		cmd = Controller.CONFIGURE_AWS_OBJECT;
		msg = "An AWS object is being configured in the system";
	}

	/**
	 * Configure an AWS Object.
	 * @param to String. The bidder that will execute the command.
	 * @param name String. The name of the owner of the campaign.
	 * @param target String. The command to execute.
	 */
	public ConfigureAwsObject(String to, String name, String target) {
		super(to);
		cmd = Controller.CONFIGURE_AWS_OBJECT;
		status = "ok";
		this.target = target;
		msg = "An AWS OBJECT is befing configured: " + name +"/" + target;
	}
}
