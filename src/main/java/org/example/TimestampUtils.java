package org.example;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Methods for dealing with timestamps
 */
public class TimestampUtils {

	/**
	 * Return an ISO 8601 combined date and time string for current date/time
	 * 
	 * @return String with format "yyyy-MM-dd'T'HH:mm:ss'Z'"
	 */
	public static String getISO8601StringForCurrentDate() {
		Date now = new Date();
		return getISO8601StringForDate(now);
	}
	
	/**
	 * ISO 8601/RFC 3339 represents timezone information a little bit
	 * differently than what is expected by . For
	 * example the Google Directory REST API returns timestamps in the form of:
	 * {@code 2015-04-30T15:48:34.000Z}. Convert these to a UNIX timestamp
	 * (number of seconds from the epoch). Where the Unix Epoch started January
	 * 1st, 1970 at UTC.
	 * 
	 * @see <a href="http://www.unixtimestamp.com/">Epoch Unix Time Stamp
	 *      Converter</a>
	 * 
	 * @param timeStamp
	 *             in the format of
	 *            {@code 2015-04-30T15:48:34.000Z}
	 * @return the number of seconds from the epoch
	 */
	public static long iso8601StringToUnixTime(String timeStamp) {
		long unixTime = -1;
		TimeZone tzGMT = TimeZone.getTimeZone("GMT");
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		format.setTimeZone(tzGMT);
		try {
			// Reminder: getTime returns the number of milliseconds from the
			// Unix epoch.
			unixTime = format.parse(timeStamp).getTime() / 1000;
		} catch (ParseException e) {
			// Return initialized value of -1;
		}
		return unixTime;
	}

	/**
	 * Return an ISO 8601 combined date and time string for specified date/time
	 * 
	 * @param date
	 *            Date
	 * @return String with format "yyyy-MM-dd'T'HH:mm:ss'Z'"
	 */
	private static String getISO8601StringForDate(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		return dateFormat.format(date);
	}

	/**
	 * Private constructor: class cannot be instantiated
	 */
	private TimestampUtils() {
	}
}