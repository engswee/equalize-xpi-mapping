package com.equalize.xpi.esr.mapping.java;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.sap.aii.mapping.api.AbstractTransformation;
import com.sap.aii.mapping.api.Attachment;
import com.sap.aii.mapping.api.OutputAttachments;
import com.sap.aii.mapping.api.StreamTransformationException;
import com.sap.aii.mapping.api.TransformationInput;
import com.sap.aii.mapping.api.TransformationOutput;

public class UnzipAndAttach extends AbstractTransformation {
	private static final int DEF_BUFFER_SIZE = 8192;

	@Override
	public void transform(TransformationInput input, TransformationOutput output) throws StreamTransformationException {
		InputStream inStream = input.getInputPayload().getInputStream();
		OutputStream outStream = output.getOutputPayload().getOutputStream();
		OutputAttachments outAtt = output.getOutputAttachments();

		try {			
			// Copy input content to output content
			byte[] content = getInputStreamBytes(inStream);
			outStream.write(content);			

			// Unzip input file
			ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(content));	
			ZipEntry ze = null;
			// Loop through all entries in the zip file
			while((ze = zis.getNextEntry()) != null) {
				byte[] zipContent = getInputStreamBytes(zis);

				// Create attachment
				String name = ze.getName();
				String ctype = "text/plain;name=\"" + name +"\"";
				if (outAtt != null) {
					Attachment att = outAtt.create(name, ctype, zipContent);
					outAtt.setAttachment(att);
				}
				zis.closeEntry();
			}
			zis.close();

		} catch (Exception e) {
			throw new StreamTransformationException("Exception: " + e.getMessage(), e);
		}

	}

	public byte[] getInputStreamBytes(InputStream inStream) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[DEF_BUFFER_SIZE];
		int read = 0;
		while ((read = inStream.read(buffer, 0, buffer.length)) != -1) {
			baos.write(buffer, 0, read);
		}
		baos.flush();		
		return baos.toByteArray();
	}

}
