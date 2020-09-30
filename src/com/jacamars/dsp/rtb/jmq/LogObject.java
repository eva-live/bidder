package com.jacamars.dsp.rtb.jmq;


/** 
 * A simple class to hold the channel info, the name is the channel. The content is the object to log (in this case JSON)
 * @author David Boulette
 *
 */
public class LogObject {
	public String name;
	public String content;

	public LogObject(String name, String content) {
		this.name = name;
		this.content = content;
	}
}