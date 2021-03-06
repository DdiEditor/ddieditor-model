package org.ddialliance.ddieditor.ui.model.userid;

public enum UserIdType {
	ARCHIVE_BRANCH("dk.dda.archivebranch"),
	
	ANNONYMIZIED_DATA("dk.dda.study.annonymizeddata"),
	
	CPR_DATA("dk.dda.study.cprdata"),

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

	ANALYSIS_UNIT_COVERED("dk.dda.study.analysisunitcovered"),

	PRIMARY_PUBLICATION("dk.dda.study.primarypublication"),

	SECONDARY_PUBLICATION("dk.dda.study.secondarypublication"),

	ORG_SAMLE_SIZE("dk.dda.originalsamplesize"),
	
	ORGANIZATION_DDA_ID("dk.dda.organization.dbid"),
	
	INDIVIDUAL_DDA_ID("dk.dda.individual.dbid"),
	
	MULTI_QUEI_GROUPING_ID("dk.dda.multiplequestion.groupingid"), 
	
	DDI_EDITOR_VERSION("ddieditor.app.version");

	String type;

	private UserIdType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
