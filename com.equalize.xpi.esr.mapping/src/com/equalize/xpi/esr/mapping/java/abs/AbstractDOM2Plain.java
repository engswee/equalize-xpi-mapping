package com.equalize.xpi.esr.mapping.java.abs;

import java.io.InputStream;
import java.io.OutputStream;

import org.w3c.dom.Document;

import com.sap.aii.mapping.api.StreamTransformationException;

public abstract class AbstractDOM2Plain extends AbstractMappingBase{

	@Override
	protected void executeMappingSteps(InputStream is, OutputStream os) throws StreamTransformationException {
		try {
			Document inputDoc = parseInputXML(is);
			
			StringBuilder outSB = generateOutput(inputDoc);						
			os.write(outSB.toString().getBytes(plainOutputEncoding));
			
		} catch (Exception e) {
			trace.addInfo(e.getMessage());
			throw new StreamTransformationException("Mapping Exception: " + e.getMessage(), e);
		}
	}

	protected abstract StringBuilder generateOutput (Document inDoc) throws StreamTransformationException;
}
