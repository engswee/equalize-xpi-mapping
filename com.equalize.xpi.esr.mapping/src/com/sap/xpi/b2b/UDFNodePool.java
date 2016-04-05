package com.sap.xpi.b2b;

import com.sap.aii.mapping.api.*;
import com.sap.aii.mapping.lookup.*;
import com.sap.aii.mappingtool.tf7.rt.*;
import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import com.sap.ide.esr.tools.mapping.core.LibraryMethod;
import com.sap.ide.esr.tools.mapping.core.ExecutionType;
import com.sap.ide.esr.tools.mapping.core.Argument;
import com.sap.ide.esr.tools.mapping.core.Init;
import com.sap.ide.esr.tools.mapping.core.Cleanup;
import com.sap.ide.esr.tools.mapping.core.Parameter;


public class UDFNodePool  {



	@Init(description="") 
	public void init (
		 GlobalContainer container)  throws StreamTransformationException{
		
	}

	@Cleanup 
	public void cleanup (
		 GlobalContainer container)  throws StreamTransformationException{
		
	}

	@LibraryMethod(title="createIfHasValue", description="Produces empty value if argument is not empty; ResultList.SUPPRESS otherwise", category="UDFNodePool", type=ExecutionType.ALL_VALUES_OF_CONTEXT) 
	public void createIfHasValue (
		@Argument(title="")  String[] contextValues,
		 ResultList result,
		 Container container)  throws StreamTransformationException{
		
		if (contextValues != null && contextValues.length > 0) {
			for (int i = 0; i < contextValues.length; i++) {
				String value = contextValues[i];
				if (value != null && value.trim().length() > 0
						&& !ResultList.SUPPRESS.equalsIgnoreCase(value)) {
					result.addValue("");
				} else {
					result.addSuppress();
				}
			}
		}
	}

	@LibraryMethod(title="createIfExistsAndHasValue", description="Produces empty value if argument is not empty and exists; ResultList.SUPPRESS otherwise", category="UDFNodePool", type=ExecutionType.ALL_VALUES_OF_CONTEXT) 
	public void createIfExistsAndHasValue (
		@Argument(title="")  String[] contextValues,
		 ResultList result,
		 Container container)  throws StreamTransformationException{
		
		if (contextValues != null && contextValues.length > 0) {
			for (int i = 0; i < contextValues.length; i++) {
				String value = contextValues[i];
				if (value != null && value.trim().length() > 0
						&& !ResultList.SUPPRESS.equalsIgnoreCase(value)) {
					result.addValue("");
				} else {
					result.addSuppress();
				}
			}
		} else {
			result.addSuppress();
		}
	}

	@LibraryMethod(title="createIfHasOneOfSuchValues", description="Produces empty value when the first argument has one of the values passed as a constant in the second argument (separated by a semicolon);  ResultList.SUPPRESS otherwise", category="UDFNodePool", type=ExecutionType.ALL_VALUES_OF_CONTEXT) 
	public void createIfHasOneOfSuchValues (
		@Argument(title="")  String[] contextValues,
		@Argument(title="")  String[] suchValuesString,
		 ResultList result,
		 Container container)  throws StreamTransformationException{
		
		if (suchValuesString == null || suchValuesString.length == 0) {
			throw new IllegalStateException(
					"createIfHasOneOfSuchValues: there is no suchValuesString");
		}
		if (contextValues != null && contextValues.length > 0) {
			String[] suchValues = suchValuesString[0].split(";");

			for (int i = 0; i < contextValues.length; i++) {
				String value = ResultList.SUPPRESS;
				for (int j = 0; j < suchValues.length; j++) {
					if (suchValues[j].equalsIgnoreCase(contextValues[i])) {
						value = "";
						break;
					}
				}
				result.addValue(value);
			}
		}
	}

	@LibraryMethod(title="createIfExistsAndHasOneOfSuchValues", description="Produces empty value when the first argument exists and has one of the values passed as a constant in the second argument (separated by a semicolon);  ResultList.SUPPRESS otherwise", category="UDFNodePool", type=ExecutionType.ALL_VALUES_OF_CONTEXT) 
	public void createIfExistsAndHasOneOfSuchValues (
		@Argument(title="")  String[] contextValues,
		@Argument(title="")  String[] suchValuesString,
		 ResultList result,
		 Container container)  throws StreamTransformationException{
		
		if (suchValuesString == null || suchValuesString.length == 0) {
			throw new IllegalStateException(
					"createIfHasOneOfSuchValues: there is no suchValuesString");
		}
		if (contextValues != null && contextValues.length > 0) {
			String[] suchValues = suchValuesString[0].split(";");

			for (int i = 0; i < contextValues.length; i++) {
				String value = ResultList.SUPPRESS;
				for (int j = 0; j < suchValues.length; j++) {
					if (suchValues[j].equalsIgnoreCase(contextValues[i])) {
						value = "";
						break;
					}
				}
				result.addValue(value);
			}
		} else {
			result.addSuppress();
		}
	}

	@LibraryMethod(title="passIfHasValue", description="Maps every context value to itself when not an empty string; to ResultList.SUPPRESS otherwise", category="UDFNodePool", type=ExecutionType.ALL_VALUES_OF_CONTEXT) 
	public void passIfHasValue (
		@Argument(title="")  String[] contextValues,
		 ResultList result,
		 Container container)  throws StreamTransformationException{
		
		if (contextValues != null && contextValues.length > 0) {
			for (int i = 0; i < contextValues.length; i++) {
				String str = contextValues[i];
				if (str != null && str.trim().length() > 0) {
					result.addValue(str);
				} else {
					result.addSuppress();
				}
			}
		}
	}

	@LibraryMethod(title="passIfExistsAndHasValue", description="Maps every context value to itself when not an empty string and existing; to ResultList.SUPPRESS otherwise", category="UDFNodePool", type=ExecutionType.ALL_VALUES_OF_CONTEXT) 
	public void passIfExistsAndHasValue (
		@Argument(title="")  String[] contextValues,
		 ResultList result,
		 Container container)  throws StreamTransformationException{
		
			if (contextValues != null && contextValues.length > 0) {
				for (int i = 0; i < contextValues.length; i++) {
					String str = contextValues[i];
					if (str != null && str.trim().length() > 0) {
						result.addValue(str);
					} else {
						result.addSuppress();
					}
				}
			} else {
				result.addSuppress();
			}
	}

	@LibraryMethod(title="assignValueByCondition", description="Short of IfWithoutElse: Passes a value from the third argument when the corresponding value from first argument has one of the value contained in the second value (these conditions are separated by a semicolon); passes ResultList.SUPPRESS otherwise", category="UDFNodePool", type=ExecutionType.ALL_VALUES_OF_CONTEXT) 
	public void assignValueByCondition (
		@Argument(title="")  String[] conditionContextValues,
		@Argument(title="")  String[] suchValuesString,
		@Argument(title="")  String[] contextValues,
		 ResultList result,
		 Container container)  throws StreamTransformationException{
		
		// A short form of the standard's IfWithoutElse

		// AbstractTrace trace=container.getTrace();
		if (suchValuesString == null || suchValuesString.length == 0) {
			throw new IllegalStateException("assignValueByCondition: "
					+ "there is no suchValuesString");
		}
		
		// Action when one of the contexts have values
		if (conditionContextValues != null
				&& conditionContextValues.length > 0 || (contextValues != null
				&& contextValues.length > 0)) {
			// Forbidden: both contexts have content but different number of
			// value
			if (contextValues != null && contextValues.length > 0
					&& conditionContextValues != null
					&& conditionContextValues.length > 0
					&& contextValues.length != conditionContextValues.length) {
				throw new IllegalStateException(
						"assignValueByCondition: "
								+ "conditionContextValues and contextValues have different lengths");
			}

			if (conditionContextValues == null
					|| conditionContextValues.length == 0
					|| contextValues == null || contextValues.length == 0) {
				// Allowed; one context has content the other hasn't
				result.addSuppress();
			} else {
				String[] conditions = suchValuesString[0].split(";");
				for (int i = 0; i < conditionContextValues.length; i++) {
					String value = ResultList.SUPPRESS;
					for (int j = 0; j < conditions.length; j++) {
						if (conditions[j]
								.equalsIgnoreCase(conditionContextValues[i])) {
							value = contextValues[i];
							break; // leave the inner loop
						}
					}
					result.addValue(value);
				}
			}
		}
	}

	@LibraryMethod(title="simpleUseOneAsManyAndSplitByEachValue", description="Uses the first argument (the context should have exactly one value) as often as the length of the second context indicates and splits the result by each value.", category="UDFNodePool", type=ExecutionType.ALL_VALUES_OF_CONTEXT) 
	public void simpleUseOneAsManyAndSplitByEachValue (
		@Argument(title="")  String[] contextValues,
		@Argument(title="")  String[] secondContext,
		 ResultList result,
		 Container container)  throws StreamTransformationException{
		
		// AbstractTrace trace=container.getTrace();
		if (secondContext != null && secondContext.length > 0) {
			if (contextValues != null && contextValues.length > 0) {
				String value = null;
				if (contextValues.length == 1) {
					value = contextValues[0];
				} else {
					for (int i = 0; i < contextValues.length; i++) {
						if (!ResultList.SUPPRESS.equals(contextValues[i])) {
							value = contextValues[i];
							break;
						}
					}
					if (value == null) {
						value = contextValues[0];
					}
				}
				result.addValue(value);
				for (int i = 1; i < secondContext.length; i++) {
					result.addContextChange();
					result.addValue(value);
				}
			} else {
				// bug fix: an empty context must be duplicated too !!
				for (int i = 1; i < secondContext.length; i++) {
					result.addContextChange();
				}
			}
		}
	}

	@LibraryMethod(title="simpleUseOneAsMany", description="Uses the first argument (the context should have exactly one value) as often as the length of the second context indicates.", category="UDFNodePool", type=ExecutionType.ALL_VALUES_OF_CONTEXT) 
	public void simpleUseOneAsMany (
		@Argument(title="")  String[] contextValues,
		@Argument(title="")  String[] secondContext,
		 ResultList result,
		 Container container)  throws StreamTransformationException{
		
		if (contextValues != null && contextValues.length > 0
				&& secondContext != null && secondContext.length > 0) {
			String value = null;
			if (contextValues.length == 1) {
				value = contextValues[0];
			} else {
				for (int i = 0; i < contextValues.length; i++) {
					if (!ResultList.SUPPRESS.equals(contextValues[i])) {
						value = contextValues[i];
						break;
					}
				}
				if (value == null) {
					value = contextValues[0];
				}
			}
			for (int i = 0; i < secondContext.length; i++) {
				result.addValue(value);
			}
		}
	}

	@LibraryMethod(title="deleteSuppress", description="Maps every context value to itself when not null and not ResultList.SUPPRESS - maybe the context queue will get shoter<br>", category="UDFNodePool", type=ExecutionType.ALL_VALUES_OF_CONTEXT) 
	public void deleteSuppress (
		@Argument(title="")  String[] contextValues,
		 ResultList result,
		 Container container)  throws StreamTransformationException{
		
		if (contextValues != null && contextValues.length > 0) {
			for (int i = 0; i < contextValues.length; i++) {
				if (contextValues[i] != null
						&& !ResultList.SUPPRESS
								.equalsIgnoreCase(contextValues[i])) {
					result.addValue(contextValues[i]);
				}
			}
		}
	}

	@LibraryMethod(title="getFirstContextValue", description="Returns the first non empty context value (or ResultList.SUPPRESS if no such value exits).", category="UDFNodePool", type=ExecutionType.ALL_VALUES_OF_CONTEXT) 
	public void getFirstContextValue (
		@Argument(title="")  String[] contextValues,
		 ResultList result,
		 Container container)  throws StreamTransformationException{
		
		if (contextValues != null && contextValues.length > 0) {
			String value = ResultList.SUPPRESS;
			for (int i = 0; i < contextValues.length; i++) {
				String str = contextValues[i];
				if (str != null && !ResultList.SUPPRESS.equalsIgnoreCase(str)) {
					value = str;
					break;
				}
			}
			result.addValue(value);
		}
	}

	@LibraryMethod(title="existsAndHasValue", description="Produces 'true' if the argument is not empty; 'false' otherwise", category="UDFNodePool", type=ExecutionType.ALL_VALUES_OF_CONTEXT) 
	public void hasValue (
		@Argument(title="")  String[] contextValues,
		 ResultList result,
		 Container container)  throws StreamTransformationException{
		
		if (contextValues != null && contextValues.length > 0) {
			for (int i = 0; i < contextValues.length; i++) {
				if (contextValues[i] != null
						&& contextValues[i].trim().length() > 0
						&& !ResultList.SUPPRESS
								.equalsIgnoreCase(contextValues[i])) {
					result.addValue("true");
				} else {
					result.addValue("false");
				}
			}
		}  else {
			result.addValue("false");
		}
	}

	@LibraryMethod(title="existsAndHasOneOfSuchValues", description="Produces 'true' if the first argument has one of the values passed as a constant in the second argument (separated by a semicolon); 'false' otherwise", category="UDFNodePool", type=ExecutionType.ALL_VALUES_OF_CONTEXT) 
	public void hasOneOfSuchValues (
		@Argument(title="")  String[] contextValues,
		@Argument(title="")  String[] suchValuesString,
		 ResultList result,
		 Container container)  throws StreamTransformationException{
		
		if (suchValuesString == null || suchValuesString.length == 0) {
			throw new IllegalStateException("hasOneOfSuchValues: "
					+ "there is no suchValuesString");
		}
		
		if (contextValues != null && contextValues.length > 0) {
			String[] suchValues = suchValuesString[0].split(";");

			for (int i = 0; i < contextValues.length; i++) {
				String oneOfSuchValues = "false";
				for (int j = 0; j < suchValues.length; j++) {
					if (suchValues[j].equalsIgnoreCase(contextValues[i])) {
						oneOfSuchValues = "true";
						break;
					}
				}
				result.addValue(oneOfSuchValues);
			}
		}  else {
			result.addValue("false");
		}
	}

	@LibraryMethod(title="contextHasOneOfSuchValues", description="Produces a single  'true' if the first argument (a context queue) contains one of the values passed as a constant in the second argument (separated by a semicolon); 'false' otherwise", category="UDFNodePool", type=ExecutionType.ALL_VALUES_OF_CONTEXT) 
	public void contextHasOneOfSuchValues (
		@Argument(title="")  String[] contextValues,
		@Argument(title="")  String[] suchValuesString,
		 ResultList result,
		 Container container)  throws StreamTransformationException{
		
		if (contextValues != null && contextValues.length > 0) {
			if (suchValuesString == null || suchValuesString.length == 0
					|| suchValuesString[0] == null) {
				throw new IllegalStateException("contextHasOneOfSuchValues: "
						+ "there is no suchValuesString");
			}
			String[] suchValues = suchValuesString[0].split(";");

			String oneOfSuchValues = "false";
			fcontext: for (int i = 0; i < contextValues.length; i++) {
				for (int j = 0; j < suchValues.length; j++) {
					if (suchValues[j].equalsIgnoreCase(contextValues[i])) {
						oneOfSuchValues = "true";
						break fcontext;
					}
				}
			}

			result.addValue(oneOfSuchValues);
		} else {
			result.addValue("false");
		}
	}

	@LibraryMethod(title="useOneContextAsMany", description="Uses the first argument as often as values exist in the second argument.", category="UDFNodePool", type=ExecutionType.ALL_VALUES_OF_CONTEXT) 
	public void useOneContextAsMany (
		@Argument(title="")  String[] contextValues,
		@Argument(title="")  String[] secondContext,
		 ResultList result,
		 Container container)  throws StreamTransformationException{
				if (contextValues != null && contextValues.length > 0) {
			if (secondContext != null && secondContext.length > 0) {
				for (int i = 0; i < secondContext.length - 1; i++) {
					for (int j=0; j < contextValues.length; j++) {
						result.addValue(contextValues[j]);
					}
					result.addContextChange();
				}
				// the last context
				for (int j=0; j < contextValues.length; j++) {
					result.addValue(contextValues[j]);
				}
			}
		}
	}

	@LibraryMethod(title="concatToOneQueue", description="Puts all arguments sequentially into one queue", category="UDFNodePool", type=ExecutionType.ALL_VALUES_OF_CONTEXT) 
	public void concatToOneQueue (
		@Argument(title="")  String[] queue1values,
		@Argument(title="")  String[] queue2values,
		@Argument(title="")  String[] queue3values,
		@Argument(title="")  String[] queue4values,
		@Argument(title="")  String[] queue5values,
		 ResultList result,
		 Container container)  throws StreamTransformationException{
		
		if (queue1values != null && queue1values.length > 0) {
			for (int i = 0; i < queue1values.length; i++) {
				result.addValue(queue1values[i]);
			}
		} else {
			result.addValue(ResultList.SUPPRESS);
		}
		if (queue2values != null && queue2values.length > 0) {
			for (int i = 0; i < queue2values.length; i++) {
				result.addValue(queue2values[i]);
			}
		} else {
			result.addValue(ResultList.SUPPRESS);
		}
		if (queue3values != null && queue3values.length > 0) {
			for (int i = 0; i < queue3values.length; i++) {
				result.addValue(queue3values[i]);
			}
		} else {
			result.addValue(ResultList.SUPPRESS);
		}
		if (queue4values != null && queue4values.length > 0) {
			for (int i = 0; i < queue4values.length; i++) {
				result.addValue(queue4values[i]);
			}
		} else {
			result.addValue(ResultList.SUPPRESS);
		}
		if (queue5values != null && queue5values.length > 0) {
			for (int i = 0; i < queue5values.length; i++) {
				result.addValue(queue5values[i]);
			}
		} else {
			result.addValue(ResultList.SUPPRESS);
		}
	}

	@LibraryMethod(title="deleteMultipleContextValues", description="Removes all multiple values from the context.leaving only the first", category="UDFNodePool", type=ExecutionType.ALL_VALUES_OF_CONTEXT) 
	public void groupContextValues (
		@Argument(title="")  String[] contextValues,
		 ResultList result,
		 Container container)  throws StreamTransformationException{
		
		List values = new ArrayList();
		if (contextValues != null && contextValues.length > 0) {
			for (int i = 0; i < contextValues.length; i++) {
				if (ResultList.SUPPRESS.equals(contextValues[i])) {
					continue;
				}
				if (!values.contains(contextValues[i])) {
					values.add(contextValues[i]);
					result.addValue(contextValues[i]);
				}
			}
		}
	}

	@LibraryMethod(title="suppressMultipleContextValues", description="Suppresses all multiple values from the context.leaving only the first", category="UDFNodePool", type=ExecutionType.ALL_VALUES_OF_CONTEXT) 
	public void suppressMultipleContextValues (
		@Argument(title="")  String[] contextValues,
		 ResultList result,
		 Container container)  throws StreamTransformationException{
		
		List values = new ArrayList();
		if (contextValues != null && contextValues.length > 0) {
			for (int i = 0; i < contextValues.length; i++) {
				if (ResultList.SUPPRESS.equals(contextValues[i])) {
					result.addSuppress();
				} else if (!values.contains(contextValues[i])) {
					values.add(contextValues[i]);
					result.addValue(contextValues[i]);
				} else {
					result.addSuppress();
				}
			}
		}
	}

	@LibraryMethod(title="concatContextValues", description="concatenates all values of a context seperated by second argument", category="UDFNodePool", type=ExecutionType.ALL_VALUES_OF_CONTEXT) 
	public void concatContextValues (
		@Argument(title="")  String[] contextValues,
		@Argument(title="")  String[] delimiterString,
		 ResultList result,
		 Container container)  throws StreamTransformationException{
		
		if (delimiterString == null || delimiterString.length == 0) {
			throw new IllegalStateException("concatContextValues: "
					+ "there is no delimiterString");
		}

		if (contextValues != null && contextValues.length > 0) {
			String delimiter = delimiterString[0];
			StringBuffer sb = new StringBuffer(contextValues[0]);
			for (int i = 1; i < contextValues.length; i++) {
				sb.append(delimiter).append(contextValues[i]);
			}

			result.addValue(sb.toString());
		}
	}

	@LibraryMethod(title="formatByContextExample", description="format contexts in inputQueue by values (representing contexts) in exampleQueue", category="UDFNodePool", type=ExecutionType.ALL_VALUES_OF_QUEUE) 
	public void formatByContextExample (
		@Argument(title="")  String[] inputQueue,
		@Argument(title="")  String[] exampleQueue,
		 ResultList result,
		 Container container)  throws StreamTransformationException{
		
		// format contexts in inputQueue by values (representing contexts) in exampleQueue.
		// this means that contexts can reduce
		// requirement: number of contexts in inputQueue = number of values in exampleQueue
		if (inputQueue != null && inputQueue.length > 0) {
			// declaration
			int inputCCCounter = 1;
			int exampleContextCounter = 0;

			// count number of contexts in inputQueue for checking
			for (int k = 0; k < inputQueue.length; k++) {
				if (ResultList.CC.equals(inputQueue[k])) {
					inputCCCounter++;
				}
			}

			// count number of values in exampleQueue for checking
			for (int l = 0; l < exampleQueue.length; l++) {
				if (!(ResultList.CC.equals(exampleQueue[l]))) {
					exampleContextCounter++;
				}
			}

			// start of main computing
			if (inputCCCounter == exampleContextCounter) {
				int inputIndex = 0;

				// looping on exampleQueue
				for (int i = 0; i < exampleQueue.length; i++) {
					// check for context change in exampleQueue
					if (ResultList.CC.equals(exampleQueue[i])) {
						// add context change to result
						result.addContextChange();
					} else
						// looping on inputQueue
						for (int j = inputIndex; j < inputQueue.length; j++) {
							inputIndex++;
							// check for context changes in inputQueue
							if (ResultList.CC.equals(inputQueue[j])) {
								break;
							} else {
								// add value to result if there is no context change
								result.addValue(inputQueue[j]);
							}
						}
				}
			} else {
				throw new RuntimeException(
						"formatByContextExample: number of contexts in inputQueue ("
								+ inputCCCounter + ") and number of values in exampleQueue ("
								+ exampleContextCounter + ") are not equal.");
			}
		}
	}

	@LibraryMethod(title="createMultipleCopies", description="Copies each value of the 1st queue as often as the corresponding value of the 2nd queue indicates", category="UDFNodePool", type=ExecutionType.ALL_VALUES_OF_CONTEXT) 
	public void createMultipleCopies (
		@Argument(title="")  String[] contextValues,
		@Argument(title="")  String[] copyCounts,
		 ResultList result,
		 Container container)  throws StreamTransformationException{
			
	if (contextValues != null && contextValues.length > 0) {
			if (copyCounts == null || contextValues.length != copyCounts.length) {
				throw new IllegalStateException("createMultipleCopies: "
						+ "contextValues and copyCounts have different lengths");
			}
			if (contextValues != null) {
				for (int i = 0; i < contextValues.length; i++) {
					int count = 0;
					try {
						count = Integer.parseInt(copyCounts[i]);
					} catch (Exception ex) {
						throw new RuntimeException("UDF createMultipleCopies: "
								+ copyCounts[i] + " is not a number");
					}
	
					if (count > 0) {
						for (int j = 0; j < count; j++) {
							result.addValue(contextValues[i]);
						}
					} else
						result.addSuppress();
				}
			}
		}
	}

	@LibraryMethod(title="createMultipleContextCopies", description="Copies the whole context of 1st queue as often as the corresponding value of the 2nd queue indicates", category="UDFNodePool", type=ExecutionType.ALL_VALUES_OF_CONTEXT) 
	public void createMultipleContextCopies (
		@Argument(title="")  String[] contextValues,
		@Argument(title="")  String[] copyCounts,
		 ResultList result,
		 Container container)  throws StreamTransformationException{
		
		if (copyCounts.length == 0) {
			throw new RuntimeException("UDF createMultipleContextCopies: "
					+ " copyCounts has length 0");
		}
		int count = 0;
		try {
			count = Integer.parseInt(copyCounts[0]);
		} catch (Exception ex) {
			throw new RuntimeException("UDF createMultipleContextCopies: "
					+ copyCounts[0] + " is not a number");
		}
		if (count > 0) {
			for (int i = 0; i < contextValues.length; i++) {
				result.addValue(contextValues[i]);
			}
			for (int i = 1; i < count; i++) {
				result.addContextChange();
				for (int k = 0; k < contextValues.length; k++) {
					result.addValue(contextValues[k]);
				}
			}
		} else {
			for (int i = 0; i < contextValues.length; i++) {
				result.addSuppress();
			}
		}
	}

	@LibraryMethod(title="fragmentSingleValue", description="Fragments the first argument (a single context value) into a max. number of peaces (given by the second argument) of length from the third; the last one may be shorter. All arguments are treated like constants.", category="UDFNodePool", type=ExecutionType.ALL_VALUES_OF_CONTEXT) 
	public void fragmentSingleValue (
		@Argument(title="")  String[] wholeValue,
		@Argument(title="")  String[] maxFragmentCount,
		@Argument(title="")  String[] eachFragmentsLength,
		 ResultList result,
		 Container container)  throws StreamTransformationException{
		
		// AbstractTrace trace = container.getTrace();
		if (wholeValue.length == 0) {
			return;
		}
		if (wholeValue.length > 1) {
			List contextValueList = new ArrayList();
			for (int i = 0; i < wholeValue.length; i++) {
				if (!ResultList.SUPPRESS.equals(wholeValue[i])) {
					contextValueList.add(wholeValue[i]);
				}
			}
			if (contextValueList.size() > 1) {
				throw new RuntimeException("Only one context value is expected");
			}
			if (contextValueList.size() == 0) {
				result.addSuppress();
				return;
			}
		}
		
		String singleValue = wholeValue[0];

		int maxCount;
		int length;
		try {
			maxCount = Integer.parseInt(maxFragmentCount[0]);
		} catch (Exception ex) {
			throw new RuntimeException(
					"UDF fragmentSingleValue: maxFragmentCount"
							+ maxFragmentCount[0] + " is not a number");
		}

		try {
			length = Integer.parseInt(eachFragmentsLength[0]);
		} catch (Exception ex) {
			throw new RuntimeException(
					"UDF fragmentSingleValue: eachFragmentsLength"
							+ eachFragmentsLength[0] + " is not a number");
		}

		int completeLineCount = singleValue.length() / length;
		int rest = singleValue.length() - completeLineCount * length;

		if (completeLineCount == 0 && rest == 0) {
			result.addValue(singleValue);
			return;
		}

		int resultLineCount = Math.min(maxCount, completeLineCount);
		// trace.addInfo("completeLineCount: " + completeLineCount);
		// trace.addInfo("maxCount: " + maxCount);
		// trace.addInfo("resultLineCount: " + resultLineCount);
		// trace.addInfo("rest: " + rest);
		for (int i = 0; i < resultLineCount; i++) {
			String subString = singleValue.substring(i * length, (i + 1)
					* length);
			result.addValue(subString);
		}

		if (maxCount > completeLineCount && rest > 0) {
			String subString = singleValue.substring(completeLineCount
					* length);
			result.addValue(subString);
		}
	}

	@LibraryMethod(title="concatTwoQueuesToOne", description="Puts all arguments sequentially into one queue", category="UDFNodePool", type=ExecutionType.ALL_VALUES_OF_CONTEXT) 
	public void concatTwoQueuesToOne (
		@Argument(title="")  String[] queue1values,
		 ResultList result,
		@Argument(title="")  String[] queue2values,
		 Container container)  throws StreamTransformationException{
		
	if (queue1values != null && queue1values.length > 0) {
	    for (int i = 0; i < queue1values.length; i++) {
					result.addValue(queue1values[i]);
	    }
	}
	if (queue2values != null && queue2values.length > 0) {
	    for (int i = 0; i < queue2values.length; i++) {
		result.addValue(queue2values[i]);
	    }
	}
	}

	@LibraryMethod(title="rearrangeByKey", description="Rearranges the third argument by the corresponding key set in the second argument formatted by the first argument", category="UDFNodePool", type=ExecutionType.ALL_VALUES_OF_CONTEXT) 
	public void getFieldValsByStr (
		@Argument(title="")  String[] arrangedSetOfKeys,
		@Argument(title="")  String[] allKeys,
		@Argument(title="")  String[] valuesToRearrange,
		 ResultList result,
		 Container container)  throws StreamTransformationException{
		//		AbstractTrace trace = container.getTrace();
		if (allKeys == null || valuesToRearrange == null
				|| allKeys.length != valuesToRearrange.length) {
			throw new IllegalStateException("UDF rearrangeByKey: "
					+ "allKeys and valuesToRearrange have different lengths");
		}

		if (valuesToRearrange == null || valuesToRearrange.length == 0) {
			for (int i = 0; i < arrangedSetOfKeys.length; i++) {
				result.addSuppress();
			}
		} else {
			if (arrangedSetOfKeys == null || arrangedSetOfKeys.length == 0) {
				throw new IllegalStateException(
						"UDF rearrangeByKey: there is no arrangedKeySet");
			}

			Set indexSet = new HashSet();
			for (int i = 0; i < allKeys.length; i++) {
				//				trace.addInfo("i: ... " + indexSet);
				//				trace.addInfo("i: ... " + allKeys[i]);
				if (ResultList.SUPPRESS.equals(allKeys[i])
						|| allKeys[i].trim().length() == 0) {
					// keep suppress values
					result.addSuppress();
				} else {
					// iterate over the arranged key set
					for1 : for (int k = 0; k < arrangedSetOfKeys.length; k++) {
						for (int m = 0; m < allKeys.length; m++) {
							if (indexSet.contains(new Integer(m))) {
								// value is already processed; ignore
								continue;
							}
							if (ResultList.SUPPRESS.equals(allKeys[m])
									|| allKeys[m].trim().length() == 0) {
								continue;
							}
							if (arrangedSetOfKeys[k].equals(allKeys[m])) {
								// value is not yet already processed; 
								// add tu resultlist and keep in mind by
								// putting onto a set
								result.addValue(valuesToRearrange[m]);
								indexSet.add(Integer.valueOf(m + ""));
								break for1;
							}
						}
					}
				}
			}
		}
	}

	@LibraryMethod(title="getValueByIndex", description="returns a value from a context(arg1) by given index(arg2) (starting at 1)", category="UDFNodePool", type=ExecutionType.ALL_VALUES_OF_CONTEXT) 
	public void getValueByIndex (
		@Argument(title="")  String[] contextValues,
		@Argument(title="")  String[] index,
		 ResultList result,
		 Container container)  throws StreamTransformationException{
				if(contextValues!=null && index!=null){
			String indexStr=index[0];			
			try{
				int indexInt=Integer.parseInt(indexStr);
				
				if(indexInt <= contextValues.length && indexInt >0) result.addValue(contextValues[indexInt-1]);
				return;
			}

			catch(java.lang.NumberFormatException numberFormatExp){
					throw new NumberFormatException(
						"UDF getValueByIndex: could not convert index to number");
			}

		}

	}

	@LibraryMethod(title="createNumberRange", description="Creates an empty string for each number of the missing Argument-to-Argument 2-intervall", category="UDFNodePool", type=ExecutionType.ALL_VALUES_OF_CONTEXT) 
	public void createNumberRange (
		@Argument(title="")  String[] startValues,
		@Argument(title="")  String[] endValues,
		 ResultList result,
		 Container container)  throws StreamTransformationException{
		
		if (startValues.length != endValues.length) {
			throw new IllegalStateException("UDF createNumberRange: "
					+ "startValues and endValues have different lengths");
		}
		for (int i = 0; i < startValues.length; i++) {
			String startValue = startValues[i].trim();
			if (ResultList.SUPPRESS.equals(startValue)) {
				continue;
			}
			long lv_from;
			try {
				lv_from = Long.parseLong(startValue);
			} catch (NumberFormatException nfe) {
				throw new RuntimeException("UDF createNumberRange: "
						+ " cannot parse long startValue " + startValue);
			}

			String endValue = endValues[i].trim();
			long lv_to;
			if (endValue.length() == 0
					|| ResultList.SUPPRESS.equals(startValue)) {
				lv_to = lv_from;
			} else {
				try {
					lv_to = Long.parseLong(endValue);
				} catch (NumberFormatException nfe) {
					throw new RuntimeException("UDF createNumberRange: "
							+ " cannot parse long endValue " + endValue);
				}
			}

			for (long k = lv_from; k <= lv_to; k++) {
				result.addValue(k + "");
			}
		}
	}

	@LibraryMethod(title="createContextsForFixedBlockSize", description="DO NOT USE ANYMORE!!! Splits context in first argument into blocks defined in size by parameter. Returns SUPPRESS when context is empty.", category="UDFNodePool", type=ExecutionType.ALL_VALUES_OF_CONTEXT) 
	public void createContextsForFixedBlockSize (
		@Argument(title="")  String[] contextValues,
		@Parameter(title="")  String blockSize,
		 ResultList result,
		 Container container)  throws StreamTransformationException{
				List contextValueList = new ArrayList();
		for (int i = 0; i < contextValues.length; i++) {
			if (!ResultList.SUPPRESS.equals(contextValues[i])) {
				contextValueList.add(contextValues[i]);
			}
		}
		int contextValueSize = contextValueList.size();
		if (contextValueSize > 0) {
			int blockSizeInt = 0;
			try {
				blockSizeInt = Integer.parseInt(blockSize);
			} catch (Exception ex) {
				throw new RuntimeException(
						"UDF createContextsForFixedBlockSize " + blockSize + " isn't an int value");
			}
			int ftxCount = contextValueSize / blockSizeInt;
			if (contextValueSize % blockSizeInt > 0) {
				++ftxCount;
			}
			for (int i = 0; i < ftxCount; i++) {
				result.addValue("");
			}
		} else {
			result.addSuppress();
		}
	}

	@LibraryMethod(title="buildBlocksAndGetValueByIndex", description="DO NOT USE ANYMORE!!! Splits context in first argument into blocks defined in size by parameter and returns value at index given by second argument. Returns SUPPRESS when context is empty.", category="UDFNodePool", type=ExecutionType.ALL_VALUES_OF_CONTEXT) 
	public void buildBlocksAndGetValueByIndex (
		@Argument(title="")  String[] contextValues,
		@Argument(title="")  String[] indexString,
		@Parameter(title="")  String blockSize,
		 ResultList result,
		 Container container)  throws StreamTransformationException{
				List contextValueList = new ArrayList();
		for (int i = 0; i < contextValues.length; i++) {
			if (!ResultList.SUPPRESS.equals(contextValues[i])) {
				contextValueList.add(contextValues[i]);
			}
		}

		int contextValueSize = contextValueList.size();
		if (contextValueSize > 0) {
			int blockSizeInt = 0;
			try {
				blockSizeInt = Integer.parseInt(blockSize);
			} catch (Exception ex) {
				throw new RuntimeException(
						"UDF buildBlocksAndGetValueByIndex " + blockSize + " isn't an int value");
			}

			int index = 0;
			try {
				index = Integer.parseInt(indexString[0]);
			} catch (Exception ex) {
				throw new RuntimeException("UDF buildBlocksAndGetValueByIndex " + indexString 
									+ " isn't an index value");
			}
			
			if (index >= blockSizeInt) {
				throw new RuntimeException("UDF buildBlocksAndGetValueByIndex: indexString "
						+ indexString + " doesn't fit the blocksize " + blockSize);
			}

			for (int i = index; i < contextValueSize; i += blockSizeInt) {
				result.addValue(contextValueList.get(i));
			}

			int mod = contextValueSize % blockSizeInt;
			if (mod != 0 && index >= mod) {
				result.addSuppress();
			}
		} else {
			result.addSuppress();
		}
	}

	@LibraryMethod(title="createContextsForFixedBlockSize001", description="Splits context in 1st argument into blocks defined in size by 2nd argument. Returns SUPPRESS when context is empty.", category="UDFNodePool", type=ExecutionType.ALL_VALUES_OF_CONTEXT) 
	public void createContextsForFixedBlockSize001 (
		@Argument(title="")  String[] contextValues,
		@Argument(title="")  String[] blockSize,
		 ResultList result,
		 Container container)  throws StreamTransformationException{
				int contextValueLength = contextValues.length;
		if (contextValueLength > 0) {
			int blockSizeInt = 0;
			try {
				blockSizeInt = Integer.parseInt(blockSize[0]);
			} catch (Exception ex) {
				throw new RuntimeException(
						"UDF createContextsForFixedBlockSize001 " + blockSize[0] + " isn't an int value");
			}

			int ftxCount = contextValueLength / blockSizeInt;
			if (contextValueLength % blockSizeInt > 0) {
				++ftxCount;
			}
			for (int i = 0; i < ftxCount; i++) {
				result.addValue("");
			}
		} 
	}

	@LibraryMethod(title="buildBlocksAndGetValueByIndex001", description="Splits context in 1st argument into blocks defined in size by 3rd argument and returns value at index given by 2nd argument. Returns SUPPRESS when context is empty.", category="UDFNodePool", type=ExecutionType.ALL_VALUES_OF_CONTEXT) 
	public void buildBlocksAndGetValueByIndex001 (
		@Argument(title="")  String[] contextValues,
		@Argument(title="")  String[] indexString,
		@Argument(title="")  String[] blockSize,
		 ResultList result,
		 Container container)  throws StreamTransformationException{
				int contextValueLength = contextValues.length;
		if (contextValueLength > 0) {
			int blockSizeInt = 0;
			try {
				blockSizeInt = Integer.parseInt(blockSize[0]);
			} catch (Exception ex) {
				throw new RuntimeException(
						"UDF buildBlocksAndGetValueByIndex001 " + blockSize[0] + " isn't an int value");
			}

			int index = 0;
			try {
				index = Integer.parseInt(indexString[0]);
			} catch (Exception ex) {
				throw new RuntimeException("UDF buildBlocksAndGetValueByIndex001 "
						+ indexString[0] + " isn't an index value");
			}
			
			if (index >= blockSizeInt) {
				throw new RuntimeException("UDF buildBlocksAndGetValueByIndex001: indexString "
						+ indexString[0] +  " doesn't fit the blocksize " + blockSize[0]);
			}

			for (int i = index; i < contextValueLength; i += blockSizeInt) {
				result.addValue(contextValues[i]);
			}

			int mod = contextValueLength % blockSizeInt;
			if (mod != 0 && index >= mod) {
				result.addSuppress();
			}
		}
	}

	@LibraryMethod(title="splitValueStringToContextValues", description="splits input string (Arg1) at given delimiter (Arg2) and creates a new context value for each new value", category="UDFNodePool", type=ExecutionType.ALL_VALUES_OF_CONTEXT) 
	public void splitValueStringToContextValues (
		@Argument(title="")  String[] inputString,
		@Argument(title="")  String[] delimiter,
		 ResultList result,
		 Container container)  throws StreamTransformationException{
			
		if (inputString == null) {
			return;
		}

		for (int i = 0; i < inputString.length; i++) {
			String[] splits = inputString[i].split(delimiter[0]);
			for (int j = 0; j < splits.length; j++) {
				result.addValue(splits[j]);
			}
		}
	}
}