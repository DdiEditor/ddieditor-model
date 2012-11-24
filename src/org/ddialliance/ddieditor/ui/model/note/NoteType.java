package org.ddialliance.ddieditor.ui.model.note;

/*
 * As defined by ddi-3.1<br> 
 * 0=Processing 1=Footnote 2=Addendum 3=System 4=Problem 5=Comment 6=Other
 * @see http://www.ddialliance.org/Specification/DDI-Lifecycle/3.1/XMLSchema/FieldLevelDocumentation/reusable_xsd/complexTypes/NoteType.html#r3
 */
public enum NoteType {
	PROCESSING, FOOTNOTE, ADDENDUM, SYSTEM, PROBLEM, COMMENT, OTHER;
}
