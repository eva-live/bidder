package com.jacamars.dsp.rtb.fraud;

/**
 * Interface for Fraud implementations
 * @author David Boulette
 *
 */
public interface FraudIF {

	/**
	 * The interface that all fraud detection clases use.
	 * @param rt String, record type. Some kind of marker to use for logging, usually 'display'
	 * @param ip String. The string representation of the ip address.
	 * @param url String. The page url.
	 * @param ua String. The user agent.
	 * @param seller String. The seller, usually app.id or site.id.
	 * @param crid String. Not used, deprecated.
	 * @param exchange String. The exchange
	 * @return FraudLog. If this returns null, this is not a bot. If it is bot, returns the log record to use.
	 * @throws Exception
	 */
	public FraudLog bid(String rt, String ip, String url, String ua, String seller, String crid, String exchange) throws Exception;
	
	/**
	 * If you want to bid on an error (like when the underlying service is unavailable, then return true.
	 * @return
	 */
	public boolean bidOnError();
}
