package org.ddialliance.ddieditor.ui.model;

import java.util.List;

import org.ddialliance.ddi3.xml.xmlbeans.reusable.IDType;
import org.ddialliance.ddi3.xml.xmlbeans.reusable.ReferenceType;
import org.ddialliance.ddieditor.logic.identification.IdentificationManager;
import org.ddialliance.ddieditor.model.DdiManager;
import org.ddialliance.ddieditor.model.lightxmlobject.LightXmlObjectListDocument;
import org.ddialliance.ddieditor.model.lightxmlobject.LightXmlObjectType;
import org.ddialliance.ddiftp.util.DDIFtpException;
import org.ddialliance.ddiftp.util.ReflectionUtil;
import org.ddialliance.ddiftp.util.xml.XmlBeansUtil;

public class ModelAccessor {

	/**
	 * Does not set version and agency in reference
	 * 
	 * Use
	 * org.ddialliance.ddieditor.logic.identification.addReferenceInformation
	 * instead
	 * 
	 * @deprecated
	 * @see org.ddialliance.ddieditor.logic.identification.addReferenceInformation
	 */
	@Deprecated
	public static ReferenceType setReference(ReferenceType reference,
			LightXmlObjectType lightXmlObject) {
		try {
			IdentificationManager.getInstance().addReferenceInformation(reference,
					lightXmlObject);
		} catch (DDIFtpException e) {
			// do nothing
			e.printStackTrace();
		}
		return reference;
	}

	public static ReferenceType setReference(List<?> refList,
			ReferenceType reference, LightXmlObjectType refered) {
		if (refered != null && refered.getId().equals("")) {
			if (!refList.isEmpty()) {
				refList.remove(0);
			}
			return null;
		}
		return setReference(reference, refered);
	}

	static String quei = "QuestionItem";

	public static LightXmlObjectListDocument resolveReference(
			ReferenceType reference, String localName) throws DDIFtpException {
		if (localName.equals(quei)) {
			return resolveQuestionItem(reference);
		} else {
			return resolveReferenceImpl(reference, localName);
		}
	}

	private static LightXmlObjectListDocument resolveReferenceImpl(
			ReferenceType reference, String localName) throws DDIFtpException {
		StringBuilder operation = new StringBuilder("get");
		operation.append(localName);
		operation.append("sLight");

		LightXmlObjectListDocument lightXmlObjectList = null;
		try {
			lightXmlObjectList = (LightXmlObjectListDocument) ReflectionUtil
					.invokeMethod(
							DdiManager.getInstance(),
							operation.toString(),
							false,
							new Object[] {
									XmlBeansUtil
											.getTextOnMixedElement(reference
													.getIDArray(0)), "", "", "" });
		} catch (Exception e) {
			throw new DDIFtpException(e);
		}
		return lightXmlObjectList;
	}

	private static LightXmlObjectListDocument resolveQuestionItem(
			ReferenceType reference) throws DDIFtpException {
		LightXmlObjectListDocument queiDoc = resolveReferenceImpl(reference,
				quei);
		LightXmlObjectListDocument mqueDoc = resolveReferenceImpl(reference,
				"MultipleQuestionQuestionItem");

		queiDoc.getLightXmlObjectList()
				.getLightXmlObjectList()
				.addAll(mqueDoc.getLightXmlObjectList().getLightXmlObjectList());
		return queiDoc;
	}
}
