package org.example;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class TestTimeStampUtils {

	String[] loginTimes = { 
			"2015-04-30T15:48:34.000Z",
			"2015-03-26T20:38:55.000Z", 
			"2015-03-26T20:37:42.000Z" };

	long[] expectedTimes = { 1430426914, 1427420335, 1427420262 };
	
	@Before
	public void setUp() throws Exception {
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testISO8609ForCurrent() {
		Date now = new Date();
		String timeStamp = TimestampUtils.getISO8601StringForCurrentDate();
		assertTrue(timeStamp.startsWith(String.valueOf(now.getYear() + 1900)));
	}

	@Test
	public void testISO8609ToUnixTime() {
		int idx = 0;
		long unixtime;
		for (String timeStamp : loginTimes) {
			unixtime = TimestampUtils.iso8601StringToUnixTime(timeStamp);
			assertEquals(expectedTimes[idx], unixtime);
			idx++;
		}
	}

}