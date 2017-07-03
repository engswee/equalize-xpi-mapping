package com.equalize.xpi.esr.mapping.udf.library;

import com.sap.aii.mapping.api.*;

import java.io.*;
import java.util.*;
import com.sap.aii.mappingtool.tf7.rt.*;
import com.sap.aii.mapping.lookup.*;
import java.lang.reflect.*;
import java.net.URI;

import javax.xml.bind.DatatypeConverter;

import com.sap.ide.esr.tools.mapping.core.LibraryMethod;
import com.sap.ide.esr.tools.mapping.core.ExecutionType;
import com.sap.ide.esr.tools.mapping.core.Argument;
import com.sap.ide.esr.tools.mapping.core.Init;
import com.sap.ide.esr.tools.mapping.core.Cleanup;
import com.sap.ide.esr.tools.mapping.core.Parameter;


public class FL_TextPool  {



	@Init(description="") 
	public void init (
		 GlobalContainer container)  throws StreamTransformationException{
		
	}

	@Cleanup 
	public void cleanup (
		 GlobalContainer container)  throws StreamTransformationException{
		
	}

	@LibraryMethod(title="isEmpty", description="Returns true if input String is empty - length() is 0", category="FL_TextPool", type=ExecutionType.SINGLE_VALUE) 
	public String isEmpty (
		@Argument(title="Input String")  String input,
		 Container container)  throws StreamTransformationException{
			return Boolean.toString(input.isEmpty());
	}

	@LibraryMethod(title="equalsConstant", description="Input value equals constant value configured in parameter", category="FL_TextPool", type=ExecutionType.SINGLE_VALUE) 
	public String equalsConstant (
		@Argument(title="Input String")  String input,
		@Parameter(title="Constant value")  String constant,
		 Container container)  throws StreamTransformationException{
			return Boolean.toString(input.equals(constant));
	}

	@LibraryMethod(title="regexReplace", description="Replace with regular expression", category="FL_TextPool", type=ExecutionType.SINGLE_VALUE) 
	public String regexReplace (
		@Argument(title="Input String")  String input,
		@Argument(title="Regex")  String regex,
		@Argument(title="Replacement")  String replacement,
		@Parameter(title="Replace All (Y, N)")  String replaceAll,
		 Container container)  throws StreamTransformationException{
			if(replaceAll.equalsIgnoreCase("Y")) {
			return input.replaceAll(regex, replacement);
		} else {
			return input.replaceFirst(regex, replacement);
		}
	}

	@LibraryMethod(title="getParameterValue", description="Extract the value of the parameter from an input String", category="FL_TextPool", type=ExecutionType.SINGLE_VALUE) 
	public String getParameterValue (
		@Argument(title="Input String")  String input,
		@Argument(title="Parameter Name")  String paramName,
		@Parameter(title="Equals Operator")  String equalsOperator,
		@Parameter(title="End Delimiter")  String endDelimiter,
		 Container container)  throws StreamTransformationException{
			// ----------------------------------------------------------
		// Extracts the value for a parameter in an input String				
		// ----------------------------------------------------------
		// input - input string
		// paramName - name of the parameter to extract
		// equalsOperator - character indicating equals operator for the parameter name-value pair
		// endDelimiter - character indicating end of parameter if the parameter is in the middle of the input
		// ----------------------------------------------------------
		// Sample input string:-
		// param1=value1&param2=value2&param3=value3
		// ----------------------------------------------------------

		if ( paramName == null || paramName.equals("") ) {
			throw new StreamTransformationException("paramName is empty");
		}

		int start = 0;
		int end = 0;
		String paramValue = null;

		// Get starting position of parameter
		start = input.indexOf(paramName + equalsOperator);
		if( start == -1 ) {
			return null;
		}
		// Find the end position of the parameter value
		// If there is no end delimiter, the value is until the end of the input string
		end = input.indexOf(endDelimiter, start);
		if( end == -1 ) {
			paramValue = input.substring(start);			
		}	else {
			paramValue = input.substring(start, end);			
		}
		// Remove the parameter name and equals operator from output
		paramValue = paramValue.substring(paramValue.indexOf(equalsOperator)+1);
		return paramValue;
	}

	@LibraryMethod(title="dynamicSubstring", description="Get substring of input", category="FL_TextPool", type=ExecutionType.SINGLE_VALUE) 
	public String dynamicSubstring (
		@Argument(title="Input String")  String input,
		@Parameter(title="Start index")  int start,
		@Parameter(title="Length of output")  int length,
		 Container container)  throws StreamTransformationException{
			// ----------------------------------------------------------
		// Extracts the substring of an input String				
		// ----------------------------------------------------------
		// input - input string
		// start - position index for start of string
		// length - length of substring
		// ----------------------------------------------------------
		int startPos = start;
		int endPos = start + length - 1;
		String output = "";

		if ( startPos < 0 ) {
			// (1) Start position is before start of input, return empty string
			output = "";
		} else if ( startPos >= 0 && startPos < input.length() ) {
			if ( endPos < input.length() ) {
				// (2) Start & end positions are before end of input, return the partial substring
				output = input.substring( startPos, endPos + 1 );
			} else if ( endPos >= input.length() ) {
				// (3) Start position is before start of input but end position is after end of input, return from start till end of input
				output = input.substring( startPos, input.length() );
			}
		} else if ( startPos >= input.length() ) {
			// (4) Start position is after end of input, return empty string
			output = "";
		}

		return output;
	}

	@LibraryMethod(title="urlEncoding", description="Convert the URL reserved characters in input string with percent encoding", category="FL_TextPool", type=ExecutionType.SINGLE_VALUE) 
	public String urlEncoding (
		@Argument(title="Input String")  String input,
		 Container container)  throws StreamTransformationException{
			// ----------------------------------------------------------
		// Converts the reserved characters in input string with 
		// percent encoding
		// ----------------------------------------------------------
		// Ref: http://en.wikipedia.org/wiki/Percent-encoding
		//
		// i.e: $ -> %24
		// ----------------------------------------------------------

		String converted = new String(input);
		converted = converted.replaceAll("[$]", "%24");
		converted = converted.replaceAll("[/]", "%2F");
		converted = converted.replaceAll("[:]", "%3A");
		converted = converted.replaceAll("[*]", "%2A");
		converted = converted.replaceAll("[?]", "%3F");

		return converted;
	}
	
	@LibraryMethod(title="encodeQueryString", description="", category="User-Defined", type=ExecutionType.SINGLE_VALUE) 
	public String encodeQueryString (
			@Argument(title="Input Query String")  String queryString,
			Container container)  throws StreamTransformationException{
		try {
			URI uri = new URI(null, null, null, -1, null, queryString, null);
			return uri.toString().replace("?", "");
		} catch (Exception e) {
			throw new StreamTransformationException(e.getMessage());
		}
	}

	@LibraryMethod(title="getLastChar", description="Get last n characters of input", category="FL_TextPool", type=ExecutionType.SINGLE_VALUE) 
	public String getLastChar (
		@Argument(title="Input String")  String input,
		@Parameter(title="Length of output")  int length,
		 Container container)  throws StreamTransformationException{
			// ----------------------------------------------------------
		// Get the last N characters of an input string				
		// ----------------------------------------------------------
		// input - input string
		// length - length of characters to extract
		// ----------------------------------------------------------
		int inputLength = input.length();
		if(inputLength <= length) {
			return input;
		} else {
			return input.substring(inputLength - length, inputLength);
		}
	}
	
	@LibraryMethod(title="hasValue", description="", category="FL_TextPool", type=ExecutionType.SINGLE_VALUE) 
	public String hasValue (
		@Argument(title="Input")  String input,
		@Parameter(title="Value List (comma separated)")  String listValues,
		 Container container)  throws StreamTransformationException{
				// listValues is comma separated
		String[] values = listValues.split(",");
		for(String value: values) {
			if(input.equals(value)) {
				return "true";
			}
		}
		// Otherwise not found
		return "false";
	}
	
	@LibraryMethod(title="checkNumeric", description="Check if input is numeric", category="FL_TextPool", type=ExecutionType.SINGLE_VALUE) 
	public String checkNumeric (
		@Argument(title="Input String")  String input,
		 Container container)  throws StreamTransformationException{

		boolean result = true;
		for (int i = 0; i < input.length() ; i++) {
			if(!Character.isDigit(input.charAt(i))) {
				result = false;
				break;
			}
		}
		return Boolean.toString(result);
	}
	
	@LibraryMethod(title="decodeBase64", description="Decode Base 64 input", category="FL_TextPool", type=ExecutionType.SINGLE_VALUE) 
	public String decodeBase64 (
			@Argument(title="Input Base 64 String")  String base64,
			 Container container)  throws StreamTransformationException{

		byte[] decoded = DatatypeConverter.parseBase64Binary(base64);
		try {
			return new String(decoded, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new StreamTransformationException("UnsupportedEncodingException: " + e.getMessage(), e);
		}
	}
	
	@LibraryMethod(title = "createCSVField", description = "Create the content in CSV format", category = "FL_TextPool", type = ExecutionType.SINGLE_VALUE)
	public String createCSVField(@Argument(title = "Input String") String input,
			@Parameter(title = "Delimiter character") String delimiter,
			@Parameter(title = "Enclosure Sign character") String enclosureSign,
			Container container) throws StreamTransformationException {

		if (input.contains(delimiter) || input.contains(delimiter)) {
			String output = input;
			if (output.contains(enclosureSign)) {
				output = output.replaceAll(enclosureSign, enclosureSign
						+ enclosureSign);
			}
			return enclosureSign + output + enclosureSign;
		} else {
			return input;
		}

	}
}