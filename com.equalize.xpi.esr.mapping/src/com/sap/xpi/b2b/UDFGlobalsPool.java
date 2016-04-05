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


public class UDFGlobalsPool  {



	@Init(description="") 
	public void init (
		 GlobalContainer container)  throws StreamTransformationException{
		
	}

	@Cleanup 
	public void cleanup (
		 GlobalContainer container)  throws StreamTransformationException{
		
	}

	@LibraryMethod(title="initGlobalCounter", description="Creates a global parameter with name given by the first argument with value 0", category="UDFGlobalsPool", type=ExecutionType.SINGLE_VALUE) 
	public String initGlobalCounter (
		@Argument(title="")  String counterName,
		 Container container)  throws StreamTransformationException{
		
		//AbstractTrace trace = container.getTrace();
		GlobalContainer glCon = container.getGlobalContainer();
		String counter = "0";
		glCon.setParameter(counterName, Integer.valueOf(counter));
		//trace.addInfo(counterName + " has new value ...: " + counter);

		return "";
	}

	@LibraryMethod(title="increaseGlobalCounter", description="Add 1 to the value of the global parameter identified by the first argument", category="UDFGlobalsPool", type=ExecutionType.SINGLE_VALUE) 
	public String increaseGlobalCounter (
		@Argument(title="")  String counterName,
		 Container container)  throws StreamTransformationException{
		
		//AbstractTrace trace = container.getTrace();
		GlobalContainer glCon = container.getGlobalContainer();
		Integer counter = (Integer) glCon.getParameter(counterName);

		if (counter == null) {
			throw new IllegalStateException(
					"getGlobalCounter: No global counter with name "
							+ counterName + " found");
		}

		int newValue = counter.intValue() + 1;
		glCon.setParameter(counterName, Integer.valueOf(newValue));
		//trace.addInfo(counterName + " has new value ...: " + newValue);

		return "";
	}

	@LibraryMethod(title="getGlobalCounterValue", description="Gets the value of global parameter identified by the first argument", category="UDFGlobalsPool", type=ExecutionType.SINGLE_VALUE) 
	public String getGlobalCounterValue (
		@Argument(title="")  String counterName,
		 Container container)  throws StreamTransformationException{
		
		//AbstractTrace trace = container.getTrace();
		GlobalContainer glCon = container.getGlobalContainer();
		Integer counter = (Integer) glCon.getParameter(counterName);
		if (counter == null) {
			throw new IllegalStateException(
					"getGlobalCounter: No global counter with name " + " found");
		}
		String output = counter.toString();
		//trace.addInfo(" getGlobalCounter:  " + counterName + " has value ...: " + output);		

		return output;
	}

	@LibraryMethod(title="setParameter", description="sets a parameter; first value defines the name, the second one sets the value", category="UDFGlobalsPool", type=ExecutionType.SINGLE_VALUE) 
	public String setParameter (
		@Argument(title="")  String parameterName,
		@Argument(title="")  String parameterValue,
		 Container container)  throws StreamTransformationException{
				GlobalContainer glc = container.getGlobalContainer();
		glc.setParameter(parameterName, parameterValue);

		return(parameterValue);
	}

	@LibraryMethod(title="getParameter", description="returns the value of a parameter, identified by the argument", category="UDFGlobalsPool", type=ExecutionType.SINGLE_VALUE) 
	public String getParameter (
		@Argument(title="")  String parameterName,
		 Container container)  throws StreamTransformationException{
				GlobalContainer glc = container.getGlobalContainer();
		String value = (String) glc.getParameter(parameterName);

		return(value);
	}

	@LibraryMethod(title="increaseOrCreateGlobalCounterIfTrue", description="Increments the value of a global counter (identified by the second argument) by '1' for every value containing 'true' in the first argument; if needed the counter will be initialized with the thirs argument.", category="UDFGlobalsPool", type=ExecutionType.SINGLE_VALUE) 
	public String increaseOrCreateGlobalCounterIfTrue (
		@Argument(title="")  String booleanValue,
		@Argument(title="")  String parameterName,
		@Argument(title="")  int startValue,
		 Container container)  throws StreamTransformationException{
		
		int value = 0;
		if (booleanValue != null && Boolean.valueOf(
				booleanValue).booleanValue()) {
			GlobalContainer globalContainer = container.getGlobalContainer();
			value = startValue;
			Object object = globalContainer.getParameter(parameterName);
			if (object != null && object.toString().length() > 0) {
				// be considerate of 1.4, use trailing intValue() !
				value = ((Integer) object).intValue(); // instead of (Integer) object
				// If there has already been a value we try to iterate over it.
				value++; // Iterate +1
			}
			globalContainer.setParameter(
					parameterName, Integer.valueOf(value + ""));
		}
		return value + "";
	}

	@LibraryMethod(title="hasGlobalCounter", description="Returns 'true' if a counter (identified by the first argument) already exists, otherwise 'false'.", category="UDFGlobalsPool", type=ExecutionType.SINGLE_VALUE) 
	public String hasGlobalCounter (
		@Argument(title="")  String parameterName,
		 Container container)  throws StreamTransformationException{
			 GlobalContainer glCon = container.getGlobalContainer();
	 Object counter = glCon.getParameter(parameterName);
		return (counter == null) ? "false" : "true";
	}
}