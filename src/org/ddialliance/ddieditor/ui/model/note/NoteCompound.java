package org.ddialliance.ddieditor.ui.model.note;

import java.util.ArrayList;
import java.util.List;

import org.ddialliance.ddi3.xml.xmlbeans.reusable.ContentDocument;
import org.ddialliance.ddi3.xml.xmlbeans.reusable.StructuredStringType;
import org.ddialliance.ddieditor.model.lightxmlobject.LightXmlObjectType;
import org.ddialliance.ddiftp.util.DDIFtpException;
import org.ddialliance.ddiftp.util.xml.XmlBeansUtil;

public class NoteCompound {
	public NoteStructure noteStructure;
	List<StructuredStringType> content;
	public LightXmlObjectType lightXmlObject;

	/**
	 * Note compound constructor
	 * 
	 * @param noteStructure
	 * @param content
	 * @param lightXmlObject
	 */
	public NoteCompound(NoteStructure noteStructure,
			List<StructuredStringType> content, LightXmlObjectType lightXmlObject) {
		this.noteStructure = noteStructure;
		this.content = content;
		this.lightXmlObject = lightXmlObject;
	}

	public static List<StructuredStringType> initLabelList() {
		return new ArrayList<StructuredStringType>();
	}

	public static StructuredStringType createLabel(String content)
			throws DDIFtpException {
		return createLabel(content, null, false, false);
	}

	public static StructuredStringType createLabel(String content, String lang,
			boolean isTranslated, boolean isTranslatable)
			throws DDIFtpException {
		StructuredStringType sst = StructuredStringType.Factory.newInstance();

		if (lang != null) {
			XmlBeansUtil.addTranslationAttributes(sst, lang, isTranslated,
					isTranslatable);
		}

		if (content != null) {
			XmlBeansUtil.setTextOnMixedElement(sst, content);
		}
		return sst;
	}
}
