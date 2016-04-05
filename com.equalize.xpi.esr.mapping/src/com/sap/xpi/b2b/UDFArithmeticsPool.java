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


public class UDFArithmeticsPool  {



	@Init(description="") 
	public void init (
		 GlobalContainer container)  throws StreamTransformationException{
		
	}

	@Cleanup 
	public void cleanup (
		 GlobalContainer container)  throws StreamTransformationException{
		
	}

	@LibraryMethod(title="bigDecimalAdd", description="Adds two values", category="UDFArithmeticsPool", type=ExecutionType.SINGLE_VALUE) 
	public String bigDecimalAdd (
		@Argument(title="")  String summand1,
		@Argument(title="")  String summand2,
		 Container container)  throws StreamTransformationException{
		
		try {
			BigDecimal summand1D = new BigDecimal(summand1);
			BigDecimal summand2D = new BigDecimal(summand2);
			return summand1D.add(summand2D).toString();
		} catch (Exception ex) {
			throw new RuntimeException("UDF bigDecimalAdd: the values "
					+ summand1 + ", " + summand2
					+ " cannot be transformed into a addition ");
		}
	}

	@LibraryMethod(title="bigDecimalSubtract", description="Subtracts second value from the first value", category="UDFArithmeticsPool", type=ExecutionType.SINGLE_VALUE) 
	public String bigDecimalSubtract (
		@Argument(title="")  String minuend,
		@Argument(title="")  String subtrahend,
		 Container container)  throws StreamTransformationException{
		
		try {
			BigDecimal minuendD = new BigDecimal(minuend);
			BigDecimal subtrahendD = new BigDecimal(subtrahend);
			return minuendD.subtract(subtrahendD).toString();
		} catch (Exception ex) {
			throw new RuntimeException("UDF bigDecimalSubtract: the values "
					+ minuend + ", " + subtrahend
					+ " cannot be transformed into a subtraction ");
		}
	}

	@LibraryMethod(title="bigDecimalMultiply", description="Multiplies two values", category="UDFArithmeticsPool", type=ExecutionType.SINGLE_VALUE) 
	public String bigDecimalMultiply (
		@Argument(title="")  String factor1,
		@Argument(title="")  String factor2,
		 Container container)  throws StreamTransformationException{
		
		try {
			BigDecimal factor1D = new BigDecimal(factor1);
			BigDecimal factor2D = new BigDecimal(factor2);
			return factor1D.multiply(factor2D).toString();
		} catch (Exception ex) {
			throw new RuntimeException("UDF bigDecimalMultiply: the values "
					+ factor1 + ", " + factor2
					+ " cannot be transformed into a multiplication ");
		}
	}

	@LibraryMethod(title="bigDecimalDivide", description="Divides first value by the second value, rounding to the number of decimal places given by the third value", category="UDFArithmeticsPool", type=ExecutionType.SINGLE_VALUE) 
	public String bigDecimalDivide (
		@Argument(title="")  String dividend,
		@Argument(title="")  String divisor,
		@Argument(title="")  String precision,
		 Container container)  throws StreamTransformationException{
		
		try {
			BigDecimal dividentD = new BigDecimal(dividend);
			BigDecimal divisorD = new BigDecimal(divisor);

			return dividentD.divide(divisorD, Integer.parseInt(precision),
					BigDecimal.ROUND_HALF_UP).toString();
		} catch (Exception ex) {
			throw new RuntimeException("UDF bigDecimalDivide: the values "
					+ dividend + ", " + divisor
					+ " cannot be transformed into a division");
		}
	}

	@LibraryMethod(title="bigDecimalRound", description="Rounds the first value to the number of decimal places given by the second value", category="UDFArithmeticsPool", type=ExecutionType.SINGLE_VALUE) 
	public String bigDecimalRound (
		@Argument(title="")  String value,
		@Argument(title="")  String precision,
		 Container container)  throws StreamTransformationException{
		
		try {
			int precisionInt = Integer.parseInt(precision);
			BigDecimal d = new BigDecimal(value).setScale(precisionInt,
					BigDecimal.ROUND_HALF_UP);

			return d.toString();
		} catch (Exception ex) {
			throw new RuntimeException("UDF bigDecimalRound: the values " + value
					+ ", " + precision
					+ " cannot be transformed into a rounding");
		}
	}

	@LibraryMethod(title="bigDecimalRoundUp", description="Rounds up the first value to the number of decimal places given by the second value", category="UDFArithmeticsPool", type=ExecutionType.SINGLE_VALUE) 
	public String bigDecimalRoundUp (
		@Argument(title="")  String value,
		@Argument(title="")  String precision,
		 Container container)  throws StreamTransformationException{
		
		try {
			int precisionInt = Integer.parseInt(precision);
			BigDecimal d = new BigDecimal(value).setScale(precisionInt,
					BigDecimal.ROUND_UP);

			return d.toString();
		} catch (Exception ex) {
			throw new RuntimeException("UDF bigDecimalRound: the values " + value
					+ ", " + precision
					+ " cannot be transformed into a rounding");
		}
	}

	@LibraryMethod(title="bigDecimalSum", description="Calulates the sum of all context values", category="UDFArithmeticsPool", type=ExecutionType.ALL_VALUES_OF_CONTEXT) 
	public void bigDecimalSum (
		@Argument(title="")  String[] values,
		 ResultList result,
		 Container container)  throws StreamTransformationException{
		
		try {
			BigDecimal sum = new BigDecimal("0");
			for (int i = 0; i < values.length; i++) {
				if (ResultList.SUPPRESS.equals(values[i])) {
					continue;
				}
				BigDecimal valueD = new BigDecimal(values[i]);
				sum = sum.add(valueD);
			}
				result.addValue(sum.toString());
		} catch (Exception ex) {
			throw new RuntimeException("UDF bigDecimalSum: the values "
					+ Arrays.asList(values) + " cannot be transformed into a sum ");
		}
	}

	@LibraryMethod(title="bigDecimalMod", description="Self explaining", category="UDFArithmeticsPool", type=ExecutionType.SINGLE_VALUE) 
	public String bigDecimalMod (
		@Argument(title="")  String value,
		@Argument(title="")  String modValue,
		 Container container)  throws StreamTransformationException{
			
	try {
			BigInteger valueBi = new BigDecimal(value).toBigInteger();
			BigInteger modValueBi = new BigDecimal(modValue).toBigInteger();
			return valueBi.mod(modValueBi).toString();
		} catch (Exception ex) {
			throw new RuntimeException("UDF bigDecimalMod: the values "
					+ value + ", " + modValue
					+ " cannot be transformed into a mod value ");
		}
	}

	@LibraryMethod(title="bigDecimalFormatNum", description="Split number by the given position if possible", category="UDFArithmeticsPool", type=ExecutionType.SINGLE_VALUE) 
	public String bigDecimalFormatNum (
		@Argument(title="")  String number,
		@Argument(title="")  String splitIndex,
		 Container container)  throws StreamTransformationException{
		// Try to split or if value is shorter than position simply returns it
if(number != null &&  splitIndex != null) {
	try {
		BigInteger splitIndexBi = new BigDecimal(splitIndex).toBigInteger();
		if(number.length() > splitIndexBi.intValue() ) {
  				return number.substring(0, number.length() - splitIndexBi.intValue()) + "." 
																	+ number.substring(number.length() - splitIndexBi.intValue(), number.length());	 
	 } else {
			return number;
	 }
   } catch (NumberFormatException nfe) {
					nfe.printStackTrace();
   }

}
return null;
	}
}