package com.jacamars.dsp.rtb.exchanges;

import java.io.InputStream;

import com.jacamars.dsp.rtb.pojo.BidRequest;

/**
 * A class to handle Atomx ad exchange
 * @author David Boulette
 *
 */

public class Atomx extends BidRequest {
	
	public Atomx() {
		super();
		parseSpecial();
	}
	
	/**
	 * Make a Atomx bid request using a String.
	 * @param in String. The JSON bid request for smartyads
	 * @throws Exception on JSON errors.
	 */
	public Atomx(String  in) throws Exception  {
		super(in);
		parseSpecial();
    }	
	
	/**
	 * Make a Atomx bid request using an input stream.
	 * @param in InputStream. The contents of a HTTP post.
	 * @throws Exception on JSON errors.
	 */
	public Atomx(InputStream in) throws Exception {
		super(in);
		parseSpecial();
	}
	
	/**
	 * Create a new Atomx Exchange object from this class instance.
	 * @throws JsonProcessingException on parse errors.
	 * @throws Exceptionsmartypants on stream reading errors
	 */
	@Override
	public Atomx copy(InputStream in) throws Exception  {
		Atomx copy = new Atomx(in);
		copy.usesEncodedAdm = usesEncodedAdm;
		return copy;
	}
	
	
	/**
	 * Process special Atomx stuff, sets the exchange name.
	 */
	@Override
	public boolean parseSpecial() {
		setExchange( "atomx" );
		usesEncodedAdm = false;
		return true;
	}
}