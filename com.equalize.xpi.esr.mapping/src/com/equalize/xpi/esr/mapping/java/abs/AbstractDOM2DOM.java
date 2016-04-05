package com.equalize.xpi.esr.mapping.java.abs;

import java.io.InputStream;
import java.io.OutputStream;

import org.w3c.dom.Document;

import com.sap.aii.mapping.api.StreamTransformationException;

public abstract class AbstractDOM2DOM extends AbstractMappingBase{
	protected void executeMappingSteps(InputStream is, OutputStream os) throws StreamTransformationException {
		try {
			Document inputDoc = parseInputXML(is);
			Document outputDoc = getDocumentBuilder().newDocument();

			generateOutput(inputDoc, outputDoc);
			transformDocumentToStream(outputDoc, os, indentOutputXML);

		} catch (Exception e) {
			trace.addInfo(e.getMessage());
			throw new StreamTransformationException("Mapping Exception: " + e.getMessage(), e);
		}
	}

	protected abstract void generateOutput (Document inDoc, Document outDoc) throws StreamTransformationException;
}
