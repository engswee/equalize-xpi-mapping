package com.equalize.xpi.esr.mapping.java.abs;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.sap.aii.mapping.api.AbstractTransformation;
import com.sap.aii.mapping.api.StreamTransformationException;
import com.sap.aii.mapping.api.TransformationInput;
import com.sap.aii.mapping.api.TransformationOutput;

public class CustomPlain2DOM extends AbstractTransformation {

	@Override
	public void transform(TransformationInput input, TransformationOutput output)
			throws StreamTransformationException {
		AbstractMappingBase map = new CustomMap();
		map.process(input, output, this.getTrace());
	}

	// Local class with logic for output generation	
	class CustomMap extends AbstractPlain2DOM {
		@Override
		protected void generateOutput(ArrayList<String> inContents, Document outDoc) throws StreamTransformationException {
			
			Node outRoot = outDoc.createElementNS("urn://equalize/JavaMap/Plain2DOM","ns1:MT_Plain2DOM");
			outDoc.appendChild(outRoot);

			Node outRecord = addElementToNode(outDoc, outRoot, "Record");
			int count = 0;			
			for (String line : inContents) {
				if (count == 0) {
					addElementToNode(outDoc, outRecord, "Header", line);
				} else if (count == inContents.size()-1) {
					addElementToNode(outDoc, outRecord, "Footer", line);
				} else {
					addElementToNode(outDoc, outRecord, "Item", line);
				}
				count++;		
			}					
			setIndentOutputXML(true);
		}

	}
}
