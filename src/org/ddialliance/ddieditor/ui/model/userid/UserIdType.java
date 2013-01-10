package org.ddialliance.ddieditor.ui.model.userid;

public enum UserIdType {
	ARCHIVE_BRANCH("dk.dda.archivebranch"),

	UNSPECIFIED_START_DATE("dk.dda.study.startdate.unspecified"),

	UNSPECIFIED_END_DATE("dk.dda.study.enddate.unspecified"),

	DATA_COLLECTION_METHODOLOGY_CV_CODE(
			"dk.dda.study.datacollectionmethodology.cvcode"),

	DATA_COLLECTION_SAMPLING_PROCEDURE_CV_CODE(
			"dk.dda.study.samplingprocedure.cvcode"),

	DATA_COLLECTION_TIME_METHOD_CV_CODE("dk.dda.study.timemethod.cvcode"),

	DATA_COLLECTION_EVENT_COLLECTION_MODE_CV_CODE(
			"dk.dda.study.collectionmode.cvcode"),

	DATA_COLLECTION_EVENT_COLLECTION_SITUATION_CV_CODE(
			"dk.dda.study.collectionsituation.cvcode"),

	ARCHIVE_ACCESS_CONDITION("dk.dda.study.archive.access.condition.cvcode"),

	ARCHIVE_ACCESS_RESTRICTION("dk.dda.study.archive.access.restriction.cvcode"),
	
	ANALYSIS_UNIT_COVERED("dk.dda.study.analysisunitcovered");

	String type;

	private UserIdType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
