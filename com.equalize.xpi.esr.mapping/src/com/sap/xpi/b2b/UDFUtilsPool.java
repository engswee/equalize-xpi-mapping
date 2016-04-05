package com.sap.xpi.b2b;

import com.sap.aii.mapping.api.*;
import com.sap.aii.mapping.lookup.*;
import com.sap.aii.mappingtool.tf7.rt.*;
import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import java.math.*;
import com.sap.ide.esr.tools.mapping.core.LibraryMethod;
import com.sap.ide.esr.tools.mapping.core.ExecutionType;
import com.sap.ide.esr.tools.mapping.core.Argument;
import com.sap.ide.esr.tools.mapping.core.Init;
import com.sap.ide.esr.tools.mapping.core.Cleanup;


public class UDFUtilsPool  {



	@Init(description="") 
	public void init (
		 GlobalContainer container)  throws StreamTransformationException{
		
	}

	@Cleanup 
	public void cleanup (
		 GlobalContainer container)  throws StreamTransformationException{
		
	}

	@LibraryMethod(title="hasValue", description="Returns 'true' if the first argument is not empty; 'false' otherwise", category="UDFUtilsPool", type=ExecutionType.SINGLE_VALUE) 
	public String hasValue (
		@Argument(title="")  String value,
		 Container container)  throws StreamTransformationException{
		
		String output;
		if (value != null) {
			if (value.trim().length() > 0
					&& !ResultList.SUPPRESS.equalsIgnoreCase(value)) {
				output = "true";
			} else {
				output = "false";
			}
		} else {
			output = value;
		}

		return output;
	}

	@LibraryMethod(title="hasOneOfSuchValues", description="Returns 'true' when the first argument has one of the values passed as a constant in the second argument (separated by a semicolon); 'false' otherwise", category="UDFUtilsPool", type=ExecutionType.SINGLE_VALUE) 
	public String hasOneOfSuchValues (
		@Argument(title="")  String value,
		@Argument(title="")  String suchValuesString,
		 Container container)  throws StreamTransformationException{
		
		String oneOfSuchValues;
		if (value != null) {
			if (suchValuesString == null) {
				throw new IllegalStateException("UDF hasOneOfSuchValues: "
						+ "there is no suchValuesString");
			}
			oneOfSuchValues = "false";
			String[] suchValues = suchValuesString.split(";");
			for (int i = 0; i < suchValues.length; i++) {
				if (suchValues[i].equalsIgnoreCase(value)) {
					oneOfSuchValues = "true";
					break;
				}
			}
		} else {
			oneOfSuchValues = value;
		}

		return oneOfSuchValues;
	}

	@LibraryMethod(title="trimZeroLeft", description="Removes leading zeros.", category="UDFUtilsPool", type=ExecutionType.SINGLE_VALUE) 
	public String trimZeroLeft (
		@Argument(title="")  String value,
		 Container container)  throws StreamTransformationException{
		
		String output;
		if (value != null) {
			if (value.trim().length() == 0) {
				output = value;
			} else {
				output = value.replaceAll("^0*", "");
				if (output.trim().length() == 0) {
					output = "0";
				}
			}
		} else {
			output = value;
		}

		return output;
	}

	@LibraryMethod(title="trimRight", description="Removes trailing whitespaces (leading whitespaces may be significant).", category="UDFUtilsPool", type=ExecutionType.SINGLE_VALUE) 
	public String trimRight (
		@Argument(title="")  String value,
		 Container container)  throws StreamTransformationException{
				
		String result = null;
		int length = value.length();
		if (length > 0) {
			char[] chars = value.toCharArray();
			int trailing = length - 1;			
			while (trailing > -1 && chars[trailing] == ' ') {
				trailing--;
			}
			result = value.substring(0, trailing + 1);
		} else {
			result = value;
		}
		
		return result;
	}

	@LibraryMethod(title="fillUpToLengthWithSpace", description="Appends whitespaces to argument 1 til the length argeument 2  is reached.", category="UDFUtilsPool", type=ExecutionType.SINGLE_VALUE) 
	public String fillUpToLengthWithSpace (
		@Argument(title="")  String value,
		@Argument(title="")  String length,
		 Container container)  throws StreamTransformationException{
		
		String result = value;
		int requiredLength = 0;
		try {
			requiredLength = Integer.parseInt(length);
		} catch (NumberFormatException nfe) {
			throw new RuntimeException(
					"UDF fillUpToLengthWithSpace: the length  " + length
							+ " cannot be transformed into an integer value");
		}

		while (result.length() < requiredLength) {
			result += " ";
		}

		return result;
	}

	@LibraryMethod(title="splitToIndex", description="Splits the first argument by a delimiter (third argument) and returns the partial string requested by the the second argument (or an empty string if no such partial string exists)", category="UDFUtilsPool", type=ExecutionType.SINGLE_VALUE) 
	public String splitToIndex (
		@Argument(title="")  String value,
		@Argument(title="")  String index,
		@Argument(title="")  String delimiter,
		 Container container)  throws StreamTransformationException{
		
		String output = null;
		if (value != null) {
			int ind = 0;
			try {
				ind = Integer.parseInt(index);
			} catch (Exception ex) {
				throw new RuntimeException("UDF splitToIndex: index " + index
						+ " is not a numeric value.");
			}
			String[] splits = value.split(delimiter);
			if (splits.length > ind) {
				output = splits[ind];
			} else {
				// output null or suppress may drive the pi crazy
				output = "";
			}
		} else {
			output = value;
		}

		return output;
	}

	@LibraryMethod(title="formatValueByZero", description="Creates a new string from a non empty first argument with length given by the second as follows: cut at left or right end (the third argument) or fill (if the fourth says 'true') at left or right side (defined by the fifth))", category="UDFUtilsPool", type=ExecutionType.SINGLE_VALUE) 
	public String formatValueByZero (
		@Argument(title="")  String value,
		@Argument(title="")  String length,
		@Argument(title="")  String cutLengthDirection,
		@Argument(title="")  String fillZero,
		@Argument(title="")  String fillZeroDirection,
		 Container container)  throws StreamTransformationException{
		
		//AbstractTrace trace = container.getTrace();
		String output = null;
		if (value != null) {
			int lengthInt;
			try {
				lengthInt = Integer.parseInt(length);
			} catch (Exception ex) {
				throw new RuntimeException("UDF formatValueByZero: length "
						+ length + " is not a numeric value.");
			}
			int lengthValue = value.length();

			if (lengthValue > 0 && lengthValue != lengthInt) {
				if (lengthValue > lengthInt) {
					if ("left".equalsIgnoreCase(cutLengthDirection)) {
						int offset = lengthValue - lengthInt;
						output = value.substring(offset, lengthValue);
						//trace.addInfo("formatValueByZero output  value  " + resultVal);
					} else if ("right".equalsIgnoreCase(cutLengthDirection)) {
						output = value.substring(0, lengthInt);
						//trace.addInfo("formatValueByZero output  value  " + resultVal);
					} else {
						throw new RuntimeException(
								"UDF formatValueByZero: unexpected value "
										+ cutLengthDirection
										+ " for the cutDirection");
					}
				} else {
					if ("true".equalsIgnoreCase(fillZero)) {
						// now lengthValue < lengthInt
						int offset = lengthInt - lengthValue;
						String zeroString = "0";
						for (int i = 0; i < offset - 1; i++) {
							zeroString += "0";
						}
						if ("left".equalsIgnoreCase(fillZeroDirection)) {
							output = zeroString + value;
							//trace.addInfo("formatValueByZero output  value  " + resultVal);
						} else if ("right".equalsIgnoreCase(fillZeroDirection)) {
							output = value + zeroString;
							//trace.addInfo("formatValueByZero output  value  " + resultVal);
						} else {
							throw new RuntimeException(
									"UDF formatValueByZero: unexpected value "
											+ fillZeroDirection
											+ " for the fillDirection");
						}
					}
				}
			}
			if (output == null) {
				output = value;
			}
		} else {
			output = value;
		}

		return output;
	}

	@LibraryMethod(title="headString", description="Removes the trailing characters of the first argument leaving the number of characters given by the second argument. Attention: whitespaces are significant,  no trim is in action !", category="UDFUtilsPool", type=ExecutionType.SINGLE_VALUE) 
	public String headString (
		@Argument(title="")  String value,
		@Argument(title="")  String headLength,
		 Container container)  throws StreamTransformationException{
		
		String output = null;
		if (value != null && headLength != null && headLength.length() > 0) {
			int headLengthInt;
			try {
				headLengthInt = Integer.parseInt(headLength);
			} catch (NumberFormatException numberFormatExp) {
				throw new RuntimeException(
						"UDF headString: could not convert headLength"
								+ headLength + " to integer");
			}

			// DO NOT trim: in some cases the trailing whitespaces may be significant
			int length = value.length();
			if (length > headLengthInt) {
				output = value.substring(0, headLengthInt);
			} else {
				output = value;
			}
		} else {
			output = value;
		}

		return output;
	}

	@LibraryMethod(title="tailString", description="Removes the leading characters of the first argument leaving the number of characters given by the second argument.  Leading or trailing whitespaces don't counf.", category="UDFUtilsPool", type=ExecutionType.SINGLE_VALUE) 
	public String tailString (
		@Argument(title="")  String value,
		@Argument(title="")  String tailLength,
		 Container container)  throws StreamTransformationException{
		
		String output = null;
		if (value != null && tailLength != null
				&& tailLength.trim().length() > 0) {
			int tailLengthInt;
			try {
				tailLengthInt = Integer.parseInt(tailLength);
			} catch (NumberFormatException numberFormatExp) {
				throw new RuntimeException(
						"UDF tailString: could not convert tailLength"
								+ tailLength + " to integer");
			}
			// DO NOT trim as usual: in some cases the trailing whitespaces may be significant
			String trimmedValue = "";
			int valueLength = value.length();
			for (int i = valueLength; i > 0; i--) {
				if (value.charAt(i - 1) != ' ') {
					trimmedValue = value.substring(0, i);
					break;
				}
			}
			
			int length = trimmedValue.length();
			if (length > tailLengthInt) {
				output = trimmedValue.substring(length - tailLengthInt);
			} else {
				output = value;
			}
		} else {
			output = value;
		}

		return output;
	}

	@LibraryMethod(title="isNumber", description="Returns 'true' if the argument is a number, 'false' otherwise", category="UDFUtilsPool", type=ExecutionType.SINGLE_VALUE) 
	public String isNumber (
		@Argument(title="")  String value,
		 Container container)  throws StreamTransformationException{
		
		String output;
		if (value != null) {
			try {
				Double.parseDouble(value.trim());
				output = "true";
			} catch (Exception numExp) {
				output = "false";
			}
		} else {
			output = value;
		}

		return output;
	}

	@LibraryMethod(title="toNumber", description="Formats a string like e number (removes +-sign and leading/trailing zeroes", category="UDFUtilsPool", type=ExecutionType.SINGLE_VALUE) 
	public String toNumber (
		@Argument(title="")  String numberString,
		 Container container)  throws StreamTransformationException{
		
		if (numberString != null && numberString.trim().length() == 0) {
			return ResultList.SUPPRESS;
		}
		try {
			return new BigDecimal(numberString).toString();
		} catch (Exception ex) {
			throw new RuntimeException("UDF toNumber: "
					+ " can not transform numberString " + numberString 
					+ " to a number");
		}
	}

	@LibraryMethod(title="isNumberEqualZero", description="Returns 'true' if the argument is numeric and  has value 0; 'false' otherwise", category="UDFUtilsPool", type=ExecutionType.SINGLE_VALUE) 
	public String isNumberEqualZero (
		@Argument(title="")  String value,
		 Container container)  throws StreamTransformationException{
		
	String output = null;
	if (value != null) {
	    try {
					output = Boolean.toString(Double.parseDouble(value.trim()) == 0.0);
	    } catch (Exception numExp) {
					output = "false";
	    }
	} else {
	    output = value;
	}

	return output;
	}

	@LibraryMethod(title="isNumberNotEqualZero", description="Returns 'true' if the argument is numeric and  has not value 0; 'false' otherwise", category="UDFUtilsPool", type=ExecutionType.SINGLE_VALUE) 
	public String isNumberNotEqualZero (
		@Argument(title="")  String value,
		 Container container)  throws StreamTransformationException{
		
	String output = null;
	if (value != null) {
	    double dd = 0;
	    try {
		      dd = Double.parseDouble(value.trim());
	    } catch (Exception numExp) {
		      output = "false";
	    } finally {
		      if (output == null) {
		          output = Boolean.toString(dd != 0.0);
		      }
	    }
	} else {
	    output = value;
	}

	return output;
	}

	@LibraryMethod(title="minusFromBeginToEnd", description="set sign minus from begin to end", category="UDFUtilsPool", type=ExecutionType.SINGLE_VALUE) 
	public String minusFromBeginToEnd (
		@Argument(title="")  String value,
		 Container container)  throws StreamTransformationException{
		
		String output;
		if (value != null && value.startsWith("-")) {
			output = value.substring(1, value.length()) + "-";
		} else {
			output = value;
		}

		return output;
	}

	@LibraryMethod(title="minusFromEndToBegin", description="set sign minus from end to begin", category="UDFUtilsPool", type=ExecutionType.SINGLE_VALUE) 
	public String minusFromEndToBegin (
		@Argument(title="")  String value,
		 Container container)  throws StreamTransformationException{
		
		String output;
		if (value != null && value.endsWith("-")) {
			output = "-" + value.substring(0, value.length() - 1);
		} else {
			output = value;
		}
		
		return output;
	}
}