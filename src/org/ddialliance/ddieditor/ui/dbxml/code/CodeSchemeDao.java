package org.ddialliance.ddieditor.ui.dbxml.code;

import java.util.ArrayList;
import java.util.List;

import org.ddialliance.ddi3.xml.xmlbeans.logicalproduct.CodeSchemeDocument;
import org.ddialliance.ddi3.xml.xmlbeans.logicalproduct.CodeSchemeType;
import org.ddialliance.ddieditor.logic.identification.IdentificationManager;
import org.ddialliance.ddieditor.logic.urn.ddi.ReferenceResolution;
import org.ddialliance.ddieditor.model.DdiManager;
import org.ddialliance.ddieditor.model.lightxmlobject.LightXmlObjectType;
import org.ddialliance.ddieditor.model.resource.DDIResourceType;
import org.ddialliance.ddieditor.persistenceaccess.PersistenceManager;
import org.ddialliance.ddieditor.ui.dbxml.DaoHelper;
import org.ddialliance.ddieditor.ui.dbxml.IDao;
import org.ddialliance.ddieditor.ui.model.ElementType;
import org.ddialliance.ddieditor.ui.model.IModel;
import org.ddialliance.ddieditor.ui.model.code.CodeScheme;
import org.ddialliance.ddiftp.util.DDIFtpException;
import org.ddialliance.ddiftp.util.log.Log;
import org.ddialliance.ddiftp.util.log.LogFactory;
import org.ddialliance.ddiftp.util.log.LogType;

/**
 * Code Scheme Dao
 */
public class CodeSchemeDao implements IDao {
	private static Log log = LogFactory.getLog(LogType.SYSTEM,
			CodeSchemeDao.class);

	/**
	 * 
	 * Get Light Code Scheme List
	 * 
	 * @param id
	 * @param version
	 * @return List<LightXmlObjectType>
	 * @throws Exception
	 */
	public static List<LightXmlObjectType> getCodeSchemesLight(String id,
			String version) throws Exception {
		List<LightXmlObjectType> lightXmlObjectTypeList = DdiManager
				.getInstance().getCodeSchemesLight(id, version, null, null)
				.getLightXmlObjectList().getLightXmlObjectList();
		return lightXmlObjectTypeList;
	}

	public static List<LightXmlObjectType> getAllCodeSchemesLight(String id,
			String version) throws Exception {
		List<DDIResourceType> resources = PersistenceManager.getInstance()
				.getResources();
		String workingresource = PersistenceManager.getInstance()
				.getWorkingResource();

		List<LightXmlObjectType> lightXmlObjectTypeList = new ArrayList<LightXmlObjectType>();
		for (DDIResourceType resource : resources) {
			PersistenceManager.getInstance().setWorkingResource(
					resource.getOrgName());
			lightXmlObjectTypeList.addAll(DdiManager.getInstance()
					.getCodeSchemesLight(id, version, null, null)
					.getLightXmlObjectList().getLightXmlObjectList());
		}

		PersistenceManager.getInstance().setWorkingResource(workingresource);
		return lightXmlObjectTypeList;
	}

	public static CodeSchemeDocument getAllCodeSchemeByReference(
			ReferenceResolution refRes) throws Exception {
		CodeSchemeDocument result = null;

		List<DDIResourceType> resources = PersistenceManager.getInstance()
				.getResources();
		String workingresource = PersistenceManager.getInstance()
				.getWorkingResource();

		for (DDIResourceType resource : resources) {
			PersistenceManager.getInstance().setWorkingResource(
					resource.getOrgName());
			result = getCodeSchemeByReference(refRes);
			if (result != null) {
				break;
			}
		}

		PersistenceManager.getInstance().setWorkingResource(workingresource);
		return result;
	}

	/**
	 * Get Code Scheme by Id
	 * 
	 * @param id
	 * @param parentId
	 * @return CodeSchemeType
	 * @throws Exception
	 */
	public CodeSchemeType getCodeSchemeById(String id, String parentId)
			throws Exception {
		return DdiManager.getInstance().getCodeScheme(id, null, parentId, null)
				.getCodeScheme();
	}

	/**
	 * Get code scheme by reference
	 * 
	 * @param refRes
	 * @return code scheme document
	 * @throws Exception
	 */
	public static CodeSchemeDocument getCodeSchemeByReference(
			ReferenceResolution refRes) throws Exception {
		List<LightXmlObjectType> codeSchemeRefList = DdiManager.getInstance()
				.getCodeSchemesLight(null, null, null, null)
				.getLightXmlObjectList().getLightXmlObjectList();
		CodeSchemeDocument result = null;
		for (LightXmlObjectType lightXmlObject : codeSchemeRefList) {
			if (lightXmlObject.getId().equals(refRes.getId())) {
				result = DdiManager.getInstance().getCodeScheme(
						lightXmlObject.getId(), lightXmlObject.getVersion(),
						lightXmlObject.getParentId(),
						lightXmlObject.getParentVersion());
				break;
			}
		}
		return result;
	}

	@Override
	public void delete(String id, String version, String parentId,
			String parentVersion) throws Exception {
		IModel model = getModel(id, version, parentId, parentVersion);
		DdiManager.getInstance().deleteElement(model.getDocument(),
				model.getParentId(), model.getParentVersion(),
				DaoHelper.defineParent("logicalproduct__LogicalProduct"));
	}

	@Override
	public IModel create(String id, String version, String parentId,
			String parentVersion) throws Exception {

		CodeSchemeDocument doc = CodeSchemeDocument.Factory.newInstance();
		IdentificationManager.getInstance().addIdentification(
				doc.addNewCodeScheme(), ElementType.CODE_SCHEME.getIdPrefix(),
				null);
		IdentificationManager.getInstance().addVersionInformation(
				doc.getCodeScheme(), null, null);
		CodeScheme model = new CodeScheme(doc, parentId, parentVersion);
		return model;
	}

	@Override
	public void create(IModel model) throws DDIFtpException {
		DdiManager
				.getInstance()
				.createElement(
						model.getDocument(),
						model.getParentId(),
						model.getParentVersion(),
						DaoHelper
								.defineParent("logicalproduct__LogicalProduct"),
						// parentSubElements - elements of parent
						new String[] { "VersionRationale",
								"VersionResponsibility", "LogicalProductName",
								"Label", "Description", "Coverage", "Citation",
								"group__Abstract", "group__Purpose" },
						// stopElements - do not search below ...
						new String[] { "VariableScheme",
								"VariableSchemeReference" },
						// jumpElements - jump over elements
						new String[] { "DataRelationship", "OtherMaterial",
								"Note", "CategoryScheme", "CodeScheme",
								"logicalproduct__CodeSchemeReference" });
	}

	@Override
	public List<LightXmlObjectType> getLightXmlObject(
			LightXmlObjectType parentCodeScheme) throws Exception {
		return getLightXmlObject("", "", parentCodeScheme.getId(),
				parentCodeScheme.getVersion());
	}

	@Override
	public List<LightXmlObjectType> getLightXmlObject(String id,
			String version, String parentId, String parentVersion)
			throws Exception {
		List<LightXmlObjectType> lightXmlObjectTypeList = DdiManager
				.getInstance()
				.getCodeSchemesLight(id, version, parentId, parentVersion)
				.getLightXmlObjectList().getLightXmlObjectList();
		return lightXmlObjectTypeList;
	}

	@Override
	public IModel getModel(String id, String version, String parentId,
			String parentVersion) throws Exception {
		CodeSchemeDocument doc = DdiManager.getInstance().getCodeScheme(id,
				version, parentId, parentVersion);
		return doc == null ? null
				: new CodeScheme(doc, parentId, parentVersion);
	}

	@Override
	public void update(IModel model) throws DDIFtpException {
		try {
			// TODO Version Control - not supported
			DdiManager.getInstance().updateElement(model.getDocument(),
					model.getId(), model.getVersion());
		} catch (DDIFtpException e) {
			log.error("Update Code Scheme error: " + e.getMessage());

			throw new DDIFtpException(e.getMessage());
		}

		// DaoSchemeHelper.update(model);
	}
}
