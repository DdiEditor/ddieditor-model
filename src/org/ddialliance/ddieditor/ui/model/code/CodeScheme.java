package org.ddialliance.ddieditor.ui.model.code;

import java.util.List;

import org.ddialliance.ddi3.xml.xmlbeans.logicalproduct.CodeSchemeDocument;
import org.ddialliance.ddi3.xml.xmlbeans.logicalproduct.CodeType;
import org.ddialliance.ddi3.xml.xmlbeans.reusable.ReferenceType;
import org.ddialliance.ddi3.xml.xmlbeans.reusable.SchemeReferenceType;
import org.ddialliance.ddieditor.logic.identification.IdentificationManager;
import org.ddialliance.ddieditor.model.lightxmlobject.LightXmlObjectType;
import org.ddialliance.ddieditor.ui.model.IModel;
import org.ddialliance.ddieditor.ui.model.Model;
import org.ddialliance.ddiftp.util.DDIFtpException;
import org.ddialliance.ddiftp.util.log.Log;
import org.ddialliance.ddiftp.util.log.LogFactory;
import org.ddialliance.ddiftp.util.log.LogType;
import org.ddialliance.ddiftp.util.xml.XmlBeansUtil;

/**
 * Code Scheme model
 */
public class CodeScheme extends Model implements IModel {
	private static Log log = LogFactory
			.getLog(LogType.SYSTEM, CodeScheme.class);
	private CodeSchemeDocument doc;

	/**
	 * Constructor
	 * 
	 * @param questionItemDocument
	 * @param parentId
	 * @param parentVersion
	 * @throws Exception
	 */
	public CodeScheme(CodeSchemeDocument doc, String parentId,
			String parentVersion) throws Exception {

		super(doc.getCodeScheme(), parentId, parentVersion);

		if (doc == null) {
			this.doc = CodeSchemeDocument.Factory.newInstance();
			// add id and version
			setId("");
			setVersion("");
		} else {
			this.doc = doc;
		}
	}

	public SchemeReferenceType getCategorySchemeReference() {
		return doc.getCodeScheme().getCategorySchemeReference();
	}

	/**
	 * Get ID of Default Category Scheme
	 * 
	 * @return String
	 */
	public String getDefaultCategorySchemeID() {
		SchemeReferenceType categoryScheme = getCategorySchemeReference();
		if (categoryScheme == null || categoryScheme.getIDList().size() == 0) {
			return null;
		}
		return XmlBeansUtil.getTextOnMixedElement(categoryScheme.getIDList()
				.get(0));
	}

	/**
	 * 
	 * @param index
	 *            index in list of code
	 * @param categoryReference
	 * @param value
	 */
	public void addCode(int index, String categoryReference, String agency, String value) {

		if (doc.getCodeScheme() == null) {
			doc.addNewCodeScheme();
		}

		if ((index < 0)
				|| (doc.getCodeScheme().getCodeList().size() == 0 && index != 0)
				|| (doc.getCodeScheme().getCodeList().size() > index)) {
			// TODO error reporting
			System.out.println("Index error");
		}
		CodeType codeType = CodeType.Factory.newInstance();
		codeType.addNewCategoryReference().addNewID()
				.setStringValue(categoryReference);
		codeType.getCategoryReference().addIdentifyingAgency(agency);
		codeType.setValue(value);
		doc.getCodeScheme().getCodeList().add(index, codeType);
	}

	public void addCode(String categoryReference, String agency, String value) {
		int index = -1;
		if (doc.getCodeScheme() == null) {
			index = 0;
		} else {
			index = doc.getCodeScheme().getCodeList().size();
		}
		addCode(index, categoryReference, agency, value);
	}

	public List<CodeType> getCodes() {
		return doc.getCodeScheme().getCodeList();
	}

	@Override
	public void validate() throws Exception {
		// not implemented
	}

	@Override
	public void executeChange(Object value, Class<?> type) throws Exception {
		// default cats reference
		if (type.equals(ReferenceType.class)) {
			// remove reference
			if (value == null || ((LightXmlObjectType) value).getId() == null
					|| ((LightXmlObjectType) value).getId().equals("")) {
				if (doc.getCodeScheme().isSetCategorySchemeReference()) {
					doc.getCodeScheme().unsetCategorySchemeReference();
				}
				return;
			}

			// add reference
			LightXmlObjectType lightXmlObjectType = (LightXmlObjectType) value;
			SchemeReferenceType ref = getCategorySchemeReference();
			if (ref == null) {
				ref = doc.getCodeScheme().addNewCategorySchemeReference();
			}
			IdentificationManager.getInstance().addReferenceInformation(ref,
					lightXmlObjectType);
		} else {
			throw new Exception("type not supported: "
					+ type.getClass().getName());
		}
	}

	@Override
	public CodeSchemeDocument getDocument() throws DDIFtpException {
		return doc;
	}
}
