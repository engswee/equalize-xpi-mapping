package com.equalize.xpi.esr.mapping.java.abs;

import java.util.ArrayList;

import com.sap.aii.mapping.api.AbstractTransformation;
import com.sap.aii.mapping.api.StreamTransformationException;
import com.sap.aii.mapping.api.TransformationInput;
import com.sap.aii.mapping.api.TransformationOutput;

public class CustomPlain2Plain extends AbstractTransformation {

	@Override
	public void transform(TransformationInput input, TransformationOutput output)
			throws StreamTransformationException {
		AbstractMappingBase map = new CustomMap();
		map.process(input, output, this.getTrace());
	}

	// Local class with logic for output generation	
	class CustomMap extends AbstractPlain2Plain {
		@Override
		protected StringBuilder generateOutput(ArrayList<String> inContents) throws StreamTransformationException {
			
			StringBuilder sb = new StringBuilder(); 
			int count = 0;			
			for (String line : inContents) {
				if (count == 0) {
					sb.append("HD");
				} else if (count == inContents.size()-1) {
					sb.append("FT");
				} else {
					sb.append("IT");
				}
				sb.append(line);
				sb.append("\n");
				count++;
			}
			setPlainOutputEncoding("UTF-8");
			return new StringBuilder(sb.toString());
		}			
	}
}
