package com.equalize.xpi.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import com.equalize.xpi.esr.mapping.udf.library.FL_DateTimePool;
import com.sap.aii.mapping.api.StreamTransformationException;
public class EpochDateConverterTest {

	@Test
	public void convertTimestampToEpochCase1() throws StreamTransformationException {
		String inputTimeStamp = "2014-09-22T00:00:00";
		FL_DateTimePool lib = new FL_DateTimePool();
		assertEquals("Error", "1411344000000", lib.convertTimestampToEpochTime(inputTimeStamp, null));
	}
	
	@Test
	public void convertTimestampToEpochCase2() throws StreamTransformationException {
		String inputTimeStamp = "2017-10-01T00:00:00";
		FL_DateTimePool lib = new FL_DateTimePool();
		assertEquals("Error", "1506816000000", lib.convertTimestampToEpochTime(inputTimeStamp, null));
	}
	
	@Test
	public void convertTimestampToEpochInfinity() throws StreamTransformationException {
		String inputTimeStamp = "9999-12-31T00:00:00";
		FL_DateTimePool lib = new FL_DateTimePool();
		assertEquals("Error", "253402214400000", lib.convertTimestampToEpochTime(inputTimeStamp, null));
	}
	
	@Test
	public void convertTimestampToEpochNegative() throws StreamTransformationException {
		String inputTimeStamp = "1900-01-01T00:00:00";
		FL_DateTimePool lib = new FL_DateTimePool();
		assertEquals("Error", "-2208988800000", lib.convertTimestampToEpochTime(inputTimeStamp, null));
	}
}
