package org.ddialliance.ddieditor.ui.model.note;

import java.util.List;

import org.apache.xmlbeans.XmlObject;
import org.ddialliance.ddi3.xml.xmlbeans.reusable.ContentDocument;
import org.ddialliance.ddi3.xml.xmlbeans.reusable.NoteDocument;
import org.ddialliance.ddi3.xml.xmlbeans.reusable.StructuredStringType;
import org.ddialliance.ddi3.xml.xmlbeans.reusable.UserIDType;
import org.ddialliance.ddi3.xml.xmlbeans.reusable.impl.StructuredStringTypeImpl;
import org.ddialliance.ddieditor.logic.identification.IdentificationManager;
import org.ddialliance.ddieditor.model.lightxmlobject.LightXmlObjectType;
import org.ddialliance.ddieditor.ui.model.ElementType;
import org.ddialliance.ddieditor.ui.model.Model;
import org.ddialliance.ddiftp.util.DDIFtpException;
import org.ddialliance.ddiftp.util.xml.XmlBeansUtil;

public class Note extends Model {
	NoteDocument doc;
	ElementType parent;

	public Note(NoteDocument doc, String parentId, String parentVersion) {
		super(doc, parentId, parentVersion);
		this.doc = doc;
		if (doc.getNote().getId() == null) {
			try {
				IdentificationManager.getInstance().addIdentification(
						doc.getNote(), ElementType.NOTE.getIdPrefix(), null);
			} catch (DDIFtpException e) {
				// do nothing see logs
			}
		}
	}

	@Override
	public void executeChange(Object object, Class<?> type) throws Exception {
		// multi change
		if (type.equals(NoteCompound.class)) {
			NoteCompound noteCompound = (NoteCompound) object;
			executeChange(noteCompound.noteStructure, NoteStructure.class);
			executeChange(noteCompound.content, List.class);
			executeChange(noteCompound.lightXmlObject, LightXmlObjectType.class);
		}

		// init note
		if (type.equals(NoteStructure.class)) {
			NoteStructure noteStructure = (NoteStructure) object;

			// processing
			doc.getNote()
					.setType(
							org.ddialliance.ddi3.xml.xmlbeans.reusable.NoteTypeCodeType.Enum
									.forInt(noteStructure.getNoteTypeInt()));

			// user id
			UserIDType userId = doc.getNote().addNewUserID();
			userId.setType(IdentificationManager.getInstance().getAgency());
			userId.setStringValue(noteStructure.getUserId());

			// subject
			doc.getNote().addNewSubject()
					.setStringValue(noteStructure.getSubject());

			// parent
			this.parent = noteStructure.getParentElement();

		}

		// set content
		if (type.equals(List.class)) {
			doc.getNote().getContentList()
					.addAll((List<StructuredStringType>) object);
		}

		// set reference
		if (type.equals(LightXmlObjectType.class)) {
			IdentificationManager.getInstance().addReferenceInformation(
					doc.getNote().addNewRelationship()
							.addNewRelatedToReference(),
					(LightXmlObjectType) object);
		}
	}

	@Override
	public XmlObject getDocument() throws DDIFtpException {
		return doc;
	}

	public ElementType getParent() {
		return parent;
	}

	public void setParent(ElementType parent) {
		this.parent = parent;
	}
}
