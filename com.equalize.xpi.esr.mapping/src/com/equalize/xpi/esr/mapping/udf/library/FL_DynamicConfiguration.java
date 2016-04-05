package com.equalize.xpi.esr.mapping.udf.library;

import com.sap.aii.mapping.api.*;
import java.io.*;
import java.util.*;
import com.sap.aii.mappingtool.tf7.rt.*;
import com.sap.aii.mapping.lookup.*;
import java.lang.reflect.*;
import com.sap.ide.esr.tools.mapping.core.LibraryMethod;
import com.sap.ide.esr.tools.mapping.core.ExecutionType;
import com.sap.ide.esr.tools.mapping.core.Argument;
import com.sap.ide.esr.tools.mapping.core.Init;
import com.sap.ide.esr.tools.mapping.core.Cleanup;


public class FL_DynamicConfiguration  {



	@Init(description="") 
	public void init (
		 GlobalContainer container)  throws StreamTransformationException{
		
	}

	@Cleanup 
	public void cleanup (
		 GlobalContainer container)  throws StreamTransformationException{
		
	}

	@LibraryMethod(title="setDynamicConfigGenericAttribute", description="Set dynamic configuration attribute", category="FL_DynCfg", type=ExecutionType.SINGLE_VALUE) 
	public String setDynamicConfigGenericAttribute (
		@Argument(title="Namespace")  String namespace,
		@Argument(title="Attribute")  String attribute,
		@Argument(title="Value")  String value,
		 Container container)  throws StreamTransformationException{
			// ----------------------------------------------------------
		// Sets the attribute value in dynamic configuration				
		// ----------------------------------------------------------
		if (namespace.equals("") ) {
			throw new RuntimeException("Dynamic Configuration namespace not populated");
		}
		if (attribute.equals("") ) {
			throw new RuntimeException("Dynamic Configuration attribute not populated");
		}

		DynamicConfiguration conf = (DynamicConfiguration) container.getTransformationParameters().get(StreamTransformationConstants.DYNAMIC_CONFIGURATION);

		if (conf != null) { 
			// Key
			DynamicConfigurationKey dynCfgKey = DynamicConfigurationKey.create( namespace, attribute );
			// Store in Dyn Config
			conf.put(dynCfgKey, value);
			return value;
		} else { //No dynamic configuration in test mode
			return value;
		}
	}

	@LibraryMethod(title="getDynamicConfigGenericAttribute", description="Get dynamic configuration attribute", category="FL_DynCfg", type=ExecutionType.SINGLE_VALUE) 
	public String getDynamicConfigGenericAttribute (
		@Argument(title="Namespace")  String namespace,
		@Argument(title="Attribute")  String attribute,
		 Container container)  throws StreamTransformationException{
			// ----------------------------------------------------------
		// Get the attribute value in dynamic configuration				
		// ----------------------------------------------------------
		if (namespace.equals("") ) {
			throw new RuntimeException("Dynamic Configuration namespace not populated");
		}
		if (attribute.equals("") ) {
			throw new RuntimeException("Dynamic Configuration field not populated");
		}

		DynamicConfiguration conf = (DynamicConfiguration) container.getTransformationParameters().get(StreamTransformationConstants.DYNAMIC_CONFIGURATION);

		if (conf != null) { 
			// Key
			DynamicConfigurationKey dynCfgKey = DynamicConfigurationKey.create( namespace, attribute );
			// Get from Dyn Config
			return conf.get(dynCfgKey);
		} else { //No dynamic configuration in test mode
			return "Test Mode";
		}
	}

	@LibraryMethod(title="setFilename", description="Set dynamic configuration filename", category="FL_DynCfg", type=ExecutionType.SINGLE_VALUE) 
	public String setFilename (
		@Argument(title="File name")  String filename,
		 Container container)  throws StreamTransformationException{
			// ----------------------------------------------------------
		// Set filename value in dynamic configuration				
		// ----------------------------------------------------------
		return setDynamicConfigGenericAttribute ("http://sap.com/xi/XI/System/File", "FileName", filename, container);
	}

	@LibraryMethod(title="getFilename", description="Get dynamic configuration filename", category="FL_DynCfg", type=ExecutionType.SINGLE_VALUE) 
	public String getFilename (
		 Container container)  throws StreamTransformationException{
			// ----------------------------------------------------------
		// Get filename value in dynamic configuration				
		// ----------------------------------------------------------
		return getDynamicConfigGenericAttribute("http://sap.com/xi/XI/System/File", "FileName", container);
	}

	@LibraryMethod(title="setDirectory", description="Set dynamic configuration directory", category="FL_DynCfg", type=ExecutionType.SINGLE_VALUE) 
	public String setDirectory (
		@Argument(title="Directory")  String directory,
		 Container container)  throws StreamTransformationException{
			// ----------------------------------------------------------
		// Set directory value in dynamic configuration				
		// ----------------------------------------------------------
		return setDynamicConfigGenericAttribute ("http://sap.com/xi/XI/System/File", "Directory", directory, container);
	}

	@LibraryMethod(title="getDirectory", description="Set dynamic configuration directory", category="FL_DynCfg", type=ExecutionType.SINGLE_VALUE) 
	public String getDirectory (
		 Container container)  throws StreamTransformationException{
			// ----------------------------------------------------------
		// Get directory value in dynamic configuration				
		// ----------------------------------------------------------
		return getDynamicConfigGenericAttribute("http://sap.com/xi/XI/System/File", "Directory", container);
	}
	
	public String getPIMessageID(Container container)  throws StreamTransformationException {
		return container.getInputHeader().getMessageId();
	}
}