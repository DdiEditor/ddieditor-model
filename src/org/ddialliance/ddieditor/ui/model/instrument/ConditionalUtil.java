package org.ddialliance.ddieditor.ui.model.instrument;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ddialliance.ddieditor.ui.model.DdiModelException;
import org.ddialliance.ddieditor.ui.model.ElementType;
import org.ddialliance.ddiftp.util.DDIFtpException;
import org.ddialliance.ddiftp.util.Translator;

public class ConditionalUtil {
	public static final String conditionalPattern = "^([vV][1-9]+[0-9]*((>{1}|>={1}|<{1}|!={1}|<={1}|={2})[0-9]*)*)+";

	/**
	 * Extract unique variable names from conditions
	 * 
	 * @param expression
	 *            conditional expression
	 * @return list of IDs
	 */
	public static String[] extractUniqueIDs(String expression) {
		// reference contains a logical expression - extract IDs and remove
		// duplicates
		String varIds[] = expression.split("\\|\\||&&");
		HashMap<String, String> varIDs = new HashMap<String, String>();
		for (int i = 0; i < varIds.length; i++) {
			String varId[] = varIds[i].split(">|>=|<|=<|==|!=");
			if (varId.length == 2) {
				varIDs.put(varId[0], varId[0]);
			}
		}
		return varIDs.keySet().toArray(new String[varIDs.size()]);
	}

	public static boolean validCondition(String expression) {
		Pattern pattern = Pattern.compile(ConditionalUtil.conditionalPattern);
		String conds[] = expression.split("&&|\\|\\|");
		for (int i = 0; i < conds.length; i++) {
			Matcher matcher = pattern.matcher(conds[i]);
			if (!matcher.matches()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Validate a DDA instrument conditions
	 * 
	 * @param expression
	 * @throws DDIFtpException
	 *             if not valid
	 */
	public static void validateCondition(String expression)
			throws DdiModelException {
			if (!validCondition(expression)) {
				throw new DdiModelException(Translator.trans(
						"instrument.condition.notcorrect",
						new Object[] { expression }),
						DdiModelException.Sevrity.FATAL);
			}
	}
}
