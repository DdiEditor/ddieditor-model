package org.ddialliance.ddieditor.ui.dbxml.instrument;

import java.util.List;

import org.ddialliance.ddieditor.model.DdiManager;
import org.ddialliance.ddieditor.model.lightxmlobject.LightXmlObjectType;
import org.ddialliance.ddieditor.persistenceaccess.maintainablelabel.MaintainableLabelQueryResult;
import org.ddialliance.ddieditor.ui.dbxml.DaoHelper;
import org.ddialliance.ddieditor.ui.dbxml.IDao;
import org.ddialliance.ddieditor.ui.model.IModel;
import org.ddialliance.ddieditor.ui.model.LabelDescriptionScheme;
import org.ddialliance.ddieditor.ui.model.instrument.ControlConstructScheme;
import org.ddialliance.ddieditor.util.LightXmlObjectUtil;
import org.ddialliance.ddiftp.util.DDIFtpException;

public class ControlConstructSchemeDao implements IDao {
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
				.getControlConstructSchemesLight(id, version, parentId, parentVersion)
				.getLightXmlObjectList().getLightXmlObjectList();
	}

	@Override
	public ControlConstructScheme create(String id, String version,
			String parentId, String parentVersion) throws Exception {

		MaintainableLabelQueryResult maintainableLabelQueryResult = LabelDescriptionScheme
				.createLabelDescriptionScheme("ControlConstructScheme");

		return new ControlConstructScheme(maintainableLabelQueryResult.getId(),
				maintainableLabelQueryResult.getVersion(), parentId,
				parentVersion, maintainableLabelQueryResult.getAgency(),
				maintainableLabelQueryResult);
	}

	@Override
	public ControlConstructScheme getModel(String id, String version,
			String parentId, String parentVersion) throws Exception {
		MaintainableLabelQueryResult maintainableLabelQueryResult = DdiManager
				.getInstance().getControlConstructSchemeLabel(id, version,
						parentId, parentVersion);

		return new ControlConstructScheme(id, version, parentId, parentVersion,
				maintainableLabelQueryResult.getAgency(),
				maintainableLabelQueryResult);
	}

	@Override
	public void create(IModel model) throws DDIFtpException {
		DaoHelper.createScheme(model, LightXmlObjectUtil.createLightXmlObject(
				model.getAgency(), model.getParentId(),
				model.getParentVersion(), null, null,
				"datacollection__DataCollection"));
	}

	@Override
	public void update(IModel model) throws DDIFtpException {
		DaoHelper.updateScheme(model);
	}

	@Override
	public void delete(String id, String version, String parentId,
			String parentVersion) throws Exception {
		ControlConstructScheme model = getModel(id, version, parentId,
				parentVersion);
		DdiManager.getInstance().deleteElement(model.getDocument(),
				model.getParentId(), model.getParentVersion(),
				DaoHelper.defineParent("datacollection__DataCollection"));
	}
}
