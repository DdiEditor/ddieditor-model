package org.ddialliance.ddieditor.ui.model.question;

/**
 * Question Item model.
 * 
 */
/*
 * $Author: ddadak $ 
 * $Date: 2011-08-10 09:10:50 +0200 (ons, 10 aug 2011) $ 
 * $Revision: 2677 $
 */

import java.math.BigInteger;
import java.util.List;

import org.ddialliance.ddi3.xml.xmlbeans.datacollection.CodeDomainDocument;
import org.ddialliance.ddi3.xml.xmlbeans.datacollection.CodeDomainType;
import org.ddialliance.ddi3.xml.xmlbeans.datacollection.DateTimeDomainDocument;
import org.ddialliance.ddi3.xml.xmlbeans.datacollection.DateTimeDomainType;
import org.ddialliance.ddi3.xml.xmlbeans.datacollection.DynamicTextType;
import org.ddialliance.ddi3.xml.xmlbeans.datacollection.LiteralTextDocument;
import org.ddialliance.ddi3.xml.xmlbeans.datacollection.LiteralTextType;
import org.ddialliance.ddi3.xml.xmlbeans.datacollection.NumericDomainDocument;
import org.ddialliance.ddi3.xml.xmlbeans.datacollection.NumericDomainType;
import org.ddialliance.ddi3.xml.xmlbeans.datacollection.QuestionItemDocument;
import org.ddialliance.ddi3.xml.xmlbeans.datacollection.TextDomainDocument;
import org.ddialliance.ddi3.xml.xmlbeans.datacollection.TextType;
import org.ddialliance.ddi3.xml.xmlbeans.logicalproduct.CodeRepresentationType;
import org.ddialliance.ddi3.xml.xmlbeans.reusable.DateTimeRepresentationType;
import org.ddialliance.ddi3.xml.xmlbeans.reusable.DateTypeCodeType;
import org.ddialliance.ddi3.xml.xmlbeans.reusable.NameType;
import org.ddialliance.ddi3.xml.xmlbeans.reusable.NumericRepresentationType;
import org.ddialliance.ddi3.xml.xmlbeans.reusable.NumericTypeCodeType;
import org.ddialliance.ddi3.xml.xmlbeans.reusable.ReferenceType;
import org.ddialliance.ddi3.xml.xmlbeans.reusable.RepresentationType;
import org.ddialliance.ddi3.xml.xmlbeans.reusable.TextDomainType;
import org.ddialliance.ddi3.xml.xmlbeans.reusable.TextRepresentationType;
import org.ddialliance.ddieditor.model.lightxmlobject.LightXmlObjectType;
import org.ddialliance.ddieditor.ui.model.Model;
import org.ddialliance.ddieditor.ui.model.ModelAccessor;
import org.ddialliance.ddieditor.ui.model.ModelIdentifingType;
import org.ddialliance.ddiftp.util.DDIFtpException;
import org.ddialliance.ddiftp.util.Translator;
import org.ddialliance.ddiftp.util.log.Log;
import org.ddialliance.ddiftp.util.log.LogFactory;
import org.ddialliance.ddiftp.util.log.LogType;
import org.ddialliance.ddiftp.util.xml.XmlBeansUtil;

public class QuestionItem extends Model {
	private static Log log = LogFactory.getLog(LogType.SYSTEM,
			QuestionItem.class);
	private QuestionItemDocument doc;
	private String originalLanguageCode = null;

	/**
	 * Constructor
	 * 
	 * @param questionItemDocument
	 * @param parentId
	 * @param parentVersion
	 * @throws Exception
	 */
	public QuestionItem(QuestionItemDocument doc, String parentId,
			String parentVersion) throws Exception {

		super(doc.getQuestionItem(), parentId, parentVersion);

		if (doc == null) {
			this.doc = QuestionItemDocument.Factory.newInstance();
			// add id and version
			setId("");
			setVersion("");
		} else {
			this.doc = doc;
		}
	}

	/**
	 * 
	 * Get Concept reference
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String getConceptRef() throws Exception {
		String conceptRef = "";
		List<ReferenceType> conceptReferenceList = doc.getQuestionItem()
				.getConceptReferenceList();
		if (conceptReferenceList.size() == 1) {
			conceptRef = XmlBeansUtil
					.getTextOnMixedElement(conceptReferenceList.get(0));
		} else if (conceptReferenceList.size() > 1) {
			throw new Exception(
					Translator
							.trans("QuestionItem.mess.MultipleConceptReferencesNotSupported")); //$NON-NLS-1$
		}
		return conceptRef;
	}

	public ReferenceType getConceptReferenceType() {
		List<ReferenceType> conceptReferenceList = doc.getQuestionItem()
				.getConceptReferenceList();
		if (conceptReferenceList.isEmpty()) {
			ReferenceType ref = doc.getQuestionItem().addNewConceptReference();
			ref.addNewID();
			return ref;
		}
		return conceptReferenceList.get(0);
	}

	/**
	 * Set Concept reference
	 * 
	 * @param conceptRef
	 * @throws Exception
	 */
	public void setConceptRef(String conceptRef) throws Exception {
		if (doc.getQuestionItem().getConceptReferenceList().size() > 1) {
			throw new Exception(
					Translator
							.trans("QuestionItem.mess.MultipleConceptReferencesNotSupported")); //$NON-NLS-1$
		}

		if (doc.getQuestionItem().getConceptReferenceList().size() == 1) {
			doc.getQuestionItem().removeConceptReference(0);
		}
		doc.getQuestionItem().addNewConceptReference().addNewID()
				.setStringValue(conceptRef);
	}

	/**
	 * Get Question Items
	 * 
	 * @return list of dynamic texts
	 * @throws DDIFtpException
	 */
	public List<DynamicTextType> getQuestionText() throws DDIFtpException {
		if (doc.getQuestionItem().getQuestionTextList().isEmpty() && create) {
			DynamicTextType dynamicText = doc.getQuestionItem()
					.addNewQuestionText();
			TextType textType = dynamicText.addNewText();
			XmlBeansUtil.addTranslationAttributes(dynamicText,
					Translator.getLocaleLanguage(), false, true);
			LiteralTextType lTextType = (LiteralTextType) textType.substitute(
					LiteralTextDocument.type.getDocumentElementName(),
					LiteralTextType.type);
			lTextType.addNewText();
		}
		return (doc.getQuestionItem().getQuestionTextList());
	}

	/**
	 * Get Response Domain of Question Item.
	 * 
	 * @return RepresentationType
	 */
	public RepresentationType getResponseDomain() {
		return doc.getQuestionItem().getResponseDomain();
	}
	
	// missing value
	public List getMissingValue() {		
		if (getResponseDomain() instanceof CodeDomainType) {
			return ((CodeDomainType) getResponseDomain())
					.getMissingValue();
		} if ((getResponseDomain() instanceof NumericDomainType)) {
			return ((NumericDomainType) getResponseDomain())
					.getMissingValue();
		} if ((getResponseDomain() instanceof TextDomainType)) {
			return ((TextDomainType) getResponseDomain())
					.getMissingValue();
		}  if ((getResponseDomain() instanceof DateTimeDomainType)) {
			return ((DateTimeDomainType) getResponseDomain())
					.getMissingValue();
		}
		return null;
	}
	
	// numeric rep
	public BigInteger getNumericDecimalPosition() {
		if (!(getResponseDomain() instanceof NumericRepresentationType)) {
			log.warn("Not setting NumericRepresentation.type as ValueRepresentation is of type: "
					+ getResponseDomain().getClass().getName());
			return null;
		}
		return ((NumericRepresentationType) getResponseDomain())
				.getDecimalPositions();
	}
	
	// text rep
	public BigInteger getMaxLength() {
		if (!(getResponseDomain() instanceof TextRepresentationType)) {
			log.warn("Not setting TextRepresentation.maxlenght as ValueRepresentation is of type: "
					+ getResponseDomain().getClass().getName());
			return null;
		}
		return ((TextRepresentationType) getResponseDomain())
				.getMaxLength();
	}

	public BigInteger getMinLength() {
		if (!(getResponseDomain() instanceof TextRepresentationType)) {
			log.warn("Not setting TextRepresentation.minlength as ValueRepresentation is of type: "
					+ getResponseDomain().getClass().getName());
			return null;
		}
		return ((TextRepresentationType) getResponseDomain())
				.getMinLength();
	}
	
	public String getRegx() {
		if (!(getResponseDomain() instanceof TextRepresentationType)) {
			log.warn("Not setting TextRepresentation.regx as ValueRepresentation is of type: "
					+ getResponseDomain().getClass().getName());
			return null;
		}
		return ((TextRepresentationType) getResponseDomain()).getRegExp();
	}
	
	// date time rep
	public String getFormat() {
		if (!(getResponseDomain() instanceof DateTimeRepresentationType)) {
			log.warn("Not setting DateTimeRepresentation.format as ValueRepresentation is of type: "
					+ getResponseDomain().getClass().getName());
			return null;
		}
		return ((DateTimeRepresentationType) getResponseDomain())
				.getFormat();
	}

	public DateTypeCodeType.Enum getDateTimeType() {
		if (!(getResponseDomain() instanceof DateTimeRepresentationType)) {
			log.warn("Not setting DateTimeRepresentation.format as ValueRepresentation is of type: "
					+ getResponseDomain().getClass().getName());
			return null;
		}
		return ((DateTimeRepresentationType) getResponseDomain())
				.getType();
	}

	/**
	 * Set Question Item Code Response Domain
	 * 
	 * @param codeSchemeReference
	 * @return RepresentationType
	 */
	public RepresentationType setCodeResponseDomain(String codeSchemeReference) {
		if (doc.getQuestionItem().getResponseDomain() != null) {
			doc.getQuestionItem().unsetResponseDomain();
		}
		RepresentationType rt = doc.getQuestionItem().addNewResponseDomain();
		CodeDomainType cdt = (CodeDomainType) rt.substitute(
				CodeDomainDocument.type.getDocumentElementName(),
				CodeDomainType.type);
		cdt.addNewCodeSchemeReference().addNewID()
				.setStringValue(codeSchemeReference);
		return cdt;
	}

	/**
	 * Set Question Item Text Response Domain
	 * 
	 * @param maxLength
	 * @return RepresentationType
	 */
	public RepresentationType setTextResponseDomain(BigInteger maxLength) {
		if (doc.getQuestionItem().getResponseDomain() != null) {
			doc.getQuestionItem().unsetResponseDomain();
		}
		RepresentationType rt = doc.getQuestionItem().addNewResponseDomain();
		TextDomainType tdt = (TextDomainType) rt.substitute(
				TextDomainDocument.type.getDocumentElementName(),
				TextDomainType.type);
		tdt.setMaxLength(maxLength);
		return tdt;
	}

	/**
	 * Set Question Item Numeric Response Domain
	 * 
	 * @param type
	 *            (integer, double or float)
	 * @param decimalPosition
	 *            (not used if integer)
	 * @return RepresentationType
	 */
	public RepresentationType setNumericResponseDomain(
			NumericTypeCodeType.Enum type, BigInteger decimalPosition) {

		if (doc.getQuestionItem().getResponseDomain() != null) {
			doc.getQuestionItem().unsetResponseDomain();
		}
		RepresentationType rt = doc.getQuestionItem().addNewResponseDomain();
		NumericDomainType ndt = (NumericDomainType) rt.substitute(
				NumericDomainDocument.type.getDocumentElementName(),
				NumericDomainType.type);
		ndt.setType(type);
		if (type == NumericTypeCodeType.DOUBLE
				|| type == NumericTypeCodeType.FLOAT) {
			if (decimalPosition != null) {
				ndt.setDecimalPositions(decimalPosition);
			}
		} else {
			if (ndt.getDecimalPositions() != null) {
				ndt.unsetDecimalPositions();
			}
		}
		return ndt;
	}

	/**
	 * Set new Question Item Response Domain
	 * 
	 * @param responseType
	 * @param value
	 * @return RepresentationType or null - if unsupported response type
	 */
	public RepresentationType setResponseDomain(ResponseType responseType,
			String value) {

		if (doc.getQuestionItem().getResponseDomain() != null) {
			doc.getQuestionItem().unsetResponseDomain();
		}
		RepresentationType rt = doc.getQuestionItem().addNewResponseDomain();
		if (responseType == ResponseType.UNDEFINED) {
			return rt;
		} 
		// code domain
		else if (responseType == ResponseType.CODE) {
			CodeDomainType cdt = (CodeDomainType) rt.substitute(
					CodeDomainDocument.type.getDocumentElementName(),
					CodeDomainType.type);
			cdt.addNewCodeSchemeReference().addNewID().setStringValue(value);
			return cdt;
			// text domain
		} else if (responseType == ResponseType.TEXT) {
			TextDomainType tdt = (TextDomainType) rt.substitute(
					TextDomainDocument.type.getDocumentElementName(),
					TextDomainType.type);
			return tdt;
			// numeric domain
		} else if (responseType == ResponseType.NUMERIC) {
			NumericDomainType ndt = (NumericDomainType) rt.substitute(
					NumericDomainDocument.type.getDocumentElementName(),
					NumericDomainType.type);
			ndt.setType(null);
			return ndt;
			// date time domain
		} else if (responseType == ResponseType.DATE) {
			DateTimeDomainType ddt = (DateTimeDomainType) rt.substitute(
					DateTimeDomainDocument.type.getDocumentElementName(),
					DateTimeDomainType.type);
			ddt.setType(null);
			return ddt;
			// category domain
		} else if (responseType == ResponseType.CATEGORY) {
			// TODO Support Category
			return null;
			// geographic domain
		} else if (responseType == ResponseType.GEOGRAPHIC) {
			// TODO Support Geographic
			return null;
		} else {
			// TODO Error handling
			return null;
		}
	}

	/**
	 * Get Question Item Document of Question Item.
	 * 
	 * @return QuestionItemDocument
	 */
	@Override
	public QuestionItemDocument getDocument() {
		return doc;
	}

	/**
	 * Validates the Question Item before it is saved. It e.g. checks if all
	 * mandatory attributes has been given.
	 * 
	 * @throws Exception
	 */
	public void validate() throws Exception {
		// Check if a Response Domain has been given
		RepresentationType rt = getResponseDomain();
		if (rt == null) {
			throw new Exception(
					Translator
							.trans("QuestionItem.mess.QuestionResponseTypeHasNotBeenSpecified")); //$NON-NLS-1$
		}
		String sn = rt.getClass().getSimpleName();
		if (sn.equals("RepresentationTypeImpl")) {
			throw new Exception(
					Translator
							.trans("QuestionItem.mess.QuestionResponseTypeHasNotBeenSpecified")); //$NON-NLS-1$
		} else if (sn.equals("CodeDomainTypeImpl")) {
			try {
				XmlBeansUtil.getTextOnMixedElement(rt);
			} catch (Exception e) {
				throw new Exception(
						Translator
								.trans("QuestionItem.mess.QuestionResponseCodeReferenceNotBeenSpecified")); //$NON-NLS-1$
			}
		} else if (sn.equals("TextDomainTypeImpl")) {
			// Nothing to check
		} else if (sn.equals("NumericDomainTypeImpl")) {
			// Nothing to check
		}

		// Check if Language has been given for all Question Texts
		DynamicTextType dynamicText = null;
		List<DynamicTextType> dynamicTextList = doc.getQuestionItem()
				.getQuestionTextList();
		for (int i = 0; i < dynamicTextList.size(); i++) {
			dynamicText = dynamicTextList.get(i);
			if (dynamicText == null) {
				continue;
			}
			if (XmlBeansUtil.getXmlAttributeValue(dynamicText.xmlText(),
					"lang=\"") == null) {
				throw new Exception(
						Translator
								.trans("QuestionItem.mess.QuestionTextLanguageTypeHasNotBeenSpecified")); //$NON-NLS-1$
			}
		}
		return; // No error found:
	}

	@Override
	public void executeChange(Object value, Class<?> type) throws Exception {
		
		if (type.equals(ModelIdentifingType.Type_A.class)) {
			if (((String) value).length() == 0) {
				if (doc.getQuestionItem().getQuestionTextList().size() > 0) {
					doc.getQuestionItem().removeQuestionText(0);
				}
			} else {
				DynamicTextType questionText = (DynamicTextType) XmlBeansUtil
						.getLangElement(getDisplayLanguage(), getQuestionText());
				LiteralTextType lTextType = (LiteralTextType) questionText
						.getTextList()
						.get(0)
						.substitute(
								LiteralTextDocument.type
										.getDocumentElementName(),
								LiteralTextType.type);
				XmlBeansUtil.setTextOnMixedElement(lTextType.getText(),
						(String) value);
			}
		}
		
		// code rep
		// code scheme ref
		if (type.equals(ModelIdentifingType.Type_D.class)) {
			ReferenceType ref = getCodeDomainCodeSchemeReference();
			if (ref == null) {
				log.warn("Not setting CodeRepresentationCodeSchemeReference as ValueRepresentation is of type: "
						+ getResponseDomain().getClass().getName());
				return;
			}
			ModelAccessor.setReference(ref, (LightXmlObjectType) value);
		}
		
		// numeric rep
		// type
		if (type.equals(ModelIdentifingType.Type_E.class)) {
			if (!(getResponseDomain() instanceof NumericRepresentationType)) {
				log.warn("Not setting NumericRepresentation.type as ValueRepresentation is of type: "
						+ getResponseDomain().getClass().getName());
				return;
			}
			((NumericRepresentationType) getResponseDomain())
					.setType((NumericTypeCodeType.Enum) value);
		}

		// dec position
		if (type.equals(ModelIdentifingType.Type_F.class)) {
			if (!(getResponseDomain() instanceof NumericRepresentationType)) {
				log.warn("Not setting NumericRepresentation.type as ValueRepresentation is of type: "
						+ getResponseDomain().getClass().getName());
				return;
			}
			((NumericRepresentationType) getResponseDomain())
					.setDecimalPositions((BigInteger) value);
		}

		// text rep
		// min lenght
		if (type.equals(ModelIdentifingType.Type_G.class)) {
			if (!(getResponseDomain() instanceof TextRepresentationType)) {
				log.warn("Not setting TextRepresentation.type as ValueRepresentation is of type: "
						+ getResponseDomain().getClass().getName());
				return;
			}
			if (bigIntIsZero((BigInteger) value)) {
				TextRepresentationType text = ((TextRepresentationType) getResponseDomain());
				if (text.getMinLength() != null) {
					text.unsetMinLength();
				}
				return;
			}
			((TextRepresentationType) getResponseDomain())
					.setMinLength((BigInteger) value);
		}
		
		// regx
		if (type.equals(ModelIdentifingType.Type_H.class)) {
			if (!(getResponseDomain() instanceof TextRepresentationType)) {
				log.warn("Not setting TextRepresentation.type as ValueRepresentation is of type: "
						+ getResponseDomain().getClass().getName());
				return;
			}
			if ("".equals(((String) value))) {
				TextRepresentationType text = ((TextRepresentationType) getResponseDomain());
				if (text.getRegExp() != null) {
					text.unsetRegExp();
				}
				return;
			}
			((TextRepresentationType) getResponseDomain())
					.setRegExp((String) value);
		}

		// max lenght
		if (type.equals(ModelIdentifingType.Type_I.class)) {
			if (!(getResponseDomain() instanceof TextRepresentationType)) {
				log.warn("Not setting TextRepresentation.type as ValueRepresentation is of type: "
						+ getResponseDomain().getClass().getName());
				return;
			}
			if (bigIntIsZero((BigInteger) value)) {
				TextRepresentationType text = ((TextRepresentationType) getResponseDomain());
				if (text.getMaxLength() != null) {
					text.unsetMaxLength();
				}
				return;
			}
			((TextRepresentationType) getResponseDomain())
					.setMaxLength((BigInteger) value);
		}
		
		// date time rep
		// format
		if (type.equals(ModelIdentifingType.Type_J.class)) {
			if (!(getResponseDomain() instanceof DateTimeRepresentationType)) {
				log.warn("Not setting DateTimeRepresentation.format as ValueRepresentation is of type: "
						+ getResponseDomain().getClass().getName());
				return;
			}
			if (value == null || "".equals((String) value)) {
				((DateTimeRepresentationType) getResponseDomain())
						.unsetFormat();
				return;
			}
			((DateTimeRepresentationType) getResponseDomain())
					.setFormat((String) value);
		}

		// type
		if (type.equals(ModelIdentifingType.Type_K.class)) {
			if (!(getResponseDomain() instanceof DateTimeRepresentationType)) {
				log.warn("Not setting DateTimeRepresentation.type as ValueRepresentation is of type: "
						+ getResponseDomain().getClass().getName());
				return;
			}
			DateTypeCodeType.Enum dateTime = DateTypeCodeType.Enum
					.forInt((Integer) value);
			((DateTimeRepresentationType) getResponseDomain())
					.setType(dateTime);
		}
		
		// missing values
		if (type.equals(ModelIdentifingType.Type_L.class)) {			
			((RepresentationType) getResponseDomain())
					.setMissingValue((List) value);
		}

		// Set Concept reference
		if (type.equals(ReferenceType.class)) {
			ReferenceType ref = getConceptReferenceType();
			ModelAccessor.setReference(doc.getQuestionItem()
					.getConceptReferenceList(), ref,
					((LightXmlObjectType) value));
		}

		if (type.equals(NameType.class)) {
			if (((String) value).length() == 0) {
				if (doc.getQuestionItem().getQuestionItemNameList().size() > 0) {
					doc.getQuestionItem().removeQuestionItemName(0);
				}
			} else {
				NameType name = NameType.Factory.newInstance();
				name.setStringValue((String) value);
				if (doc.getQuestionItem().getQuestionItemNameList().size() > 0) {
					doc.getQuestionItem().getQuestionItemNameList()
							.set(0, name);
				} else {
					doc.getQuestionItem().getQuestionItemNameList().add(name);
				}
			}
		}
	}
	
	private boolean bigIntIsZero(BigInteger bigInt) {
		return bigInt.intValue() == 0;
	}
	
	// code rep
	public ReferenceType getCodeDomainCodeSchemeReference() {
		RepresentationType rep = getResponseDomain();
		if (!(rep instanceof CodeDomainType)) {
			return null;
		}

		ReferenceType reference = ((CodeDomainType) rep)
				.getCodeSchemeReference();
		if (create && reference == null) {
			reference = ((CodeRepresentationType) rep)
					.addNewCodeSchemeReference();
		}
		return reference;
	}

}
