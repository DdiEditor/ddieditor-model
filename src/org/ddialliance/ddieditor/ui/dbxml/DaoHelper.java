package org.ddialliance.ddieditor.ui.dbxml;

import java.util.ArrayList;
import java.util.List;

import org.ddialliance.ddi3.xml.xmlbeans.reusable.ReferenceType;
import org.ddialliance.ddieditor.model.DdiManager;
import org.ddialliance.ddieditor.model.lightxmlobject.LightXmlObjectListDocument;
import org.ddialliance.ddieditor.model.lightxmlobject.LightXmlObjectType;
import org.ddialliance.ddieditor.model.resource.DDIResourceType;
import org.ddialliance.ddieditor.persistenceaccess.PersistenceManager;
import org.ddialliance.ddieditor.persistenceaccess.maintainablelabel.MaintainableLabelQueryResult;
import org.ddialliance.ddieditor.ui.model.ElementType;
import org.ddialliance.ddieditor.ui.model.IModel;
import org.ddialliance.ddieditor.ui.model.LabelDescriptionScheme;
import org.ddialliance.ddieditor.ui.model.StopJumpElements;
import org.ddialliance.ddiftp.util.DDIFtpException;

public class DaoHelper {
	public static void updateScheme(IModel model) throws DDIFtpException {
		if (!(model instanceof LabelDescriptionScheme)) {
			throw new DDIFtpException("Model: "
					+ model.getClass().getSimpleName()
					+ " is not accepted for update. Only type: "
					+ LabelDescriptionScheme.class.getSimpleName()
					+ " is accepted.", new Throwable());
		}
		LabelDescriptionScheme ldScheme = (LabelDescriptionScheme) model;

		MaintainableLabelQueryResult result = ldScheme
				.getMaintainableLabelQueryResult();

		DdiManager.getInstance().updateMaintainableLabel(result,
				ldScheme.getMaintainableLabelUpdateElements());
		ldScheme.cleanCruds();
	}

	public static void createScheme(IModel model,
			LightXmlObjectType lightXmlObject) throws DDIFtpException {
		try {
			// study unit create
			DdiManager.getInstance().createElement(model.getDocument(),
					lightXmlObject.getParentId(),
					lightXmlObject.getParentVersion(),
					lightXmlObject.getElement());
		} catch (Exception e) {
			// check for resource package
			try {
				LightXmlObjectListDocument list = DdiManager.getInstance()
						.getResourcePackagesLight(null, null, null, null);
				if (!list.getLightXmlObjectList().getLightXmlObjectList()
						.isEmpty()) {

					StopJumpElements stopJumpElements = defineStopJumpElements(model
							.getDocument().schemaType()
							.getDocumentElementName().getLocalPart());

					DdiManager.getInstance().createElement(
							model.getDocument(),
							list.getLightXmlObjectList()
									.getLightXmlObjectList().get(0).getId(),
							list.getLightXmlObjectList()
									.getLightXmlObjectList().get(0)
									.getVersion(),
							"ResourcePackage",
							// parent sub-elements
							new String[] { "UserID", "VersionResponsibility",
									"VersionRationale", "Citation", "Abstract",
									"group__Purpose", "FundingInformation",
									"Coverage", "UniverseReference",
									"OtherMaterial", "Archive", "Note",
									"Concepts", "DataCollection",
									"LogicalProduct", "PhysicalDataProduct",
									"PhysicalInstance", "Comparison",
									"DDIProfile", "DDIProfileReference" },
							// stop elements
							stopJumpElements.stopElements,
							// jump elements
							stopJumpElements.jumpElements);
				}
			} catch (Exception e2) {
				throw new DDIFtpException(e2);
			}
		}
	}

	private static StopJumpElements defineStopJumpElements(String localName) {
		String[] schemes = { "OrganizationScheme", "ConceptScheme",
				"UniverseScheme", "GeographicStructureScheme",
				"GeographicLocationScheme", "InterviewerInstructionScheme",
				"ControlConstructScheme", "QuestionScheme", "CategoryScheme",
				"CodeScheme", "NCubeScheme", "VariableScheme",
				"PhysicalStructureScheme", "RecordLayoutScheme" };

		List<String> stopElements = new ArrayList<String>();
		List<String> jumpElements = new ArrayList<String>();
		boolean found = false;

		for (int i = 0; i < schemes.length; i++) {
			if (localName.equals(schemes[i])) {
				found = true;
				jumpElements.add(schemes[i]);
				continue;
			}
			if (found) {
				stopElements.add(schemes[i]);
			} else {
				jumpElements.add(schemes[i]);
			}
		}

		return new StopJumpElements(jumpElements.toArray(new String[] {}),
				stopElements.toArray(new String[] {}));
	}

	public static String defineParent(String parentElement)
			throws DDIFtpException {
		try {
			boolean noStudyUnit = DdiManager.getInstance()
					.getStudyUnitsLight(null, null, null, null)
					.getLightXmlObjectList().getLightXmlObjectList().isEmpty();
			if (noStudyUnit) {
				return ElementType.RESOURCE_PACKAGE.getElementName();
			} else {
				return parentElement;
			}
		} catch (Exception e) {
			throw new DDIFtpException(e);
		}
	}
	
	public static List<LightXmlObjectType> getAllLightXmlObjects(String id,
			String version, ElementType refType) throws Exception {
		List<DDIResourceType> resources = PersistenceManager.getInstance()
				.getResources();
		String workingresource = PersistenceManager.getInstance()
				.getWorkingResource();

		List<LightXmlObjectType> lightXmlObjectTypeList = new ArrayList<LightXmlObjectType>();
		for (DDIResourceType resource : resources) {
			PersistenceManager.getInstance().setWorkingResource(
					resource.getOrgName());
			switch (refType) {
            case CATEGORY_SCHEME:
			lightXmlObjectTypeList.addAll(DdiManager.getInstance()
					.getCategorySchemesLight(id, version, null, null)
					.getLightXmlObjectList().getLightXmlObjectList());
			break;
            case CODE_SCHEME:
			lightXmlObjectTypeList.addAll(DdiManager.getInstance()
					.getCodeSchemesLight(id, version, null, null)
					.getLightXmlObjectList().getLightXmlObjectList());
			break;
            case CONCEPT:
			lightXmlObjectTypeList.addAll(DdiManager.getInstance()
					.getConceptsLight(id, version, null, null)
					.getLightXmlObjectList().getLightXmlObjectList());
			break;
            case CONTROL_CONSTRUCT_SCHEME:
			lightXmlObjectTypeList.addAll(DdiManager.getInstance()
					.getControlConstructsLight());
			break;
            case QUESTION_ITEM:
			lightXmlObjectTypeList.addAll(DdiManager.getInstance()
					.getQuestionItemsLight(id, version, null, null)
					.getLightXmlObjectList().getLightXmlObjectList());
			break;
            case SEQUENCE:
			lightXmlObjectTypeList.addAll(DdiManager.getInstance()
					.getSequencesLight(id, version, null, null)
					.getLightXmlObjectList().getLightXmlObjectList());
			break;
            case UNIVERSE:
			lightXmlObjectTypeList.addAll(DdiManager.getInstance()
					.getUniversesLight(id, version, null, null)
					.getLightXmlObjectList().getLightXmlObjectList());
			break;
            case VARIABLE:
			lightXmlObjectTypeList.addAll(DdiManager.getInstance()
					.getVariablesLight(id, version, null, null)
					.getLightXmlObjectList().getLightXmlObjectList());
			break;
			default:
				throw new DDIFtpException("Element type not supported: "+refType.name());
			}
		}

		PersistenceManager.getInstance().setWorkingResource(workingresource);
		return lightXmlObjectTypeList;
	}

	// 20120523 -work in progress on initMultipleStorage and
	// resetMultipleStorage
	// delete if nessesary not used :)	
	String workingResource;

	public List<DDIResourceType> initMultipleStorage() throws DDIFtpException {
		workingResource = PersistenceManager.getInstance().getWorkingResource();
		return PersistenceManager.getInstance().getResources();
	}

	public void resetMultipleStorage() throws DDIFtpException {
		PersistenceManager.getInstance().setWorkingResource(workingResource);
	}
}
