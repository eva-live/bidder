package test.java;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import com.jacamars.dsp.rtb.bidder.DeadmanSwitch;
import com.jacamars.dsp.rtb.common.Configuration;
import com.jacamars.dsp.rtb.redisson.RedissonClient;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests whether the bidders will stop if the accounting deadman switch is deleted in Redis
 * @author David Boulette
 *
 */
public class TestDeadmanSwitch {

    static RedissonClient redisson;

    @BeforeClass
    public static void setup() {
        try {
            Config.setup();
            redisson = Configuration.getInstance().redisson;
            System.out.println("******************  TestFreqCapLogic");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Shut the RTB server down.
     */
    @AfterClass
    public static void testCleanup() {
        Config.teardown();
        System.out.println("We are done!");
    }
	@Test 
	public void testSwitch() throws Exception {

			DeadmanSwitch.testmode = true;
			
			redisson.del("deadmanswitch");
			
			DeadmanSwitch d = new DeadmanSwitch(redisson,"deadmanswitch");
			Thread.sleep(1000);
			assertFalse(d.canRun());
			redisson.set("deadmanswitch", "ready",5);
			
			assertTrue(d.canRun());
			Thread.sleep(10000);
			assertFalse(d.canRun());
		}
	
	@Test
	public void testDelayedExpire() throws Exception {

			
			redisson.del("xxx");
			Map m = new HashMap();
			m.put("Ben", 1);
			m.put("Peter", 2);
			redisson.hmset("xxx", m);
			
			m = redisson.hgetAll("xxx");
			assertNotNull(m);
			Number n = (Number)m.get("Ben");
			assertNotNull(n);
			
			redisson.expire("xxx", 2);
			
			m = redisson.hgetAll("xxx");
			assertNotNull(m);
			n = (Number)m.get("Ben");
			assertNotNull(n);
			
			Thread.sleep(5000);
			
			m = redisson.hgetAll("xxx");
			assertNull(m);
			
	}
}
