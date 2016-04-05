package com.equalize.xpi.esr.mapping.java.abs;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.sap.aii.mapping.api.AbstractTransformation;
import com.sap.aii.mapping.api.StreamTransformationException;
import com.sap.aii.mapping.api.TransformationInput;
import com.sap.aii.mapping.api.TransformationOutput;

public class CustomDOM2DOM extends AbstractTransformation{

	@Override
	public void transform(TransformationInput input, TransformationOutput output)
			throws StreamTransformationException {
		AbstractMappingBase map = new CustomMap();
		map.process(input, output, this.getTrace());
	}

	// Local class with logic for output generation
	class CustomMap extends AbstractDOM2DOM {
		@Override
		protected void generateOutput(Document inDoc, Document outDoc) throws StreamTransformationException {

			Node outRoot = outDoc.createElementNS("urn://equalize/JavaMap/DOM2DOM","ns1:MT_DOM2DOM");
			outDoc.appendChild(outRoot);
			Node outRecord = addElementToNode(outDoc, outRoot, "Record");

			Node inRoot = inDoc.getFirstChild(); //ns1:MT_Template_PurchasingContract
			Node inHeaderDirect = inRoot.getFirstChild().getFirstChild().getFirstChild(); //ns1:MT_Template_PurchasingContract/Contract/Header/Direct

			addElementToNode(outDoc, outRecord, "ContractNumber", getTextOfChildElement(inHeaderDirect, "ContractNumber"));

			ArrayList<Node> inItems = getMatchingChildNodes(inRoot.getFirstChild(), "Item"); //ns1:MT_Template_PurchasingContract/Contract/Item
			for (Node item : inItems ) {
				Node inItemDirect = item.getFirstChild();
				addElementToNode(outDoc, outRecord, "Material", getTextOfChildElement(inItemDirect, "MaterialNumber"));
			}
			setIndentOutputXML(true);
		}
	}
}
