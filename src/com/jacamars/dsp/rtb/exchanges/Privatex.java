package com.jacamars.dsp.rtb.exchanges;

import java.io.IOException;

import java.io.InputStream;

import com.jacamars.dsp.rtb.pojo.BidRequest;

/**
 * Implements the PrivateExchange processor
 * @author Ben M. Faul
 *
 */
public class Privatex extends BidRequest {
	
	/**
	 * Make a default constructor, the bidder keeps a representative class instance for each
	 * exchange so it can use a Map to make new bid requests per the format of the bid request.
	 */
	public Privatex() {
		super();
		parseSpecial();
	}
	
	/**
	 * Constructs Private exhange bid request from a file containoing JSON
	 * @param in. String - the File name containing the data.
	 * @throws JsonProcessingException on parse errors.
	 * @throws IOException on file reading errors.
	 */	
	public Privatex(String  in) throws Exception  {
		super(in);
		parseSpecial();
    }	
	
	/**
	 * Constructs Private Exchange bid request from JSON stream in jetty.
	 * @param in. InputStream - the JSON data coming from HTTP.
	 * @throws JsonProcessingException on parse errors.
	 * @throws IOException on file reading errors.
	 */
	public Privatex(InputStream in) throws Exception {
		super(in);
		parseSpecial();
	}
	/**
	 * Create a new Private Exchange object from this class instance.
	 * @throws JsonProcessingException on parse errors.
	 * @throws Exception on stream reading errors
	 */
	@Override
	public Privatex copy(InputStream in) throws Exception  {
		Privatex copy = new Privatex(in);
		copy.usesEncodedAdm = usesEncodedAdm;
		return copy;
	}
	
	/**
	 * Process special Nexage stuff, sets the exchange name.
	 */
	@Override
	public boolean parseSpecial() {
		setExchange( "privatex" );
		return true;
	}
	
}
