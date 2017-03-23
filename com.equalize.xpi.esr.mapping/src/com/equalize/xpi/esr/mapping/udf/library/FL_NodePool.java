package com.equalize.xpi.esr.mapping.udf.library;

import java.util.ArrayList;

import com.sap.aii.mapping.api.StreamTransformationException;
import com.sap.aii.mappingtool.tf7.rt.Container;
import com.sap.aii.mappingtool.tf7.rt.GlobalContainer;
import com.sap.aii.mappingtool.tf7.rt.ResultList;
import com.sap.ide.esr.tools.mapping.core.Argument;
import com.sap.ide.esr.tools.mapping.core.Cleanup;
import com.sap.ide.esr.tools.mapping.core.ExecutionType;
import com.sap.ide.esr.tools.mapping.core.Init;
import com.sap.ide.esr.tools.mapping.core.LibraryMethod;
import com.sap.ide.esr.tools.mapping.core.Parameter;

public class FL_NodePool {

	@Init(description = "")
	public void init(GlobalContainer container)
			throws StreamTransformationException {

	}

	@Cleanup
	public void cleanup(GlobalContainer container)
			throws StreamTransformationException {

	}

	@LibraryMethod(title = "formatByParentContext", description = "Format context according to parent context", category = "FL_Node", type = ExecutionType.ALL_VALUES_OF_QUEUE)
	public void formatByParentContext(
			@Parameter(title = "Child value") String[] lineValue,
			@Parameter(title = "Parent lines") String[] parentLine,
			@Parameter(title = "Output") ResultList result,
			Container container) throws StreamTransformationException {

		int nextLine = 0;

		for (int i = 0; i < parentLine.length; i++) {
			if (parentLine[i].equals(ResultList.CC)) {
				result.addContextChange();
			} else {
				for (int j = nextLine; j < lineValue.length; j++) {
					if (lineValue[j].equals(ResultList.CC)) {
						nextLine = j + 1;
						break;
					} else {
						result.addValue(lineValue[j]);
					}
				}
			}
		}
		
	}
	@LibraryMethod(title="useOneAsManyWithEmptyContext", description="Use one as many, accepting null/empty contexts", category="FL_Node", type=ExecutionType.ALL_VALUES_OF_QUEUE) 
	public void useOneAsManyWithEmptyContext (
		@Argument(title="Parent Items to be repeated")  String[] parentList,
		@Argument(title="Context")  String[] contextList,
		@Argument(title="Child items")  String[] childList,
		 ResultList result,
		 Container container)  throws StreamTransformationException{
		
		
		ArrayList<String> parentContentsWithoutCC = new ArrayList<String>();
		ArrayList<String> parentItemsPerContext = new ArrayList<String>();
		int parentListCounter = 0;				
		int contextCounter = 0;
		
		for(String parentItem: parentList) {
			if(!parentItem.equals(ResultList.CC)) {
				parentContentsWithoutCC.add(parentItem);
			}
		}
		
		for(String contextItem: contextList) {
			if(!contextItem.equals(ResultList.CC)) {
				parentItemsPerContext.add(parentContentsWithoutCC.get(parentListCounter));
			} else {
				parentListCounter++;
			}
		}
		
		for(int i = 0; i < childList.length; i++) {
			if(childList[i].equals(ResultList.CC)) {
				result.addContextChange();
			} else {
				result.addValue(parentItemsPerContext.get(contextCounter));
				contextCounter++;
			}
		}
	}
}