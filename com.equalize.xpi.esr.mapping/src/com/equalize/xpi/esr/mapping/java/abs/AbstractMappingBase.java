package com.equalize.xpi.esr.mapping.java.abs;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sap.aii.mapping.api.AbstractTrace;
import com.sap.aii.mapping.api.DynamicConfiguration;
import com.sap.aii.mapping.api.StreamTransformationException;
import com.sap.aii.mapping.api.TransformationInput;
import com.sap.aii.mapping.api.TransformationOutput;

public abstract class AbstractMappingBase {
	protected TransformationInput input;
	protected TransformationOutput output;
	protected AbstractTrace trace;
	protected DynamicConfiguration dynConfig;
	protected DocumentBuilder docBuilder;
	protected String plainOutputEncoding = "UTF-8";
	protected boolean indentOutputXML = false;

	public void process (TransformationInput input, TransformationOutput output, AbstractTrace trace) throws StreamTransformationException {
		this.trace = trace;	
		this.input = input;
		this.output = output;
		this.dynConfig = input.getDynamicConfiguration();
		
		InputStream is = input.getInputPayload().getInputStream();
		OutputStream os = output.getOutputPayload().getOutputStream();
		executeMappingSteps(is, os);
	}

	protected abstract void executeMappingSteps (InputStream is, OutputStream os) throws StreamTransformationException;

	protected void setPlainOutputEncoding(String encoding) {
		this.plainOutputEncoding = encoding;
	}

	protected void setIndentOutputXML(boolean indentOutputXML) {
		this.indentOutputXML = indentOutputXML;
	}
	
	//protected abstract AbstractParsedInput parseInput (InputStream inStream) throws Exception;

	protected Document parseInputXML (InputStream inStream) throws Exception {
		//-------------------------------------------------------
		// Create DOM parser and parse the input stream
		//-------------------------------------------------------
		return getDocumentBuilder().parse(collapseIndentedXMLStream(inStream));		
	}

	protected ArrayList<String> parsePlainInput (InputStream inStream) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(inStream));		
		String lineContent;
		ArrayList<String> contents = new ArrayList<String>(10);
		while ((lineContent = br.readLine()) != null) {
			contents.add(lineContent);
		}
		br.close();
		return contents;
	}

	protected void transformDocumentToStream (Document doc, OutputStream outStream, boolean setIndent) throws Exception {
		//-------------------------------------------------------
		// Transform the DOM to Stream
		//-------------------------------------------------------
		javax.xml.transform.Transformer transformer = TransformerFactory.newInstance().newTransformer();
		if (setIndent) { 
			transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
		}
		transformer.transform(new DOMSource(doc), new StreamResult(outStream)); 
	}

	protected DocumentBuilder getDocumentBuilder() throws Exception {
		if (docBuilder == null) {
			docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		}
		return docBuilder;				
	}

	protected String getTextOfChildElement (Node parentNode, String elementName) {
		NodeList nl = parentNode.getChildNodes();
		String elementTextValue = "";
		for (int i = 0; i < nl.getLength(); i++) {			
			if (nl.item(i).getNodeName().equals(elementName)) {
				elementTextValue = nl.item(i).getFirstChild().getNodeValue();
				break;
			}
		}
		return elementTextValue;
	}

	protected ArrayList<Node> getMatchingChildNodes ( Node parentNode, String childName ) {
		ArrayList<Node> childNodes = new ArrayList<Node>(10);
		NodeList nl = parentNode.getChildNodes();
		for ( int i = 0; i < nl.getLength(); i++) {
			if ( nl.item(i).getNodeName().equals(childName)) {
				childNodes.add(nl.item(i));
			}
		}
		return childNodes;
	}

	protected Node addElementToNode (Document doc, Node parentNode, String elementName) {
		Node element = doc.createElement(elementName);		
		parentNode.appendChild(element);
		return element;
	}
	
	protected Node addElementToNode (Document doc, Node parentNode, String elementName, String elementTextValue) {
		Node element = addElementToNode(doc, parentNode, elementName);
		if (elementTextValue != null) {
			element.appendChild(doc.createTextNode(elementTextValue));
		}		
		return element;
	}

	private InputStream collapseIndentedXMLStream (InputStream inStream) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(inStream)); 
		StringBuilder sb = new StringBuilder();
		String lineContent;
		while ((lineContent = br.readLine()) != null) {
			sb.append(lineContent);
		}
		br.close();
		// Remove all whitespaces between > and  <
		String consolidatedLine = sb.toString().replaceAll(">\\W+<", "><");
		return new ByteArrayInputStream(consolidatedLine.getBytes());
	}
}
