package org.ddialliance.ddieditor.ui.dbxml.note;

import java.util.List;

import org.ddialliance.ddieditor.model.DdiManager;
import org.ddialliance.ddieditor.model.lightxmlobject.LightXmlObjectType;
import org.ddialliance.ddieditor.ui.dbxml.IDao;
import org.ddialliance.ddieditor.ui.model.ElementType;
import org.ddialliance.ddieditor.ui.model.IModel;
import org.ddialliance.ddieditor.ui.model.note.Note;
import org.ddialliance.ddiftp.util.DDIFtpException;
import org.ddialliance.ddiftp.util.log.Log;
import org.ddialliance.ddiftp.util.log.LogFactory;
import org.ddialliance.ddiftp.util.log.LogType;

public class NoteDao implements IDao {
	private static Log log = LogFactory.getLog(LogType.SYSTEM, NoteDao.class);

	@Override
	public void create(IModel model) throws DDIFtpException {
		// parent
		ElementType parent = ((Note) model).getParent();
		if (parent == null) {
			throw new DDIFtpException("Parent element not defined",
					new Throwable());
		}

		// parent study unit
		if (parent.equals(ElementType.STUDY_UNIT)) {
			DdiManager.getInstance().createElement(
					model.getDocument(),
					model.getId(),
					model.getVersion(),
					parent.getElementName(),

					// parent sub-elements
					new String[] { "UserID", "VersionResponsibility",
							"VersionRationale", "Citation", "Abstract",
							"UniverseReference", "SeriesStatement",
							"FundingInformation", "Purpose", "Coverage",
							"AnalysisUnit", "AnalysisUnitsCovered",
							"KindOfData", "OtherMaterial", },
					// stop elements
					new String[] { "Embargo", "ConceptualComponent",
							"ConceptualComponentReference", "DataCollection",
							"DataCollectionReference", "BaseLogicalProduct",
							"LogicalProductReference", "PhysicalDataProduct",
							"PhysicalDataProductReference", "PhysicalInstance",
							"PhysicalInstanceReference", "Archive",
							"ArchiveReference", "DDIProfile",
							"DDIProfileReference" },
					// jump elements
					new String[] { "Note" });
		}

		// parent conceptual component
		else if (parent.equals(ElementType.CONCEPTUAL_COMPONENT)) {
			DdiManager.getInstance().createElement(
					model.getDocument(),
					model.getId(),
					model.getVersion(),
					parent.getElementName(),

					// parent sub-elements
					new String[] { "UserID", "VersionResponsibility",
							"VersionRationale",
							"ConceptualComponentModuleName", "reusable__Label",
							"Description", "Coverage", "OtherMaterial", },
					// stop elements
					new String[] { "ConceptScheme", "ConceptSchemeReference",
							"UniverseScheme", "UniverseSchemeReference",
							"GeographicStructureScheme",
							"GeographicStructureSchemeReference",
							"GeographicLocationScheme",
							"GeographicLocationSchemeReference" },
					// jump elements
					new String[] { "Note" });
		}

		// parent conceptual component
		else if (parent.equals(ElementType.DATA_COLLECTION)) {
			DdiManager.getInstance().createElement(
					model.getDocument(),
					model.getId(),
					model.getVersion(),
					parent.getElementName(),

					// parent sub-elements
					new String[] { "UserID", "VersionResponsibility",
							"VersionRationale", "DataCollectionModuleName",
							"reusable__Label", "Description", "Coverage",
							"OtherMaterial" },
					// stop elements
					new String[] { "Methodology", "CollectionEvent",
							"QuestionScheme", "ControlConstructScheme",
							"InterviewerInstructionScheme", "Instrument",
							"ProcessingEvent" },
					// jump elements
					new String[] { "Note" });
		}

		// guard
		else {
			throw new DDIFtpException("Parent element not identified: "
					+ parent, new Throwable());
		}
	}

	private void throwNotImpl() throws DDIFtpException {
		throw new DDIFtpException("Not implemented", new Throwable());
	}

	@Override
	public IModel create(String arg0, String arg1, String arg2, String arg3)
			throws Exception {
		throwNotImpl();
		return null;
	}

	@Override
	public void delete(String arg0, String arg1, String arg2, String arg3)
			throws Exception {
		throwNotImpl();
	}

	@Override
	public List<LightXmlObjectType> getLightXmlObject(LightXmlObjectType arg0)
			throws Exception {
		throwNotImpl();
		return null;
	}

	@Override
	public List<LightXmlObjectType> getLightXmlObject(String arg0, String arg1,
			String arg2, String arg3) throws Exception {
		throwNotImpl();
		return null;
	}

	@Override
	public IModel getModel(String arg0, String arg1, String arg2, String arg3)
			throws Exception {
		throwNotImpl();
		return null;
	}

	@Override
	public void update(IModel arg0) throws DDIFtpException {
		throwNotImpl();
	}
}
