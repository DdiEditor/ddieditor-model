package org.ddialliance.ddieditor.ui.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.ddialliance.ddieditor.model.conceptual.ConceptualType;
import org.ddialliance.ddiftp.util.DDIFtpException;
import org.ddialliance.ddiftp.util.Translator;
import org.ddialliance.ddiftp.util.log.Log;
import org.ddialliance.ddiftp.util.log.LogFactory;
import org.ddialliance.ddiftp.util.log.LogType;

/**
 * Type to bind DDI elements with Eclipse RCP via the following properties:
 * <ul>
 * <li>elementName - DDI local name</li>
 * <li>perspectiveId - preferred RCP perspective ID</li>
 * <li>editorId - corresponding RCP editor ID</li>
 * <li>idPrefix - prefix for DDI ID generation</li>
 * <li>displayMessageEntry - message key for retrieving i18n label of
 * elementName</li>
 * <li>if OPEN - support for open view given
 * <li>list of child elements (e.g. Multiple Question Item and Question Item are
 * subelements to Question Scheme)
 * </ul>
 */
public enum ElementType {
	// application
	FILE("", getID("InfoPerspective.ID"), getID("FileEditor.ID"), "",
			"ddi3file.label", "", null), MAINTAINABLE_LIGHTLABEL("", "", "",
			"", "", "", null),
	// note
	NOTE("Note", null, null, "note", "Note.label", "", null),

	// study unit
	CONCEPTUAL_STUDY_UNIT("studyunit__StudyUnit", "",
			getID("StudyUnitEditor.ID"), "",
			"InfoView.label.studyUnitLabel.StudyUnit", "OPEN", null), STUDY_UNIT(
			"studyunit__StudyUnit", "", getID("StudyUnitEditor.ID"), "stdu",
			"InfoView.label.studyUnitLabel.StudyUnit", "OPEN", null),

	// resource package
	RESOURCE_PACKAGE("ResourcePackage", null, null, "resp", "", "", null),

	// abstract
	ABSTRACT("Abstract", null, null, "abst", "", "", null),

	// purpose
	PURPOSE("Purpose", null, null, "purp", "", "", null),

	// topical coverage
	TOPICAL_COVERAGE("TopicalCoverage", null, null, "topcov", "", "", null),

	// spatial coverage
	SPATIAL_COVERAGE("SpatialCoverage", null, null, "spacov", "", "", null),

	// temporal coverage
	TEMPORAL_COVERAGE("TemporalCoverage", null, null, "tmpcov", "", "", null),

	// universe
	UNIVERSE("Universe", getID("UniversePerspective.ID"),
			getID("UniverseEditor.ID"), "univ",
			"UniverseView.label.universeLabel.Universe", "", null), UNIVERSE_SCHEME(
			"UniverseScheme", getID("UniversePerspective.ID"),
			getID("UniverseSchemeEditor.ID"), "unis",
			"UniverseView.label.universeSchemeLabel.UniverseScheme", "", Arrays
					.asList(ElementType.UNIVERSE)),

	// geographic
	GEOGRAPHICSTRUCTURESCHEME("GeographicStructureScheme", null, null,
			"geostrs", "", "", null), GEOGRAPHICSTRUCTURE(
			"GeographicStructure", null, null, "geostr", "", "", null), GEOGRAPHY(
			"Geography", null, null, "geo", "", "", null),

	// conceptual component
	CONCEPTUAL_COMPONENT("ConceptualComponent", null, null, "coco",
			"ConceptualComponent.label", "", null),

	// concept
	CONCEPT("Concept", getID("ConceptsPerspective.ID"),
			getID("ConceptEditor.ID"), "conc",
			"ConceptView.label.conceptLabel.Concept", "", null), CONCEPT_SCHEME(
			"ConceptScheme", getID("ConceptsPerspective.ID"),
			getID("ConceptSchemeEditor.ID"), "cons",
			"ConceptView.label.conceptSchemeLabel.ConceptScheme", "", Arrays
					.asList(ElementType.CONCEPT)),

	// data collection
	DATA_COLLECTION("datacollection__DataCollection", null, null, "daco",
			"DataCollection.label", "", null),
	// methodology
	METHODOLOGY("methodology", null, null, "method", "", "", null),

	// data collection methodology
	DATA_COLLECTION_METHODOLOGY("datacollectionmethodology", null, null,
			"dcmethod", "", "", null),

	// question
	QUESTION_ITEM("QuestionItem", getID("QuestionsPerspective.ID"),
			getID("QuestionItemEditor.ID"), "quei",
			"QuestionItemView.label.questionItemLabel.QuestionItem", "", null), SUB_QUESTION_ITEM(
			"QuestionItem", getID("QuestionsPerspective.ID"),
			getID("QuestionItemEditor.ID"), "quei",
			"QuestionItemView.label.questionItemLabel.SubQuestionItem", "",
			null), MULTIPLE_QUESTION_ITEM(
			"MultipleQuestionItem",
			getID("QuestionsPerspective.ID"),
			getID("MultipleQuestionItemEditor.ID"),
			"mquei",
			"QuestionItemView.label.multipleQuestionItemLabel.MultipleQuestionItem",
			"", Arrays.asList(ElementType.SUB_QUESTION_ITEM)), QUESTION_SCHEME(
			"QuestionScheme", getID("QuestionsPerspective.ID"),
			getID("QuestionSchemeEditor.ID"), "ques",
			"QuestionItemView.label.questionSchemeLabel.QuesitionScheme", "",
			Arrays.asList(ElementType.MULTIPLE_QUESTION_ITEM,
					ElementType.QUESTION_ITEM)),

	// category
	CATEGORY("Category", getID("CategoryPerspective.ID"),
			getID("CategoryEditor.ID"), "cat",
			"CategoryView.label.categoryLabel.Category", "", null), CATEGORY_SCHEME(
			"CategoryScheme", getID("CategoryPerspective.ID"),
			getID("CategorySchemeEditor.ID"), "cats",
			"CategoryView.label.categorySchemeLabel.CategoryScheme", "", Arrays
					.asList(ElementType.CATEGORY)),

	// instrument
	QUESTION_CONSTRUCT("QuestionConstruct", getID("InstrumentPerspective.ID"),
			getID("QuestionConstructEditor.ID"), "quec",
			"InstrumentView.QuestionConstruct.label", "", null), STATEMENT_ITEM(
			"StatementItem", getID("InstrumentPerspective.ID"),
			getID("StatementItemEditor.ID"), "stai",
			"InstrumentView.StatementItem.label", "", null), IF_THEN_ELSE(
			"IfThenElse", getID("InstrumentPerspective.ID"),
			getID("IfThenElseEditor.ID"), "ifth",
			"InstrumentView.IfThenElse.label", "", null), REPEAT_UNTIL(
			"RepeatUntil", getID("InstrumentPerspective.ID"),
			getID("RepeatUntilEditor.ID"), "repu",
			"InstrumentView.RepeatUntil.label", "", null), LOOP("Loop",
			getID("InstrumentPerspective.ID"), getID("LoopEditor.ID"), "loop",
			"InstrumentView.Loop.label", "", null), REPEAT_WHILE("RepeatWhile",
			getID("InstrumentPerspective.ID"), getID("RepeatWhileEditor.ID"),
			"repw", "InstrumentView.RepeatWhile.label", "", null), SEQUENCE(
			"Sequence", getID("InstrumentPerspective.ID"),
			getID("SequenceEditor.ID"), "seqc",
			"InstrumentView.Sequence.label", "", null), COMPUTATION_ITEM(
			"ComputationItem", getID("InstrumentPerspective.ID"),
			getID("ComputationItemEditor.ID"), "copi",
			"InstrumentView.ComputationItem.label", "", null), CONTROL_CONSTRUCT_SCHEME(
			"ControlConstructScheme", getID("InstrumentPerspective.ID"),
			getID("ControlConstructSchemeEditor.ID"), "cocs",
			"InstrumentView.ControlConstructScheme.label", "", Arrays.asList(
					ElementType.QUESTION_CONSTRUCT, ElementType.STATEMENT_ITEM,
					ElementType.IF_THEN_ELSE, ElementType.REPEAT_UNTIL,
					ElementType.LOOP, ElementType.REPEAT_WHILE,
					ElementType.SEQUENCE, ElementType.COMPUTATION_ITEM)), INSTRUMENT(
			"Instrument", getID("InstrumentPerspective.ID"),
			getID("InstrumentEditor.ID"), "inst",
			"InstrumentItemView.label.instrumentItemLabel.Instrument", "", null), INSTRUCTION(
			"InterviewerInstruction", null, null, "intv",
			"InterviewerInstruction.label", "", null), INTERVIEWER_INSTRUCTION_SCHEME(
			"InterviewerInstructionScheme", getID("InstrumentPerspective.ID"),
			getID("InstrumentPerspective.ID"), "invs", "InterviewerInstructionScheme", "",
			Arrays.asList(ElementType.INSTRUCTION)),

	// logical product
	LOGICAL_PRODUCT("logicalproduct__LogicalProduct", null, null, "lopr",
			"LogicalProduct.label", "", null), DATA_RELATIONSHIP(
			"DataRelationship", null, null, "dars", "DataRelationship.label",
			"", null),

	// code
	CODE("Code", null, null, "code", "Code.label", "", null), CODE_SCHEME(
			"CodeScheme", getID("CodesPerspective.ID"),
			getID("CodeSchemeEditor.ID"), "cods",
			"codeView.label.codeSchemeLabel.CodeScheme", "", null),

	// variable
	VARIABLE("Variable", getID("VariablePerspective.ID"),
			getID("VariableEditor.ID"), "vari", "Variable", "", null), VARIABLE_SCHEME(
			"VariableScheme", getID("VariablePerspective.ID"),
			getID("VariableSchemeEditor.ID"), "vars", "VariableScheme", "",
			Arrays.asList(ElementType.VARIABLE)),

	// physical data product
	PHYSICAL_DATA_PRODUCT("PhysicalDataProduct", null, null, "phdp",
			"PhysicalDataProduct.label", "", null),

	// physical instance
	PHYSICAL_INSTANCE("PhysicalInstance", null, null, "phin",
			"PhysicalInstance.label", "", null), GROSS_FILE_STRUCTURE(
			"GrossFileStructure", null, null, "grfs",
			"GrossFileStructure.label", "", null), GROSS_RECORD_STRUCTURE(
			"GrossRecordStructure", null, null, "grst",
			"GrossRecordStructure.label", "", null), LOGICAL_RECORD(
			"LogicalRecord", null, null, "lore", "LogicalRecord.label", "",
			null), PHYSICAL_RECORDSEGMENT("PhysicalRecordSegment", null, null,
			"phrs", "PhysicalRecordSegment.label", "", null), PHYSICAL_STRUCTURE_SCHEME(
			"PhysicalStructureScheme", null, null, "phss",
			"PhysicalStructureScheme.label", "", null), PHYSICAL_STRUCTURE(
			"PhysicalStructure", null, null, "phst", "PhysicalStructure.label",
			"", null), RECORD_LAYOUT_SCHEME("RecordLayoutScheme", null, null,
			"rels", "RecordLayoutScheme.label", "", null), RECORD_LAYOUT(
			"RecordLayout", null, null, "rely", "RecordLayout.label", "", null), DATA_FILE_IDENTIFICATION(
			"DataFileIdentification", null, null, "dafi",
			"DataFileIdentification.label", "", null),

	// reusable
	SOFTWARE("Software", null, null, "sofw", "Software.label", "", null),

	// archive
	INDIVIDUAL("Individual", null, null, "indi", "Individual.label", "", null);

	private static Log log = LogFactory.getLog(LogType.EXCEPTION,
			ElementType.class);

	private static Map idCache = null;

	private String elementName;
	private String perspectiveId;
	private String editorId;
	private String idPrefix;
	private String displayMessageEntry;
	private String withOpen;
	private List<ElementType> subElements;

	/**
	 * Constructor
	 * 
	 * @param elementName
	 *            DDI local name
	 * @param perspectiveId
	 *            RCP prospective ID
	 * @param editorId
	 *            editor ID
	 * @param idPrefix
	 *            prefix for DDI ID generation
	 * @param displayMessageEntry
	 *            message key for retrieving i18n label of elementName
	 * @param withOpen
	 *            view supported
	 * @param subElements
	 *            of additional subelements
	 */
	private ElementType(String elementName, String perspectiveId,
			String editorId, String idPrefix, String displayMessageEntry,
			String withOpen, List<ElementType> subElements) {
		this.elementName = elementName;
		this.perspectiveId = perspectiveId;
		this.editorId = editorId;
		this.idPrefix = idPrefix;
		this.displayMessageEntry = displayMessageEntry;
		this.withOpen = withOpen;
		this.subElements = subElements;
	}

	private static String getID(String key) {
		if (idCache == null) {
			idCache = new HashMap();
		}
		String value = (String) idCache.get(key);
		if (value == null) {
			// read property file
			Properties properties = new Properties();
			File file = new File("resources" + File.separator
					+ "model-editor-id.properties");
			try {
				FileInputStream fileInputStream = new FileInputStream(file);
				properties.load(fileInputStream);
				fileInputStream.close();
			} catch (FileNotFoundException e) {
				System.out
						.println("'model-editor-id.properties' file not found: "
								+ e.getMessage());
				return null;
			} catch (IOException e) {
				System.out
						.println("IO error reading 'model-editor-id.properties' file: "
								+ e.getMessage());
				return null;
			}

			// initialize cache
			Enumeration<Object> enumration = properties.keys();
			while (enumration.hasMoreElements()) {
				String propKey = (String) enumration.nextElement();
				value = properties.getProperty(propKey);
				idCache.put(propKey, value);
			}
			value = (String) idCache.get(key);
		}
		return value;
	}

	/**
	 * TODO concider agency access in non rcp concept!!! ddieditor module can
	 * not depend ddieditor-ui Solution refactor
	 * org.ddialliance.ddieditor.ui.model into model
	 */
	public static String getAgency() {
		return "dk.dda";
	}

	public String getElementName() {
		return elementName;
	}

	public String getPerspectiveId() {
		return perspectiveId;
	}

	public String getEditorId() {
		return editorId;
	}

	public String getIdPrefix() {
		return idPrefix;
	}

	/**
	 * Translate message key
	 * 
	 * @return translated message key
	 */
	public String getTranslatedDisplayMessageEntry() {
		return Translator.trans(displayMessageEntry);
	}

	public String getWithOpen() {
		return withOpen;
	}

	public List<ElementType> getSubElements() {
		return subElements;
	}

	public static String getPerspectiveId(String elementName)
			throws DDIFtpException {
		for (int i = 0; i < ElementType.values().length; i++) {
			ElementType elementType = ElementType.values()[i];
			if (elementType.getElementName().equals(elementName)) {
				return elementType.getPerspectiveId();
			}
		}
		// not found
		DDIFtpException e = new DDIFtpException(
				Translator.trans("editor.editelement.notimplemented"),
				new Object[] { elementName }, new Throwable());
		throw e;
	}

	public static ElementType getElementType(String elementName)
			throws DDIFtpException {
		for (int i = 0; i < ElementType.values().length; i++) {
			ElementType elementType = ElementType.values()[i];
			if (elementType.getElementName().equals(elementName)) {
				return elementType;
			}
		}
		// not found
		DDIFtpException e = new DDIFtpException(
				Translator.trans("editor.editelement.notimplemented"),
				new Object[] { elementName }, new Throwable());
		throw e;
	}

	public static boolean withOpenMenuItem(String elementName)
			throws DDIFtpException {
		for (int i = 0; i < ElementType.values().length; i++) {
			ElementType elementType = ElementType.values()[i];
			if (elementType.getElementName().equals(elementName)) {
				return (elementType.getWithOpen().equals("") ? false : true);
			}
		}
		// not found
		DDIFtpException e = new DDIFtpException(
				Translator.trans("editor.editelement.notimplemented"),
				new Object[] { elementName }, new Throwable());
		throw e;
	}

	public static ElementType getElementTypeByConceptualType(
			ConceptualType conceptualType) throws DDIFtpException {
		if (conceptualType.equals(ConceptualType.STUDY)) {
			return ElementType.STUDY_UNIT;
		} else if (conceptualType.equals(ConceptualType.LOGIC_UNIVERSE)) {
			return ElementType.UNIVERSE_SCHEME;
		} else if (conceptualType.equals(ConceptualType.LOGIC_CONCEPT)) {
			return ElementType.CONCEPT_SCHEME;
		} else if (conceptualType.equals(ConceptualType.LOGIC_CATEGORY)) {
			return ElementType.CATEGORY_SCHEME;
		} else if (conceptualType.equals(ConceptualType.LOGIC_CODE)) {
			return ElementType.CODE_SCHEME;
		} else if (conceptualType.equals(ConceptualType.LOGIC_QUESTION)) {
			return ElementType.QUESTION_SCHEME;
		} else if (conceptualType.equals(ConceptualType.LOGIC_INSTRUMENT)) {
			return ElementType.INSTRUMENT;
		} else if (conceptualType.equals(ConceptualType.LOGIC_VARIABLE)) {
			return ElementType.VARIABLE_SCHEME;
		} else if (conceptualType
				.equals(ConceptualType.LOGIC_CONTROL_CONSTRUCT)) {
			return ElementType.CONTROL_CONSTRUCT_SCHEME;
		}
		// not found
		throw new DDIFtpException(
				Translator.trans("editor.editelement.notimplemented"),
				new Object[] { conceptualType }, new Throwable());
	}
}