package com.equalize.xpi.esr.mapping.java.abs;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.sap.aii.mapping.api.AbstractTransformation;
import com.sap.aii.mapping.api.StreamTransformationException;
import com.sap.aii.mapping.api.TransformationInput;
import com.sap.aii.mapping.api.TransformationOutput;

public class CustomDOM2Plain extends AbstractTransformation {

	@Override
	public void transform(TransformationInput input, TransformationOutput output)
			throws StreamTransformationException {
		AbstractMappingBase map = new CustomMap();
		map.process(input, output, this.getTrace());
	}

	// Local class with logic for output generation	
	class CustomMap extends AbstractDOM2Plain {
		@Override
		protected StringBuilder generateOutput(Document inDoc) throws StreamTransformationException {
			
			StringBuilder sb = new StringBuilder(); 
			Node inRoot = inDoc.getFirstChild(); //ns1:MT_Template_PurchasingContract
			Node inHeaderDirect = inRoot.getFirstChild().getFirstChild().getFirstChild(); //ns1:MT_Template_PurchasingContract/Contract/Header/Direct

			sb.append("Contract:");
			sb.append(getTextOfChildElement(inHeaderDirect, "ContractNumber"));
			sb.append("\n");

			ArrayList<Node> inItems = getMatchingChildNodes(inRoot.getFirstChild(), "Item"); //ns1:MT_Template_PurchasingContract/Contract/Item
			int totalQty = 0;
			for (Node item : inItems ) {
				Node inItemDirect = item.getFirstChild();
				sb.append("Material:");
				sb.append(getTextOfChildElement(inItemDirect, "MaterialNumber"));
				sb.append("\n");
				totalQty += Integer.parseInt(getTextOfChildElement(inItemDirect, "TargetQuantity"));
			}
			sb.append("TotalQuantity:");
			sb.append(totalQty);
			sb.append("\n");
			setPlainOutputEncoding("UTF-8");
			return new StringBuilder(sb.toString());			
		}
	}
}
