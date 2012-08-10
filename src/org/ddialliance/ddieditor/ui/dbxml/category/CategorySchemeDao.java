package org.ddialliance.ddieditor.ui.dbxml.category;

import java.util.ArrayList;
import java.util.List;

import org.ddialliance.ddi3.xml.xmlbeans.logicalproduct.CategorySchemeDocument;
import org.ddialliance.ddieditor.logic.urn.ddi.ReferenceResolution;
import org.ddialliance.ddieditor.model.DdiManager;
import org.ddialliance.ddieditor.model.lightxmlobject.LightXmlObjectType;
import org.ddialliance.ddieditor.model.resource.DDIResourceType;
import org.ddialliance.ddieditor.persistenceaccess.PersistenceManager;
import org.ddialliance.ddieditor.persistenceaccess.maintainablelabel.MaintainableLabelQueryResult;
import org.ddialliance.ddieditor.ui.dbxml.DaoHelper;
import org.ddialliance.ddieditor.ui.dbxml.IDao;
import org.ddialliance.ddieditor.ui.model.IModel;
import org.ddialliance.ddieditor.ui.model.LabelDescriptionScheme;
import org.ddialliance.ddieditor.ui.model.category.CategoryScheme;
import org.ddialliance.ddiftp.util.DDIFtpException;
import org.ddialliance.ddiftp.util.log.Log;
import org.ddialliance.ddiftp.util.log.LogFactory;
import org.ddialliance.ddiftp.util.log.LogType;

public class CategorySchemeDao implements IDao {
	private static Log log = LogFactory.getLog(LogType.SYSTEM,
			CategorySchemeDao.class);
	
	public static List<LightXmlObjectType> getAllCategorySchemesLight(String id,
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
					.getCategorySchemesLight(id, version, null, null)
					.getLightXmlObjectList().getLightXmlObjectList());
		}

		PersistenceManager.getInstance().setWorkingResource(workingresource);
		return lightXmlObjectTypeList;
	}

	@Override
	public IModel create(String id, String version, String parentId,
			String parentVersion) throws Exception {
		MaintainableLabelQueryResult maintainableLabelQueryResult = LabelDescriptionScheme
				.createLabelDescriptionScheme("CategoryScheme");

		return new CategoryScheme(maintainableLabelQueryResult.getId(),
				maintainableLabelQueryResult.getVersion(), parentId,
				parentVersion, maintainableLabelQueryResult.getAgency(),
				maintainableLabelQueryResult);
	}

	@Override
	public void create(IModel model) throws DDIFtpException {
		DdiManager.getInstance().createElement(model.getDocument(),
				model.getParentId(),
				model.getParentVersion(),
				DaoHelper.defineParent("logicalproduct__LogicalProduct"),
				// parentSubElements - elements of parent
				new String[] { "VersionRationale", "VersionResponsibility",
						"LogicalProductName", "Label", "Description",
						"Coverage", "Citation", "group__Abstract",
						"group__Purpose" },
				// stopElements - do not search below ...
				new String[] { "CodeScheme", "logicalproduct__CodeSchemeReference",
						"VariableScheme", "VariableSchemeReference" },
				// jumpElements - jump over elements
				new String[] { "DataRelationship", "OtherMaterial", "Note",
						"CategoryScheme", });
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
	public List<LightXmlObjectType> getLightXmlObject(
			LightXmlObjectType parentCategoryScheme) throws Exception {
		return getLightXmlObject("", "", parentCategoryScheme.getId(),
				parentCategoryScheme.getVersion());
	}

	@Override
	public List<LightXmlObjectType> getLightXmlObject(String id,
			String version, String parentId, String parentVersion)
			throws Exception {
		List<LightXmlObjectType> lightXmlObjectTypeList = DdiManager
				.getInstance()
				.getCategorySchemesLight(id, version, parentId, parentVersion)
				.getLightXmlObjectList().getLightXmlObjectList();

		return lightXmlObjectTypeList;
	}

	@Override
	public CategoryScheme getModel(String id, String version, String parentId,
			String parentVersion) throws Exception {
		MaintainableLabelQueryResult maintainableLabelQueryResult = DdiManager
				.getInstance().getCategorySchemeLabel(id, version, parentId,
						parentVersion);

		return new CategoryScheme(id, version, parentId, parentVersion,
				maintainableLabelQueryResult.getAgency(),
				maintainableLabelQueryResult);
	}

	/**
	 * Get category scheme by reference
	 * 
	 * @param reference
	 *            resolution
	 * @return category scheme document
	 * @throws Exception
	 */
	public static CategorySchemeDocument getCategorySchemeByReference(
			ReferenceResolution refRes) throws Exception {
		List<LightXmlObjectType> catSchemesLight = DdiManager.getInstance()
				.getCategorySchemesLight(null, null, null, null)
				.getLightXmlObjectList().getLightXmlObjectList();
		for (LightXmlObjectType lightXmlObject : catSchemesLight) {
			if (lightXmlObject.getId().equals(refRes.getId())) {
				return DdiManager.getInstance().getCategoryScheme(
						lightXmlObject.getId(), lightXmlObject.getVersion(),
						lightXmlObject.getParentId(),
						lightXmlObject.getParentVersion());
			}
		}
		return null;
	}

	public static CategorySchemeDocument getAllCategorySchemeByReference(
			ReferenceResolution refRes) throws Exception {
		CategorySchemeDocument result = null;

		List<DDIResourceType> resources = PersistenceManager.getInstance()
				.getResources();
		String workingresource = PersistenceManager.getInstance()
				.getWorkingResource();

		for (DDIResourceType resource : resources) {
			PersistenceManager.getInstance().setWorkingResource(
					resource.getOrgName());
			result = getCategorySchemeByReference(refRes);
			if (result != null) {
				break;
			}
		}

		PersistenceManager.getInstance().setWorkingResource(workingresource);
		return result;
	}

	@Override
	public void update(IModel model) throws DDIFtpException {
		DaoHelper.updateScheme(model);
	}
}
