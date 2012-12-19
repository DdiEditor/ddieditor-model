package org.ddialliance.ddieditor.ui.model.note;

import org.ddialliance.ddieditor.ui.model.ElementType;

public enum NoteStructure {
	CREATOR_TO_INDIVIDUAL_NOTE("dk.dda.ddi.creatortoindividualrelation", "0.2",
			"StudyUnit/Creator first name to Individual reference",
			NoteType.SYSTEM, ElementType.STUDY_UNIT),

	KIND_OF_DATA("dk.dda.ddi.kindofdata", "0.1", "Kind of Data",
			NoteType.ADDENDUM, ElementType.STUDY_UNIT),

	UNIVERSE_DESCRIPTION("dk.dda.ddi.universedescription", "0.1",
			"Description of a universe", NoteType.ADDENDUM,
			ElementType.CONCEPTUAL_COMPONENT),

	DATA_COLLECTION_METHODOLOGY_DESCRIPTION(
			"dk.dda.ddi.datacollectionmethodologydescription", "0.1",
			"Description of a data collection methodology", NoteType.ADDENDUM,
			ElementType.DATA_COLLECTION),

	DATA_COLLECTION_SAMPLING_PROCEDURE(
			"dk.dda.ddi.datacollectionsamplingproceduredescription", "0.1",
			"Description of a samling procedure", NoteType.ADDENDUM,
			ElementType.DATA_COLLECTION),

	DATA_COLLECTION_TIME_METHOD(
			"dk.dda.ddi.datacollectiontimemethoddescription", "0.1",
			"Description of a time method", NoteType.ADDENDUM,
			ElementType.DATA_COLLECTION),

	DATA_COLLECTION_EVENT_COLLECTION_MODE(
			"dk.dda.ddi.datacollectionevntcollectionmodedescription", "0.1",
			"Description of a collection mode", NoteType.ADDENDUM,
			ElementType.DATA_COLLECTION),

	DATA_COLLECTION_EVENT_COLLECTION_SITUATION(
			"dk.dda.ddi.datacollectionevntcollectionsituationdescription",
			"0.1", "Description of a collection sitution", NoteType.ADDENDUM,
			ElementType.DATA_COLLECTION);

	private String noteId;
	private String version;
	private String subject;
	private NoteType noteType;
	private ElementType parentElement;

	private NoteStructure(String noteId, String version, String subject,
			NoteType noteType, ElementType parentElement) {
		this.noteId = noteId;
		this.version = version;
		this.subject = subject;
		this.noteType = noteType;
		this.parentElement = parentElement;
	}

	public String getSubject() {
		return subject;
	}

	public int getNoteTypeInt() {
		return noteType.ordinal();
	}

	public String getUserId() {
		return noteId + "-" + version;
	}

	public ElementType getParentElement() {
		return parentElement;
	}
}
