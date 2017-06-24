package nl.mwensveen.constantnameconverter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

public class Converter {
	private static final String VALID_VARIABLE_NAME = "[a-zA-Z_][a-zA-Z0-9_]*";
	private static final Pattern VALID_VARIABLE_NAME_PATTERN = Pattern.compile(VALID_VARIABLE_NAME);
	private static final String CONSTANT_NAME = "[A-Z_][A-Z0-9_]*";
	private static final Pattern CONSTANT_NAME_PATTERN = Pattern.compile(CONSTANT_NAME);
	private static final String CONSTANT_NAME_SPLIT = "([^_]*)";
	private static final Pattern CONSTANT_NAME_SPLIT_PATTERN = Pattern.compile(CONSTANT_NAME_SPLIT);
	private static final String VARIABLE_NAME_FIRST = "([a-z]*)";
	private static final Pattern VARIABLE_NAME_FIRST_PATTERN = Pattern.compile(VARIABLE_NAME_FIRST);
	private static final String VARIABLE_NAME_SPLIT = "([A-Z0-9_][a-z]*)";
	private static final Pattern VARIABLE_NAME_SPLIT_PATTERN = Pattern.compile(VARIABLE_NAME_SPLIT);
	private String text;

	public Converter(String text) {
		this.text = text;
	}

	public String convert() {
		if (StringUtils.isNotBlank(text)) {
			int numberOfSpaceStart = getNumberOfSpacesStart(text);
			int numberOfSpaceEnd = getNumberOfSpacesEnd(text);
			text = StringUtils.strip(text);
			if (VALID_VARIABLE_NAME_PATTERN.matcher(text).matches()) {
				String newText = null;
				if (CONSTANT_NAME_PATTERN.matcher(text).matches()) {
					newText = convertFromConstant();
				} else {
					newText = convertFromVariable();
				}
				return addSpaces(newText, numberOfSpaceStart, numberOfSpaceEnd);
			}
		}
		return null;
	}

	private String addSpaces(String text2, int numberOfSpaceStart, int numberOfSpaceEnd) {
		String t = StringUtils.leftPad(text2, text2.length() + numberOfSpaceStart);
		t = StringUtils.rightPad(t, t.length() + numberOfSpaceEnd);
		return t;
	}

	private int getNumberOfSpacesEnd(String text2) {
		int space = 0;
		String t = StringUtils.removeEnd(text2, " ");
		while (t != text2) {
			space++;
			text2 = t;
			t = StringUtils.removeEnd(text2, " ");
		}
		return space;
	}

	private int getNumberOfSpacesStart(String text2) {
		int space = 0;
		String t = StringUtils.removeStart(text2, " ");
		while (t != text2) {
			space++;
			text2 = t;
			t = StringUtils.removeStart(text2, " ");
		}
		return space;
	}

	private String convertFromVariable() {
		StringBuffer sb = new StringBuffer();
		boolean first = true;
		Matcher matcherOne = VARIABLE_NAME_FIRST_PATTERN.matcher(text);
		if (matcherOne.find()) {
			String stringOne = matcherOne.group();
			if (StringUtils.isNotBlank(stringOne)) {
				sb.append(toUpperCase(stringOne, !first));
				first = false;
			}
		}
		Matcher matcher = VARIABLE_NAME_SPLIT_PATTERN.matcher(text);
		while (matcher.find()) {
			String string = matcher.group();
			if (StringUtils.isNotBlank(string)) {
				sb.append(toUpperCase(string, !first));
				first = false;
			}
		}
		return sb.toString();
	}

	private String toUpperCase(String string, boolean addUnderscore) {
		String prefix = addUnderscore && !string.startsWith("_") ? "_" : "";
		String upper = StringUtils.upperCase(string);
		return prefix + upper;
	}

	private String convertFromConstant() {
		StringBuffer sb = new StringBuffer();
		Matcher matcher = CONSTANT_NAME_SPLIT_PATTERN.matcher(text);
		boolean first = true;
		while (matcher.find()) {
			String string = matcher.group();
			if (StringUtils.isNotBlank(string)) {
				sb.append(toLowerCase(string, !first));
				first = false;
			}
		}
		return sb.toString();
	}

	private Object toLowerCase(String string, boolean camelCase) {
		if (camelCase) {
			return WordUtils.capitalizeFully(string);
		} else {
			return StringUtils.lowerCase(string);
		}
	}
}
