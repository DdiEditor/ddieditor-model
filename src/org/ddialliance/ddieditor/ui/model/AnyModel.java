package org.ddialliance.ddieditor.ui.model;

import org.apache.xmlbeans.XmlObject;
import org.ddialliance.ddiftp.util.DDIFtpException;

public class AnyModel extends Model {
	private XmlObject xmlObject;

	public AnyModel(String id, String version, String parentId,
			String parentVersion, String agency) {
		super(id, version, parentId, parentVersion, agency);
	}

	public void setDocument(XmlObject xmlObject) {
		this.xmlObject = xmlObject;
	}

	@Override
	public XmlObject getDocument() throws DDIFtpException {
		return xmlObject;
	}

	@Override
	public void executeChange(Object arg0, Class<?> arg1) throws Exception {
		// not implemented
	}
}
