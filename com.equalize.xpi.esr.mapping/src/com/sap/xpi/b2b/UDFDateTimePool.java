package com.sap.xpi.b2b;

import com.sap.aii.mapping.api.*;
import com.sap.aii.mapping.lookup.*;
import com.sap.aii.mappingtool.tf7.rt.*;
import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import java.text.*;
import com.sap.ide.esr.tools.mapping.core.LibraryMethod;
import com.sap.ide.esr.tools.mapping.core.ExecutionType;
import com.sap.ide.esr.tools.mapping.core.Argument;
import com.sap.ide.esr.tools.mapping.core.Init;
import com.sap.ide.esr.tools.mapping.core.Cleanup;


public class UDFDateTimePool  {




	private static final String MODE_DATE_FROM = "dateFrom";
	private static final String MODE_DATE_TO = "dateTo";
	private static final String MODE_DATE = "date";
	private static final String MODE_TIME = "time";

	private static final String[] MODES = new String[]{MODE_DATE_FROM,
			MODE_DATE_TO, MODE_DATE, MODE_TIME};
	private static final List MODE_LIST = Arrays.asList(MODES);
	private Locale locale = Locale.getDefault();
	
	private AbstractTrace trace = null;


    private String getDate(String date, String dateFormat, String mode)
	    throws NumberFormatException {
	if (!MODE_LIST.contains(mode)) {
	    throw new IllegalArgumentException("unvalid mode " + mode);
	}

	String result = null;
	String mydate = date.trim();

	// boolean modeIsDateFrom = modus.equalsIgnoreCase(MODE_DATE_FROM);
	boolean modeIsDateTo = mode.equalsIgnoreCase(MODE_DATE_TO);
	// boolean modeIsDate = modus.equalsIgnoreCase(MODE_DATE);
	boolean modeIsTime = mode.equalsIgnoreCase(MODE_TIME);

	int dtmFormat = Integer.parseInt(dateFormat);
	switch (dtmFormat) {
        	case 101: { // YYMMDD
        	    if (!modeIsTime) {
        		result = "20" + mydate;
        	    }
        	    break;
        	}
        	case 102: { // CCYYMMDD
        	    if (!modeIsTime) {
        		result = mydate;
        	    }
        	    break;
        	}
        	case 201: {// YYMMDDHHMM
        	    if (modeIsTime) {
        		result = mydate.substring(6);
        	    } else {
        		result = "20" + mydate.substring(0, 6);
        	    }
        	    break;
        	}
        	case 202: { // YYMMDDHHMMSS
        	    if (modeIsTime) {
        		result = mydate.substring(6);
        	    } else {
        		result = "20" + mydate.substring(0, 6);
        	    }
        	    break;
        	}
        	case 203: { // CCYYMMDDHHMM
        	    if (modeIsTime) {
        		result = mydate.substring(8);
        	    } else {
        		result = mydate.substring(0, 8);
        	    }
        	    break;
        	}
        	case 204: {// CCYYMMDDHHMMSS
        	    if (modeIsTime) {
        		result = mydate.substring(8);
        	    } else {
        		result = mydate.substring(0, 8);
        	    }
        	    break;
        	}
        	case 609: { // YYMM
        	    if (modeIsDateTo) {
        		String year = mydate.substring(0, 2);
        		String month = mydate.substring(2, 4);
        		DateFormat dfMonth = createFormatter("yyMM");
        		result = getLastDayOfMonth(year, month, dfMonth);
        	    } else {
        		if (!modeIsTime) {
        		    result = "20" + mydate.substring(0, 4) + "01";
        		}
        	    }
        	    break;
        	}
        	case 610: {// CCYYMM
        	    if (modeIsDateTo) {
        		String year = mydate.substring(0, 4);
        		String month = mydate.substring(4, 6);
        		DateFormat dfMonth = createFormatter("yyyyMM");
        		result = getLastDayOfMonth(year, month, dfMonth);
        	    } else {
        		if (!modeIsTime) {
        		    result = mydate.substring(0, 6) + "01";
        		}
        	    }
        	    break;
        	}
        	case 615: { // YYWW
        	    String year = mydate.substring(0, 2);
        	    String week = mydate.substring(2, 4);
        	    DateFormat dfWeek = createFormatter("yyww");
        	    if (modeIsDateTo) {
        		result = getFridayOfWeek(year, week, dfWeek);
        	    } else {
        		if (!modeIsTime) {
        		    result = getMondayOfWeek(year, week, dfWeek);
        		}
        	    }
        	    break;
        	}
        	case 616: {// CCYYWW
        	    String year = mydate.substring(0, 4);
        	    String week = mydate.substring(4, 6);
        	    DateFormat dfWeek = createFormatter("yyyyww");
        	    if (modeIsDateTo) {
        		result = getFridayOfWeek(year, week, dfWeek);
        	    } else {
        		if (!modeIsTime) {
        		    result = getMondayOfWeek(year, week, dfWeek);
        		}
        	    }
        	    break;
        	}
        	case 715: { // YYWW-YYWW without hyphen
        	    String year = null;
        	    String week = null;
        	    DateFormat dfWeek = createFormatter("yyww");
        	    if (modeIsDateTo) {
        		year = mydate.substring(4, 6);
        		week = mydate.substring(6, 8);
        		result = getFridayOfWeek(year, week, dfWeek);
        	    } else {
        		if (!modeIsTime) {
        		    year = mydate.substring(0, 2);
        		    week = mydate.substring(2, 4);
        		    result = getMondayOfWeek(year, week, dfWeek);
        		}
        	    }
        	    break;
        	}
        	case 716: { // CCYYWW-CCYYWW without hyphen
        	    String year = null;
        	    String week = null;
        	    DateFormat dfWeek = createFormatter("yyyyww");
        	    if (modeIsDateTo) {
        		year = mydate.substring(6, 10);
        		week = mydate.substring(10, 12);
        		result = getFridayOfWeek(year, week, dfWeek);
        	    } else {
        		if (!modeIsTime) {
        		    year = mydate.substring(0, 4);
        		    week = mydate.substring(4, 6);
        		    result = getMondayOfWeek(year, week, dfWeek);
        		}
        	    }
        	    break;
        	}
        	default: {
        	    throw new RuntimeException("Unexpected date qualifier "
        		    + dateFormat);
        	}
	}

    if (!modeIsTime) {
	    DateFormat formatter = createFormatter("yyyyMMdd");
	    try {
		    formatter.parse(result);
	    } catch (Exception ex) {
		    throw new RuntimeException("FunctionLib UDFDateTimePool: "
			       + " cannot date string " + date + " of format "
			       + dateFormat);
	    }
	}

	return result;
    }

	private String getMondayOfWeek(String year, String week, DateFormat dfWeek) {
		Date date = null;
		Calendar calendar = null;
		try {
			date = dfWeek.parse(year + week);
			calendar = createCalendar();
			calendar.setTimeInMillis(date.getTime());
		} catch (Exception ex) {
			calendar = getCalendarByYearAndWeek(year, week);
		}

		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		DateFormat df = createFormatter("yyyyMMdd");
		return df.format(calendar.getTime());
	}

	private String getFridayOfWeek(String year, String week, DateFormat dfWeek) {
		Date date = null;
		Calendar calendar = null;
		try {
			date = dfWeek.parse(year + week);
			calendar = createCalendar();
			calendar.setTimeInMillis(date.getTime());
		} catch (Exception ex) {
			calendar = getCalendarByYearAndWeek(year, week);
		}

		calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		DateFormat df = createFormatter("yyyyMMdd");
		return df.format(calendar.getTime());
	}

	private String getLastDayOfMonth(String year, String month,
			DateFormat dfMonth) {
		Date date = null;
		try {
			date = dfMonth.parse(year + month);
		} catch (Exception ex) {
			throw new RuntimeException("FunctionLib UDFDateTimePool: "
					+ " cannot parse year/month " + year + "/" + month);
		}

		Calendar calendar = createCalendar();
		calendar.setTimeInMillis(date.getTime());
		calendar.set(Calendar.DAY_OF_MONTH, calendar
				.getActualMaximum(Calendar.DAY_OF_MONTH));
		DateFormat df = createFormatter("yyyyMMdd");
		return df.format(calendar.getTime());
	}

	private DateFormat createFormatter(String pattern) {
		DateFormat formatter = new SimpleDateFormat(pattern, locale);
		formatter.setLenient(false);
		return formatter;
	}

	private Calendar createCalendar() {
		Calendar calendar = Calendar.getInstance(locale);
		return calendar;
	}
	
	private Calendar getCalendarByYearAndWeek(String year, String week) {
		try {
			int yearInt = Integer.parseInt(year);
			if (yearInt < 100) {
				yearInt = 2000 + yearInt;
			}
			Calendar calendar = createCalendar();
			calendar.setLenient(false);
			calendar.set(Calendar.YEAR, yearInt);
			calendar.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(week));
			calendar.setLenient(true);
			return calendar;
		} catch (Exception ex1) {
			throw new RuntimeException("FunctionLib UDFDateTimePool: "
					+ "getDateByYearAndWeek cannot parse integer " 
					+ year + "/" + week);
		}
	}


	@Init(description="") 
	public void init (
		 GlobalContainer container)  throws StreamTransformationException{
		
//trace = container.getTrace();

// set the local manually if necessary
//locale = Locale.getDefault();
//locale = Locale.GERMANY;

// here you can debug the calendar propeties
//trace.addInfo("FunctionLib UDFDateTimePool Locale: " + locale);
//Calendar calendar = Calendar.getInstance(locale);
//trace.addInfo("FunctionLib UDFDateTimePool MinimalDaysInFirstWeek: " + calendar.getMinimalDaysInFirstWeek());
//trace.addInfo("FunctionLib UDFDateTimePool  FirstDayOfWeek: " + calendar.getFirstDayOfWeek());
	}

	@Cleanup 
	public void cleanup (
		 GlobalContainer container)  throws StreamTransformationException{
		
	}

	@LibraryMethod(title="getDate", description="Creates a YYYYMMDD date from the first argument using the second argument (an EDIFACT-F2379-conform qualifier). The third argument has one of the values constantDate, constantDateFrom or constantDateTo provided by this function library", category="UDFDateTimePool", type=ExecutionType.ALL_VALUES_OF_CONTEXT) 
	public void getDate (
		@Argument(title="")  String[] dateValues,
		@Argument(title="")  String[] dateFormatValues,
		@Argument(title="")  String[] mode,
		 ResultList result,
		 Container container)  throws StreamTransformationException{
		
		// Execution type: all values of a context
		if (dateValues != null && dateValues.length > 0) {
			if (dateFormatValues != null
					&& dateValues.length != dateFormatValues.length) {
				throw new IllegalStateException(
						"UDF getDate: dateArr and dateFormatArr have"
								+ " different lenght");
			}

			try {
				for (int i = 0; i < dateValues.length; i++) {
					String dateValue = dateValues[i];
					if (dateValue == null
							|| ResultList.SUPPRESS.equals(dateValue)
							|| dateValue.trim().length() == 0
							|| "0".equals(dateValue)) {
						result.addSuppress();
					} else {
						String dateFormat = dateFormatValues[i];
						String date = null;
						try {
							date = getDate(dateValue, dateFormat, mode[0]);
							if (date == null) {
								date = ResultList.SUPPRESS;
							}
							result.addValue(date);
						} catch (RuntimeException re) {
							//date = ResultList.SUPPRESS;
							throw new RuntimeException("UDF dateValue " + dateValue
									+ ", dateFormat " + dateFormat + " causes "
									+ re);
						}
					}
				}
			} catch (Exception ex) {
				throw new RuntimeException("getDate " + ex, ex);
			}
		}
	}

	@LibraryMethod(title="getTime", description="Creates a HHMM time value from the first argument using the second argument (an EDIFACT-F2379-conform qualifier). If the third argument has value TRUE the time format is HHMMSS", category="UDFDateTimePool", type=ExecutionType.ALL_VALUES_OF_CONTEXT) 
	public void getTime (
		@Argument(title="")  String[] dateValues,
		@Argument(title="")  String[] dateFormatValues,
		@Argument(title="")  String[] longFormatValues,
		 ResultList result,
		 Container container)  throws StreamTransformationException{
		
		// Execution type: all values of a context
		if (dateValues != null && dateValues.length > 0) {
			if (dateFormatValues != null
					&& dateValues.length != dateFormatValues.length) {
				throw new IllegalStateException(
						"UDF getTime: dateValues array and dateFormatValues array have"
								+ " different lenght");
			}

			// long format: seconds inclusive
			boolean longFormat = Boolean.parseBoolean(longFormatValues[0]);
			try {
				for (int i = 0; i < dateValues.length; i++) {
					String dateValue = dateValues[i];
					if (dateValue == null
							|| ResultList.SUPPRESS.equals(dateValue)
							|| dateValue.trim().length() == 0
							|| "0".equals(dateValue)) {
						result.addSuppress();
					} else {
						String dateFormat = dateFormatValues[i];
						String date = null;
						try {
							date = getDate(dateValue, dateFormat, MODE_TIME);
							if (date == null) {
								date = ResultList.SUPPRESS;
							} else {
								int dateLength = date.length();
								if (!longFormat && dateLength > 4) {
									date = date.substring(0, 4);
								} else if (!longFormat && dateLength < 4) {
									date = date + "0000";
									date = date.substring(0, 4);
								} else if (longFormat && dateLength < 6) {
									date = date + "000000";
									date = date.substring(0, 6);
								} else if (longFormat && dateLength > 6) {
									date = date.substring(0, 6);
								}
							}
							result.addValue(date);
						} catch (RuntimeException re) {
							//date = ResultList.SUPPRESS;
							throw new RuntimeException("UDF dateValue " + dateValue
									+ ", dateFormat " + dateFormat + "causes "
									+ re);
						}
					}
				}
			} catch (Exception ex) {
				throw new RuntimeException("getTime " + ex, ex);
			}
		}
	}

	@LibraryMethod(title="constantDate", description="Use this generated constant as an input for UDF getDate: indicates a single date value", category="UDFDateTimePool", type=ExecutionType.SINGLE_VALUE) 
	public String constantDate (
		 Container container)  throws StreamTransformationException{
		return MODE_DATE;
	}

	@LibraryMethod(title="constantDateFrom", description="Use this generated constant as an input for UDF getDate: indicates the begin of a time period", category="UDFDateTimePool", type=ExecutionType.SINGLE_VALUE) 
	public String constantDateFrom (
		 Container container)  throws StreamTransformationException{
		return MODE_DATE_FROM;
	}

	@LibraryMethod(title="constantDateTo", description="Use this generated constant as an input for UDF getDate: indicates the end of a time period", category="UDFDateTimePool", type=ExecutionType.SINGLE_VALUE) 
	public String constantDateTo (
		 Container container)  throws StreamTransformationException{
		return MODE_DATE_TO;
	}

	@LibraryMethod(title="getDateDifference", description="Gets the number of days between two dates of YYYYMMDD format. The first argument marks the begin of the time intervall.", category="UDFDateTimePool", type=ExecutionType.SINGLE_VALUE) 
	public String getDateDifference (
		@Argument(title="")  String dateBegin,
		@Argument(title="")  String dateEnd,
		 Container container)  throws StreamTransformationException{
		
		DateFormat formatter = createFormatter("yyyyMMdd");
		formatter.setLenient(false);

		Date date1 = null;
		Date date2 = null;
		try {
			date1 = formatter.parse(dateBegin);
		} catch (ParseException pe) {
			if (dateBegin.trim().length() == 0) {
				return ResultList.SUPPRESS;
			} else {
			throw new RuntimeException("UDF getDateDifference: "
					+ " cannot parse dateBegin " + dateBegin);
			}
		}
		try {
			date2 = formatter.parse(dateEnd);
		} catch (ParseException pe) {
			if (dateEnd.trim().length() == 0) {
				return ResultList.SUPPRESS;
			} else {
			throw new RuntimeException("UDF getDateDifference: "
					+ " cannot parse dateBegin " + dateEnd);
			}
		}

		long diff = date2.getTime() - date1.getTime();
		return (diff / 86400000) + "";
	}

	@LibraryMethod(title="getMonthDifference", description="Gets the number of months between two dates of YYYYMMDD format. Dates wizhin a calendar month yields a 0. The first argument marks the begin of the time intervall.", category="UDFDateTimePool", type=ExecutionType.SINGLE_VALUE) 
	public String getMonthDifference (
		@Argument(title="")  String dateBegin,
		@Argument(title="")  String dateEnd,
		 Container container)  throws StreamTransformationException{
		
		DateFormat formatter = createFormatter("yyyyMMdd");

		Date date1 = null;
		Date date2 = null;
		try {
			date1 = formatter.parse(dateBegin);
		} catch (ParseException pe) {
			if (dateBegin.trim().length() == 0) {
				return ResultList.SUPPRESS;
			} else {
			throw new RuntimeException("UDF getMonthDifference: "
					+ " cannot parse dateBegin " + dateBegin);
			}
		}
		try {
			date2 = formatter.parse(dateEnd);
		} catch (java.text.ParseException pe) {
			if (dateEnd.trim().length() == 0) {
				return ResultList.SUPPRESS;
			} else {
			throw new RuntimeException("UDF getMonthDifference: "
					+ " cannot parse dateBegin " + dateEnd);
			}
		}

		Calendar calendar1 =	createCalendar();
		calendar1.setTimeInMillis(date1.getTime());
		Calendar calendar2 = createCalendar();
		calendar2.setTimeInMillis(date2.getTime());

		int yearDiff = calendar2.get(Calendar.YEAR)
				- calendar1.get(Calendar.YEAR);
		int monthEnd = calendar2.get(Calendar.MONTH);
		int monthBegin = calendar1.get(Calendar.MONTH);
		if (yearDiff != 0) {
			monthEnd = monthEnd + 12 * yearDiff;
		}

		return (monthEnd - monthBegin) + "";
	}

	@LibraryMethod(title="getDateAfterDays", description="Gets the date after the date of the first argument (YYYYMMDD) by adding a number of days contained in the second argument", category="UDFDateTimePool", type=ExecutionType.SINGLE_VALUE) 
	public String getDateAfterDays (
		@Argument(title="")  String date,
		@Argument(title="")  String numberOfDays,
		 Container container)  throws StreamTransformationException{
		
		DateFormat formatter = createFormatter("yyyyMMdd");
		Calendar calendar = createCalendar();

		Date aDate;
		int dayCount;
		try {
			aDate = formatter.parse(date);
		} catch (ParseException pe) {
			if (date.trim().length() == 0) {
				return ResultList.SUPPRESS;
			} else {
			throw new RuntimeException("UDF getDateAfterDays: "
					+ " cannot parse date " + date);
			}
		}
		try {
			dayCount = Integer.parseInt(numberOfDays);
		} catch (NumberFormatException pe) {
			throw new RuntimeException("UDF getDateAfterDays: "
					+ " cannot parse int " + numberOfDays);
		}

		calendar.setTimeInMillis(aDate.getTime());
		calendar.add(Calendar.DAY_OF_MONTH, dayCount);

		return formatter.format(calendar.getTime());
	}

	@LibraryMethod(title="getFirstOfMonthAfterMonths", description="Gets the first of month date after the date of the first argument (YYYYMMDD) by adding a number of months contained in the second argument", category="UDFDateTimePool", type=ExecutionType.SINGLE_VALUE) 
	public String getFirstOfMonthAfterMonths (
		@Argument(title="")  String date,
		@Argument(title="")  String numberOfMonths,
		 Container container)  throws StreamTransformationException{
		
		DateFormat formatter = createFormatter("yyyyMMdd");
		Calendar calendar = createCalendar();

		Date aDate;
		int monthCount;
		try {
			aDate = formatter.parse(date);
		} catch (ParseException pe) {
			if (date.trim().length() == 0) {
				return ResultList.SUPPRESS;
			} else {
			throw new RuntimeException("UDF getFirstOfMonthAfterMonths: "
					+ " cannot parse dateBegin " + date);
			}
		}
		try {
			monthCount = Integer.parseInt(numberOfMonths);
		} catch (NumberFormatException pe) {
			throw new RuntimeException("UDF getFirstOfMonthAfterMonths: "
					+ " cannot parse int " + numberOfMonths);
		}

		calendar.setTimeInMillis(aDate.getTime());
		calendar.add(Calendar.MONTH, monthCount);
		calendar.set(Calendar.DAY_OF_MONTH, 1);

		return formatter.format(calendar.getTime());
	}

	@LibraryMethod(title="getDateFormat", description="Returns an EDIFACT-F2379-conform qualifier describing the dates format.", category="UDFDateTimePool", type=ExecutionType.SINGLE_VALUE) 
	public String getDateFormat (
		@Argument(title="")  String date,
		 Container container)  throws StreamTransformationException{
		
		String dtmFormat = ResultList.SUPPRESS;
		String dateString = date != null ? date.trim() : null;
		if (date != null && dateString.length() > 0) {
			dtmFormat = "";
			int dateLength = date.length();
			switch (dateLength) {
				case 6 : {
					dtmFormat = "101";
					break;
				}
				case 8 : {
					dtmFormat = "102";
					break;
				}
				case 10 : {
					dtmFormat = "201";
					break;
				}
				case 12 : {
					String yearBegin = date.substring(0, 2);
					if ("20".equalsIgnoreCase(yearBegin)
							|| "19".equalsIgnoreCase(yearBegin)) {
						dtmFormat = "203";
					} else {
						dtmFormat = "202";
					}
					break;
				}
				case 14 : {
					dtmFormat = "204";
					break;
				}
				default : {
					throw new IllegalArgumentException("getDateFormat: " +
							"the date length " + dateLength +
							" cannot be processed");
				}
			}
		}
		
		return dtmFormat;
	}

	@LibraryMethod(title="getStartDateOfTimePeriod", description="Generates the first day of the week (Argument 2: 'W') or month ('M') for a given date in Argument 1; returns Argument 1 when Argument 2 is 'D'", category="UDFDateTimePool", type=ExecutionType.SINGLE_VALUE) 
	public String getStartDateOfTimePeriod (
		@Argument(title="")  String dateString,
		@Argument(title="")  String timingQualf,
		 Container container)  throws StreamTransformationException{
		
	String resultString = null;
	DateFormat dfmt = createFormatter("yyyyMMdd");

	String newDateString = dateString.trim();
	int length = newDateString.length();
	if (length == 6) {
	    newDateString = "20" + newDateString;
	} else if (length > 8) {
	    newDateString = newDateString.substring(0, 8);
	}

	Date date = null;
	try {
	    date = dfmt.parse(newDateString);
	} catch (Exception e) {
	    throw new RuntimeException(
		    "UDF getStartDateOfTimePeriod: Could not parse Date "
			    + newDateString);
	}

	if (timingQualf.equals("D")) {
	    resultString = newDateString;
	} else {
	    Calendar calendar = createCalendar();
	    calendar.setTime(date);
	    if (timingQualf.equals("W")) { // weekly
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
	    } else if (timingQualf.equals("M")) { // monthly
		calendar.set(Calendar.DAY_OF_MONTH, 1);
	    } else {
		throw new RuntimeException(
			"UDF getEndDateOfTimePeriod: unexspected time period qualifier"
				+ timingQualf);
	    }
	    resultString = dfmt.format(calendar.getTime());
	}

	return resultString;
	}

	@LibraryMethod(title="getEndDateOfTimePeriod", description="Generates the last day (Friday) of the week (Argument 2: 'W') or month ('M') for a given date in Argument 1; returns Argument 1 when Argument 2 is 'D'.", category="UDFDateTimePool", type=ExecutionType.SINGLE_VALUE) 
	public String getEndDateOfTimePeriod (
		@Argument(title="")  String dateString,
		@Argument(title="")  String timingQualf,
		 Container container)  throws StreamTransformationException{
		
	String resultString = null;
	DateFormat dfmt = createFormatter("yyyyMMdd");

	String newDateString = dateString.trim();
	int length = newDateString.length();
	if (length == 6) {
	    newDateString = "20" + newDateString;
	} else if (length > 8) {
	    newDateString = newDateString.substring(0, 8);
	}

	Date date = null;
	try {
	    date = dfmt.parse(newDateString);
	} catch (Exception e) {
	    throw new RuntimeException(
		    "UDF getEndDateOfTimePeriod: Could not parse Date "
			    + newDateString);
	}

	if (timingQualf.equals("D")) {
	    resultString = newDateString;
	} else {
	    Calendar calendar = createCalendar();
	    calendar.setTime(date);
	    if (timingQualf.equals("W")) { // weekly
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
	    } else if (timingQualf.equals("M")) { // monthly
		calendar.set(Calendar.DAY_OF_MONTH, calendar
			.getActualMaximum(Calendar.DAY_OF_MONTH));
	    } else {
		throw new RuntimeException(
			"UDF getEndDateOfTimePeriod: unexspected time period qualifier"
				+ timingQualf);
	    }
	    resultString = dfmt.format(calendar.getTime());
	}

	return resultString;
	}

	@LibraryMethod(title="getStartOfOneOrTwoDates", description="Gets the oldest date of one or more date strings", category="UDFDateTimePool", type=ExecutionType.ALL_VALUES_OF_CONTEXT) 
	public void getStartOfOneOrTwoDates (
		@Argument(title="")  String[] dates,
		 ResultList result,
		 Container container)  throws StreamTransformationException{
		

		if (dates != null && dates.length > 0) {
			String startDate = null;
			if (dates.length < 2) {
				startDate = dates[0];
			}
			if (startDate == null) {
				String date_0 = dates[0].trim();
				if (date_0.length() == 0 || ResultList.SUPPRESS.equals(date_0)
						|| "0".equals(date_0)) {
					startDate = dates[0];
				}
				if (startDate == null) {
					String date_1 = dates[1].trim();
					if (date_1.length() == 0 || ResultList.SUPPRESS.equals(date_1)
							|| "0".equals(date_1)) {
						startDate = dates[1];
					}
				}
				if (startDate == null) {
					DateFormat dfmt = createFormatter("yyyyMMdd");
					Date date1 = null;
					Date date2 = null;
					try {
						date1 = dfmt.parse(dates[0]);
						date2 = dfmt.parse(dates[1]);
					} catch (ParseException e) {
						// ignore and try again
					}
					if (date1 == null || date2 == null) {
						try {
							date1 = dfmt.parse(dates[0].substring(0,8));
							date2 = dfmt.parse(dates[1].substring(0,8));
						} catch (ParseException e) {
							throw new RuntimeException(
									"UDF getStartOfOneOrTwoDates: Could not parse Date"
											+ dates[0] + " and/or " + dates[1]);
						}
					}
        
					if (date1.before(date2)) {
						startDate = dfmt.format(date1);
					} else {
						startDate = dfmt.format(date2);
					}
				}
			}
			result.addValue(startDate);
		}
	}

	@LibraryMethod(title="getEndOfOneOrTwoDates", description="Gets the last date of one or more date strings", category="UDFDateTimePool", type=ExecutionType.ALL_VALUES_OF_CONTEXT) 
	public void getEndOfOneOrTwoDates (
		@Argument(title="")  String[] dates,
		 ResultList result,
		 Container container)  throws StreamTransformationException{
		

		if (dates != null && dates.length > 0) {
			String endDate = null;
			if (dates.length < 2) {
				endDate = dates[0];
			}
			if (endDate == null) {
				String date_0 = dates[0].trim();
				if (date_0.length() == 0 || ResultList.SUPPRESS.equals(date_0)
						|| "0".equals(date_0)) {
					endDate = dates[1];
				}
				if (endDate == null) {
					String date_1 = dates[1].trim();
					if (date_1.length() == 0
							|| ResultList.SUPPRESS.equals(date_1)
							|| "0".equals(date_1)) {
						endDate = dates[0];
					}
				}
				if (endDate == null) {
					DateFormat dfmt = createFormatter("yyyyMMdd");
					Date date1 = null;
					Date date2 = null;
					try {
						date1 = dfmt.parse(dates[0]);
						date2 = dfmt.parse(dates[1]);
					} catch (ParseException e) {
						// ignore and try again
					}
					if (date1 == null || date2 == null) {
						try {
							date1 = dfmt.parse(dates[0].substring(0,8));
							date2 = dfmt.parse(dates[1].substring(0,8));
						} catch (ParseException e) {
							throw new RuntimeException(
									"UDF getEndOfOneOrTwoDates: Could not parse Date"
											+ dates[0] + " and/or " + dates[1]);
						}
					}

					if (date1.before(date2)) {
						endDate = dfmt.format(date2);
					} else {
						endDate = dfmt.format(date1);
					}
				}
			}
			result.addValue(endDate);
		}
	}
}