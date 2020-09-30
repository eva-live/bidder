package test.java;

import static org.junit.Assert.*;

import com.jacamars.dsp.rtb.bidder.Controller;
import com.jacamars.dsp.rtb.bidder.ZPublisher;
import com.jacamars.dsp.rtb.commands.AddCampaign;
import com.jacamars.dsp.rtb.commands.BasicCommand;
import com.jacamars.dsp.rtb.commands.DeleteCampaign;
import com.jacamars.dsp.rtb.commands.DeleteCreative;
import com.jacamars.dsp.rtb.commands.Echo;
import com.jacamars.dsp.rtb.commands.LogLevel;
import com.jacamars.dsp.rtb.commands.StartBidder;
import com.jacamars.dsp.rtb.commands.StopBidder;
import com.jacamars.dsp.rtb.common.Configuration;
import com.jacamars.dsp.rtb.common.HttpPostGet;
import com.jacamars.dsp.rtb.jmq.RTopic;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * A class for testing all the redis functions, such as logging, recording bids,
 * etc.
 * 
 * @author David Boulette
 *
 */

public class TestZZZRedis {
	static Controller c;
	public static String test = "";
	public static volatile List<BasicCommand> rcv = new ArrayList();
	static ZPublisher commands;

	static final String id = "JUNIT";

	static CountDownLatch latch;

	@BeforeClass
	public static void testSetup() {
		try {

			Config.setup();
			System.out.println("******************  TestZZZRedis");

			RTopic channel;

			channel =  new RTopic("tcp://localhost:6001&responses");
			channel.addListener(new com.jacamars.dsp.rtb.jmq.MessageListener<BasicCommand>() {
				@Override
				public void onMessage(String channel, BasicCommand cmd) {
					System.out.println("<<<<<<<<<<<<<<<<<" + cmd);
					if (cmd.cmd == Controller.HEARTBEAT || cmd.cmd == Controller.SHUTDOWNNOTICE)								// ignore shutdown
						return;
					rcv.add(cmd);
					System.out.println("COUNT: " + rcv.size());
					if (latch != null)
						latch.countDown();
				}
			});

            commands = new ZPublisher("tcp://localhost:6000&commands");
		} catch (Exception error) {
			error.printStackTrace();
			fail("No connection: " + error.toString());
		}
	}

	@AfterClass
	public static void testCleanup() {
		Config.teardown();
	}

	@Test
	public void testStub() {

	}

	/**
	 * Test adding a campaign
	 */
	@Test
	public void addCampaign() throws Exception {
		AddCampaign e = new AddCampaign("", "ben:payday");
		e.id = "ADDCAMP-ID";
		e.from = "crosstalk";
		Thread.sleep(5000);
		rcv.clear();
		latch = new CountDownLatch(1);
		commands.add(e);
		latch.await(5, TimeUnit.SECONDS);

		BasicCommand r = rcv.get(0);
		assertTrue(r.id.equals("ADDCAMP-ID"));
		assertTrue(r.status.equals("ok"));

	}

	/**
	 * Test deleting a campaign
	 */
	@Test
	public void deleteUnknownCampaign() throws Exception {
		DeleteCampaign e = new DeleteCampaign("", "id123");

		e.id = "DELETECAMP-ID";
		rcv .clear();
		latch = new CountDownLatch(1);
		commands.add(e);
		latch.await(5, TimeUnit.SECONDS);
	}

	/**
	 * Test starting and stopping the rtb bidder engine.
	 * 
	 * @throws Exception
	 *             on Redis errors.
	 */
	@Test
	public void stopStartBidder() throws Exception {
		StopBidder e = new StopBidder();
		e.id = "STOPBIDDER-ID";
		e.from = "crosstalk";
		rcv.clear();
		latch = new CountDownLatch(1);
		commands.add(e);
		latch.await(5, TimeUnit.SECONDS);

		BasicCommand r = rcv.get(0);
		assertTrue(r.msg.equals("stopped"));

		// Now make a bid
		HttpPostGet http = new HttpPostGet();
		String s = Charset.defaultCharset()
				.decode(ByteBuffer.wrap(Files.readAllBytes(Paths.get("./SampleBids/nexage.txt")))).toString();
		long time = 0;
		String str = null;
		try {
			str = http.sendPost("http://" + Config.testHost + "/rtb/bids/nexage", s);
		} catch (Exception error) {
			fail("Network error");
		}
		assertNull(str);
		assertTrue(http.getResponseCode() == 204);

		StartBidder ee = new StartBidder();
		ee.from = "crosstalk";
		ee.id = "STARTBIDDER-ID";

		rcv.clear();
		latch = new CountDownLatch(1);
		commands.add(ee);

		latch.await(5, TimeUnit.SECONDS);
		assertTrue(rcv.size()==1);
		time = System.currentTimeMillis();

		r = rcv.get(0);
		test = r.msg;
		assertTrue(test.equals("running"));
	}

	/**
	 * Test the logging function
	 */
	// @Test
	public void testLog() {

	}
}
