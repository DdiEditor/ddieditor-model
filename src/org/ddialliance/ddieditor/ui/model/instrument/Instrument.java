package org.ddialliance.ddieditor.ui.model.instrument;

import org.ddialliance.ddi3.xml.xmlbeans.datacollection.InstrumentDocument;
import org.ddialliance.ddieditor.logic.identification.IdentificationManager;
import org.ddialliance.ddieditor.model.lightxmlobject.LightXmlObjectType;
import org.ddialliance.ddieditor.ui.model.IModel;
import org.ddialliance.ddieditor.ui.model.LabelDescription;
import org.ddialliance.ddieditor.ui.model.ModelIdentifingType;

public class Instrument extends LabelDescription implements IModel {
	private InstrumentDocument doc;

	public Instrument(InstrumentDocument doc, String parentId,
			String parentVersion) throws Exception {
		super(doc.getInstrument().getId(), doc.getInstrument().getVersion(),
				parentId, parentVersion, null, null);
		this.doc = doc;
	}

	public InstrumentDocument getDocument() {
		return doc;
	}

	@Override
	public void executeChange(Object value, Class<?> type) throws Exception {
		// control construct reference
		if (type.equals(ModelIdentifingType.Type_B.class)) {
			// remove old
			if (doc.getInstrument().getControlConstructReference() != null) {
				doc.getInstrument().unsetControlConstructReference();
			}

			// check for empty
			LightXmlObjectType lightXmlObjectType = (LightXmlObjectType) value;
			if (lightXmlObjectType.getId() == null
					|| lightXmlObjectType.getId().equals("")) { // guard
				return;
			}

			// add new
			IdentificationManager.getInstance().addReferenceInformation(
					doc.getInstrument().addNewControlConstructReference(),
					(LightXmlObjectType) value);
		}
	}
}
