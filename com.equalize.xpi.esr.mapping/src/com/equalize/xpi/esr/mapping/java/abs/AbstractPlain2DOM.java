package com.equalize.xpi.esr.mapping.java.abs;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import org.w3c.dom.Document;

import com.sap.aii.mapping.api.StreamTransformationException;

public abstract class AbstractPlain2DOM extends AbstractMappingBase {

	@Override
	protected void executeMappingSteps(InputStream is, OutputStream os) throws StreamTransformationException {
		try {
			ArrayList<String> inputContents = parsePlainInput(is);
			Document outputDoc = getDocumentBuilder().newDocument();

			generateOutput(inputContents, outputDoc);	
			transformDocumentToStream(outputDoc, os, indentOutputXML);

		} catch (Exception e) {
			trace.addInfo(e.getMessage());
			throw new StreamTransformationException("Mapping Exception: " + e.getMessage(), e);
		}
	}

	protected abstract void generateOutput (ArrayList<String> inContents, Document outDoc) throws StreamTransformationException;
}
