package org.ddialliance.ddieditor.ui.model.instrument;

import java.util.ArrayList;
import java.util.List;

import org.ddialliance.ddi3.xml.xmlbeans.datacollection.ConditionalTextDocument;
import org.ddialliance.ddi3.xml.xmlbeans.datacollection.ConditionalTextType;
import org.ddialliance.ddi3.xml.xmlbeans.datacollection.DynamicTextType;
import org.ddialliance.ddi3.xml.xmlbeans.datacollection.LiteralTextDocument;
import org.ddialliance.ddi3.xml.xmlbeans.datacollection.LiteralTextType;
import org.ddialliance.ddi3.xml.xmlbeans.datacollection.StatementItemDocument;
import org.ddialliance.ddi3.xml.xmlbeans.datacollection.TextType;
import org.ddialliance.ddi3.xml.xmlbeans.reusable.CodeType;
import org.ddialliance.ddi3.xml.xmlbeans.reusable.ProgrammingLanguageCodeType;
import org.ddialliance.ddi3.xml.xmlbeans.reusable.StructuredStringType;
import org.ddialliance.ddieditor.ui.model.Model;
import org.ddialliance.ddieditor.ui.model.ModelIdentifingType;
import org.ddialliance.ddieditor.util.DdiEditorConfig;
import org.ddialliance.ddiftp.util.DDIFtpException;
import org.ddialliance.ddiftp.util.Translator;
import org.ddialliance.ddiftp.util.xml.XmlBeansUtil;

public class StatementItem extends Model {
	private StatementItemDocument doc;

	public StatementItem(StatementItemDocument doc, String parentId,
			String parentVersion) throws Exception {
		super(doc, parentId, parentVersion);

		if (doc == null) {
			this.doc = StatementItemDocument.Factory.newInstance();
			// add id and version
			setId("");
			setVersion("");
		} else {
			this.doc = doc;
		}
	}

	public StatementItemDocument getDocument() {
		return doc;
	}

	@Override
	public void executeChange(Object value, Class<?> type) throws Exception {
		// Text
		if (type.equals(TextType.class)) {
			XmlBeansUtil.setTextOnMixedElement(getText(), (String) value);
		}

		// Code
		if (type.equals(ProgrammingLanguageCodeType.class)) {
			getProgrammingLanguageCode().setStringValue((String) value);
		}

		// ProgrammingLanguage
		if (type.equals(ModelIdentifingType.Type_A.class)) {
			getProgrammingLanguageCode().setProgrammingLanguage((String) value);
		}
	}

	public ProgrammingLanguageCodeType getProgrammingLanguageCode()
			throws DDIFtpException {
		CodeType codeType = getCodeType();
		if (codeType == null) {
			return null;
		}
		return getProgrammingLanguageCode(codeType);
	}

	private CodeType getCodeType() throws DDIFtpException {
		DynamicTextType dynamicText = getDisplay();
		if (dynamicText == null) {
			return null;
		}
		ConditionalTextType conditionalText = getConditionalText(dynamicText);
		if (conditionalText == null) {
			return null;
		}
		return getExpression(conditionalText);
	}

	public DynamicTextType getDisplay() throws DDIFtpException {
		DynamicTextType dynamicText = null;
		if (doc.getStatementItem().getDisplayTextList().isEmpty() && create) {
			dynamicText = doc.getStatementItem().addNewDisplayText();
			XmlBeansUtil.addTranslationAttributes(dynamicText,
					Translator.getLocaleLanguage(), false, true);
		} else {
			dynamicText = (DynamicTextType) XmlBeansUtil.getLangElement(
					getDisplayLanguage(), doc.getStatementItem()
							.getDisplayTextList());
		}
		return dynamicText;
	}

	public StructuredStringType getText() throws DDIFtpException {
		DynamicTextType dynamicText = getDisplay();
		if (dynamicText == null) {
			return null;
		}
		return getText(dynamicText);
	}

	public StructuredStringType getText(DynamicTextType dynamicText)
			throws DDIFtpException {
		List<LiteralTextType> result = new ArrayList<LiteralTextType>();
		for (TextType test : dynamicText.getTextList()) {
			if (test instanceof LiteralTextType) {
				result.add((LiteralTextType) test);
			}
		}
		StructuredStringType text = null;

		// literal text
		for (LiteralTextType literalTextType : result) {
			if (literalTextType.getText() != null) {
				text = literalTextType.getText();
			}
		}
		if (text == null && create) {
			TextType textType = dynamicText.addNewText();
			LiteralTextType lTextType = (LiteralTextType) textType.substitute(
					LiteralTextDocument.type.getDocumentElementName(),
					LiteralTextType.type);
			text = lTextType.addNewText();
		}
		return text;
	}

	public ConditionalTextType getConditionalText(DynamicTextType dynamicText) {
		ConditionalTextType condition = null;
		for (TextType test : dynamicText.getTextList()) {
			if (test instanceof ConditionalTextType) {
				condition = (ConditionalTextType) test;
			}
		}
		if (condition == null && create) {
			TextType textType = dynamicText.addNewText();
			condition = (ConditionalTextType) textType.substitute(
					ConditionalTextDocument.type.getDocumentElementName(),
					ConditionalTextType.type);
		}
		return condition;
	}

	public CodeType getExpression(ConditionalTextType conditionalText) {
		if (conditionalText.getExpression() == null) {
			return create ? conditionalText.addNewExpression() : null;
		}
		return conditionalText.getExpression();
	}

	public ProgrammingLanguageCodeType getProgrammingLanguageCode(
			CodeType codeType) {
		if (codeType == null) {
			return null;
		}

		if (create && codeType.getCodeList().isEmpty()) {
			if (create) {
				codeType.addNewCode();
				codeType.getCodeList()
						.get(0)
						.setProgrammingLanguage(
								DdiEditorConfig
										.get(DdiEditorConfig.DDI_INSTRUMENT_PROGRAM_LANG));
				return codeType.getCodeList().get(0);
			}
		} else {
			return codeType.getCodeList().get(0);
		}
		return null;
	}
}
