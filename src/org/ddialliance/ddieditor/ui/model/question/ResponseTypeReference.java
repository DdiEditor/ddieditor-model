package org.ddialliance.ddieditor.ui.model.question;


public class ResponseTypeReference {

	private String label;
	private ResponseType responseDomain;

	/**
	 * Constructor
	 * 
	 * @param label
	 *            Label of Combo Box
	 * @param org
	 *            .ddialliance.ddieditor.ui.editor.ResponseType.RESPONSE_TYPES
	 */
	public ResponseTypeReference(String label, ResponseType representationType) {
		this.label = label;
		this.responseDomain = representationType;
	}

	/**
	 * Returns label e.g. for Combo Box
	 */
	public String toString() {
		return label;
	}

	/**
	 * Returns Response Domain of reference
	 * 
	 * @return
	 */
	public ResponseType getResponseDomain() {
		return responseDomain;
	}
}
