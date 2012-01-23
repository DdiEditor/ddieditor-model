package org.ddialliance.ddieditor.ui.model.question;

import java.util.ArrayList;
import java.util.List;

import org.ddialliance.ddi3.xml.xmlbeans.datacollection.CategoryDomainType;
import org.ddialliance.ddi3.xml.xmlbeans.datacollection.CodeDomainType;
import org.ddialliance.ddi3.xml.xmlbeans.datacollection.DateTimeDomainType;
import org.ddialliance.ddi3.xml.xmlbeans.datacollection.GeographicDomainType;
import org.ddialliance.ddi3.xml.xmlbeans.datacollection.NumericDomainType;
import org.ddialliance.ddi3.xml.xmlbeans.logicalproduct.CodeRepresentationType;
import org.ddialliance.ddi3.xml.xmlbeans.reusable.CategoryRepresentationType;
import org.ddialliance.ddi3.xml.xmlbeans.reusable.CoordinatePairsType;
import org.ddialliance.ddi3.xml.xmlbeans.reusable.DateTimeRepresentationType;
import org.ddialliance.ddi3.xml.xmlbeans.reusable.ExternalCategoryRepresentationType;
import org.ddialliance.ddi3.xml.xmlbeans.reusable.GeographicRepresentationType;
import org.ddialliance.ddi3.xml.xmlbeans.reusable.NumericRepresentationType;
import org.ddialliance.ddi3.xml.xmlbeans.reusable.RepresentationType;
import org.ddialliance.ddi3.xml.xmlbeans.reusable.TextRepresentationType;
import org.ddialliance.ddiftp.util.Translator;

public class Response {
	public static String[] RESPONSE_TYPE_LABELS = { "",
			Translator.trans("ResponseTypeDetail.label.Code"),
			Translator.trans("ResponseTypeDetail.label.Text"),
			Translator.trans("ResponseTypeDetail.label.Numeric"),
			Translator.trans("ResponseTypeDetail.label.Date"),
			Translator.trans("ResponseTypeDetail.label.Category"),
			Translator.trans("ResponseTypeDetail.label.Geographic") };

	private static final String[] NUMERIC_TYPES = {
			Translator.trans("ResponseTypeDetail.label.Integer"),
			Translator.trans("ResponseTypeDetail.label.Double"),
			Translator.trans("ResponseTypeDetail.label.Float") };

	private static enum NUMERIC_TYPE_INDEX {
		INTEGER, DOUBLE, FLOAT
	};

	public static String[] getResponseTypeLabels() {
		return RESPONSE_TYPE_LABELS;
	}

	public static String getResponseTypeLabel(ResponseType rt) {
		return RESPONSE_TYPE_LABELS[rt.ordinal()];
	}

	/**
	 * Return list of Response Domain references
	 * 
	 * @return List<ResponseDomainReference>
	 */
	public static List<ResponseTypeReference> getResponseDomainReferenceList() {
		List<ResponseTypeReference> responseDomainReferenceList = new ArrayList<ResponseTypeReference>();

		responseDomainReferenceList.add(new ResponseTypeReference(
				RESPONSE_TYPE_LABELS[ResponseType.UNDEFINED.ordinal()],
				ResponseType.UNDEFINED));
		responseDomainReferenceList.add(new ResponseTypeReference(
				RESPONSE_TYPE_LABELS[ResponseType.CODE.ordinal()],
				ResponseType.CODE));
		responseDomainReferenceList.add(new ResponseTypeReference(
				RESPONSE_TYPE_LABELS[ResponseType.TEXT.ordinal()],
				ResponseType.TEXT));
		responseDomainReferenceList.add(new ResponseTypeReference(
				RESPONSE_TYPE_LABELS[ResponseType.NUMERIC.ordinal()],
				ResponseType.NUMERIC));
		responseDomainReferenceList.add(new ResponseTypeReference(
				RESPONSE_TYPE_LABELS[ResponseType.DATE.ordinal()],
				ResponseType.DATE));
		responseDomainReferenceList.add(new ResponseTypeReference(
				RESPONSE_TYPE_LABELS[ResponseType.CATEGORY.ordinal()],
				ResponseType.CATEGORY));
		responseDomainReferenceList.add(new ResponseTypeReference(
				RESPONSE_TYPE_LABELS[ResponseType.GEOGRAPHIC.ordinal()],
				ResponseType.GEOGRAPHIC));

		return responseDomainReferenceList;
	}

	/**
	 * Return Response Type of a given Response Domain
	 * 
	 * @param representationType
	 * @return
	 */
	public static ResponseType getResponseType(
			RepresentationType representationType) {
		ResponseType result = ResponseType.UNDEFINED;
		if (representationType == null) { // guard
			return result;
		}		
		
		// logical product
		// CategoryRepresentationType
		if (representationType instanceof CategoryRepresentationType) {
			result = ResponseType.CATEGORY;
		}
		// CodeRepresentationType
		else if (representationType instanceof org.ddialliance.ddi3.xml.xmlbeans.reusable.CodeRepresentationType) {
			result = ResponseType.CODE;
		}
		// DateTimeRepresentationType
		else if (representationType instanceof DateTimeRepresentationType) {
			result = ResponseType.DATE;
		}
		// ExternalCategoryRepresentationType
		else if (representationType instanceof ExternalCategoryRepresentationType) {
			// TODO
			result = ResponseType.UNDEFINED;
		}
		// GeographicRepresentationType
		else if (representationType instanceof GeographicRepresentationType) {
			result = ResponseType.GEOGRAPHIC;
		}
		// NumericRepresentationType
		else if (representationType instanceof NumericRepresentationType) {
			result = ResponseType.NUMERIC;
		}
		// TextRepresentationType
		else if (representationType instanceof TextRepresentationType) {
			result = ResponseType.TEXT;
		}

		// data collection
		// CategoryDomainType
		else if (representationType instanceof CategoryDomainType) {
			result = ResponseType.CATEGORY;
		}
		// CodeDomainType
		else if (representationType instanceof CodeDomainType) {
			result = ResponseType.CODE;
		}
		// CodeRepresentationType
		else if (representationType instanceof CodeRepresentationType) {
			result = ResponseType.CODE;
		}
		// CoordinatePairsType
		else if (representationType instanceof CoordinatePairsType) {
			// TODO
			result = ResponseType.UNDEFINED;
		}
		// DateTimeDomainType
		else if (representationType instanceof DateTimeDomainType) {
			result = ResponseType.DATE;
		}
		// GeographicDomainType
		else if (representationType instanceof GeographicDomainType) {
			result = ResponseType.GEOGRAPHIC;
		}
		// NumericDomainType
		else if (representationType instanceof NumericDomainType) {
			result = ResponseType.NUMERIC;
		}
		// TextDomainType
		else if (representationType instanceof NumericDomainType) {
			result = ResponseType.NUMERIC;
		}

		return result;
	}
}
