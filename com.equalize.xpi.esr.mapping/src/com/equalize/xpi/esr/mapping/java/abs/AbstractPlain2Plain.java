package com.equalize.xpi.esr.mapping.java.abs;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import com.sap.aii.mapping.api.StreamTransformationException;

public abstract class AbstractPlain2Plain extends AbstractMappingBase {

	@Override
	protected void executeMappingSteps(InputStream is, OutputStream os) throws StreamTransformationException {
		try {			
			ArrayList<String> inputContents = parsePlainInput(is);
			StringBuilder outSB = generateOutput(inputContents);

			os.write(outSB.toString().getBytes(plainOutputEncoding));
			
		} catch (Exception e) {
			trace.addInfo(e.getMessage());
			throw new StreamTransformationException("Mapping Exception: " + e.getMessage(), e);
		}		
	}

	protected abstract StringBuilder generateOutput(ArrayList<String> inContents) throws StreamTransformationException;

}
