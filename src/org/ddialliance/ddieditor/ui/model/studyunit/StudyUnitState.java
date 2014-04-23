package org.ddialliance.ddieditor.ui.model.studyunit;

import java.sql.Timestamp;

public class StudyUnitState {
	String ddanr;
	String format;
	Timestamp date;

	public StudyUnitState() {		
	}
	
	public StudyUnitState(String ddanr, String format, Timestamp date) {
		super();
		this.ddanr = ddanr;
		this.format = format;
		this.date = date;
	}

	public StudyUnitState(Object[] obejcts) {
		this.format = (String)obejcts[1];
		this.ddanr = (String)obejcts[0];
		this.date = (Timestamp)obejcts[2];
	}

	public String getDdanr() {
		return ddanr;
	}

	public void setDdanr(String ddanr) {
		this.ddanr = ddanr;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "StudyUnitState [ddanr=" + ddanr + ", format=" + format
				+ ", date=" + date + "]";
	}
}
