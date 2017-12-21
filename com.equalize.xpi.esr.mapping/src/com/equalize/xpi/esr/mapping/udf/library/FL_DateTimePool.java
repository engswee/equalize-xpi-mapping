package com.equalize.xpi.esr.mapping.udf.library;

import com.sap.aii.mapping.api.*;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.sap.aii.mappingtool.tf7.rt.*;
import com.sap.aii.mapping.lookup.*;
import java.lang.reflect.*;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import com.sap.ide.esr.tools.mapping.core.LibraryMethod;
import com.sap.ide.esr.tools.mapping.core.ExecutionType;
import com.sap.ide.esr.tools.mapping.core.Argument;
import com.sap.ide.esr.tools.mapping.core.Init;
import com.sap.ide.esr.tools.mapping.core.Cleanup;
import com.sap.ide.esr.tools.mapping.core.Parameter;


public class FL_DateTimePool  {



	@Init(description="") 
	public void init (
		 GlobalContainer container)  throws StreamTransformationException{
		
	}

	@Cleanup 
	public void cleanup (
		 GlobalContainer container)  throws StreamTransformationException{
		
	}

	@LibraryMethod(title="convertTimeZone", description="Convert input time from one timezone to another", category="FL_DateTime", type=ExecutionType.SINGLE_VALUE) 
	public String convertTimeZone (
		@Argument(title="Input timestamp")  String timestamp,
		@Parameter(title="Timestamp format")  String format,
		@Parameter(title="From timezone")  String fromTZ,
		@Parameter(title="To timezone")  String toTZ,
		 Container container)  throws StreamTransformationException{
			// ----------------------------------------------------------
		// Convert input time from one timezone to another		
		// - utilizes Joda Time libraries
		// ----------------------------------------------------------
		// timestamp - input timestamp
		// format - pattern format of input and output timestamp similar allowed patterns below
		//        - http://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
		// fromTZ - from timezone
		// toTZ   - to timezone
		//        - expected timezone in long format timezone ids listed in following site
		//        - http://en.wikipedia.org/wiki/List_of_tz_database_time_zones
		// ----------------------------------------------------------
		
		DateTimeFormatter formatter = DateTimeFormat.forPattern(format);
		DateTimeZone originalTZ = DateTimeZone.forID(fromTZ);
		DateTime fromDateTime = new DateTime(DateTime.parse(timestamp, formatter), originalTZ);
		DateTime toDateTime = fromDateTime.withZone(DateTimeZone.forID(toTZ));
		return formatter.print(toDateTime);
	}
	
	@LibraryMethod(title="convertUTCTimeToEpochMillisecs", description="Convert UTC time to milliseconds from Epoch (1 Jan 1970)", category="FL_DateTime", type=ExecutionType.SINGLE_VALUE) 
	public String convertUTCTimeToEpochMillisecs(
			@Argument(title="Input timestamp") String timestamp,
			Container container) throws StreamTransformationException{
		try {
			// Sample input timestamps
			// 1900-01-01T00:00:00
			// 2014-09-22T00:00:00
			// 2017-10-01T00:00:00
			// 9999-12-31T00:00:00
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
			Date inputDate = sdf.parse(timestamp + "+0000"); // Include UTC indicator
			// Retrieve milliseconds since January 1, 1970, 00:00:00 GMT which represents Epoch time
			long millisecsSinceEpoch = inputDate.getTime();
			return Long.toString(millisecsSinceEpoch);
		} catch (ParseException e) {
			throw new StreamTransformationException(e.getMessage());
		}
	}

	@LibraryMethod(title="convertEpochMillisecsToUTCTime", description="Convert milliseconds from Epoch to UTC time", category="FL_DateTime", type=ExecutionType.SINGLE_VALUE) 
	public String convertEpochMillisecsToUTCTime(
			@Argument(title="Input milliseconds since Epoch") String millisecsSinceEpoch,
			Container container) throws StreamTransformationException{
		Date date = new Date(Long.parseLong(millisecsSinceEpoch));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		return sdf.format(date);
	}
}