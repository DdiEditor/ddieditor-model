package org.ddialliance.ddieditor.ui.model.userid;

public enum UserIdType {
	ARCHIVE_BRANCH("dk.dda.archivebranch"),

	UNSPECIFIED_START_DATE("dk.dda.unspecifiedstartdate"),

	UNSPECIFIED_END_DATE("dk.dda.unspecified.enddate"),

	DATA_COLLECTION_METHODOLOGY_CV_CODE("dk.dda.cvcode.datacollectionmethodology"),

	DATA_COLLECTION_SAMPLING_PROCEDURE_CV_CODE("dk.dda.cvcode.samplingprocedure"),

	DATA_COLLECTION_EVENT_COLLECTION_MODE_CV_CODE("dk.dda.cvcode.collectionmode");

	String type;

	private UserIdType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
