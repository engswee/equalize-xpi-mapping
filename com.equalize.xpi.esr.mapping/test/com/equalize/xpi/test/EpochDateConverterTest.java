package com.equalize.xpi.test;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import com.equalize.xpi.esr.mapping.udf.library.FL_DateTimePool;
import com.sap.aii.mapping.api.StreamTransformationException;

public class EpochDateConverterTest {
	private static FL_DateTimePool lib;
	
	@BeforeClass
	public static void initFunctionLibrary() {
		lib = new FL_DateTimePool();
	}	
	@Test
	public void convertUTCToEpochCase1() throws StreamTransformationException {		
		String inputTimeStamp = "2014-09-22T00:00:00";		
		assertEquals("Error", "1411344000000", lib.convertUTCTimeToEpochMillisecs(inputTimeStamp, null));
	}	
	@Test
	public void convertUTCToEpochCase2() throws StreamTransformationException {
		String inputTimeStamp = "2017-10-01T00:00:00";
		assertEquals("Error", "1506816000000", lib.convertUTCTimeToEpochMillisecs(inputTimeStamp, null));
	}	
	@Test
	public void convertUTCToEpochInfinity() throws StreamTransformationException {
		String inputTimeStamp = "9999-12-31T00:00:00";
		assertEquals("Error", "253402214400000", lib.convertUTCTimeToEpochMillisecs(inputTimeStamp, null));
	}	
	@Test
	public void convertUTCToEpochNegative() throws StreamTransformationException {
		String inputTimeStamp = "1900-01-01T00:00:00";
		assertEquals("Error", "-2208988800000", lib.convertUTCTimeToEpochMillisecs(inputTimeStamp, null));
	}	
	@Test
	public void convertEpochToUTCCase1() throws StreamTransformationException {
		String millisecs = "1411344000000";		
		assertEquals("Error", "2014-09-22T00:00:00", lib.convertEpochMillisecsToUTCTime(millisecs, null));
	}
	@Test
	public void convertEpochToUTCCase2() throws StreamTransformationException {
		String millisecs = "1506816000000";		
		assertEquals("Error", "2017-10-01T00:00:00", lib.convertEpochMillisecsToUTCTime(millisecs, null));
	}
	@Test
	public void convertEpochToUTCInfinity() throws StreamTransformationException {
		String millisecs = "253402214400000";		
		assertEquals("Error", "9999-12-31T00:00:00", lib.convertEpochMillisecsToUTCTime(millisecs, null));
	}
	@Test
	public void convertEpochToUTCNegative() throws StreamTransformationException {
		String millisecs = "-2208988800000";		
		assertEquals("Error", "1900-01-01T00:00:00", lib.convertEpochMillisecsToUTCTime(millisecs, null));
	}
}
