package org.ddialliance.ddieditor.ui.dbxml.variable;

import java.util.List;

import org.ddialliance.ddieditor.model.DdiManager;
import org.ddialliance.ddieditor.model.lightxmlobject.LightXmlObjectType;
import org.ddialliance.ddieditor.persistenceaccess.maintainablelabel.MaintainableLabelQueryResult;
import org.ddialliance.ddieditor.ui.dbxml.DaoHelper;
import org.ddialliance.ddieditor.ui.dbxml.IDao;
import org.ddialliance.ddieditor.ui.model.IModel;
import org.ddialliance.ddieditor.ui.model.LabelDescriptionScheme;
import org.ddialliance.ddieditor.ui.model.variable.VariableScheme;
import org.ddialliance.ddiftp.util.DDIFtpException;

public class VariableSchemeDao implements IDao {
	@Override
	public List<LightXmlObjectType> getLightXmlObject(LightXmlObjectType parent)
			throws Exception {
		return getLightXmlObject("", "", parent.getId(), parent.getVersion());
	}

	@Override
	public List<LightXmlObjectType> getLightXmlObject(String id,
			String version, String parentId, String parentVersion)
			throws Exception {
		return DdiManager.getInstance()
				.getVariableSchemesLight(id, version, parentId, parentVersion)
				.getLightXmlObjectList().getLightXmlObjectList();
	}

	@Override
	public VariableScheme create(String id, String version, String parentId,
			String parentVersion) throws Exception {

		MaintainableLabelQueryResult maintainableLabelQueryResult = LabelDescriptionScheme
				.createLabelDescriptionScheme("VariableScheme");

		return new VariableScheme(maintainableLabelQueryResult.getId(),
				maintainableLabelQueryResult.getVersion(), parentId,
				parentVersion, maintainableLabelQueryResult.getAgency(),
				maintainableLabelQueryResult);
	}

	@Override
	public VariableScheme getModel(String id, String version, String parentId,
			String parentVersion) throws Exception {
		MaintainableLabelQueryResult maintainableLabelQueryResult = DdiManager
				.getInstance().getVariableSchemeLabel(id, version, parentId,
						parentVersion);

		return new VariableScheme(id, version, parentId, parentVersion,
				maintainableLabelQueryResult.getAgency(),
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
				new String[] {},
				// jumpElements - jump over elements
				new String[] { "DataRelationship", "OtherMaterial", "Note",
						"CategoryScheme", "CodeScheme", "logicalproduct__CodeSchemeReference",
						"VariableScheme", "VariableSchemeReference" });
	}

	@Override
	public void update(IModel model) throws DDIFtpException {
		DaoHelper.updateScheme(model);
	}

	@Override
	public void delete(String id, String version, String parentId,
			String parentVersion) throws Exception {
		VariableScheme model = getModel(id, version, parentId, parentVersion);
		DdiManager.getInstance().deleteElement(model.getDocument(), parentId,
				parentVersion,
				DaoHelper.defineParent("logicalproduct__LogicalProduct"));
	}
}
