package com.prabs.json.reader;

import java.util.LinkedList;
import java.util.List;

public class JsonLexer {

	String json;
	int offset;

	List<Object> tokens;

	public JsonLexer(String json) {
		this.json = json;
		this.offset = 0;
		this.tokens = new LinkedList<>();
	}

	private boolean matchWithString() {
		if (json.charAt(offset) == '"') {
			offset++;
		} else {
			return false;
		}
		StringBuilder buffer = new StringBuilder();
		while (offset < json.length()) {
			if (json.charAt(offset) == '"') {
				offset++;
				tokens.add(buffer.toString());
				return true;
			} else {
				buffer.append(json.charAt(offset++));
			}
		}
		return false;
	}

	private boolean matchWithNumber() {
		StringBuilder buffer = new StringBuilder();
		while (offset < json.length()) {
			if ((json.charAt(offset) >= '0' && json.charAt(offset) <= '9') || json.charAt(offset) == '-'
					|| json.charAt(offset) == '.') {
				buffer.append(json.charAt(offset++));
			} else {
				break;
			}
		}
		if (buffer.length() == 0) {
			return false;
		}
		if (buffer.toString().contains(".")) {
			tokens.add(Float.parseFloat(buffer.toString()));
		}
		tokens.add(Integer.parseInt(buffer.toString()));
		return true;
	}

	private boolean matchWithBoolean() {
		if (offset + 3 < json.length()) {
			if (json.substring(offset, offset + 4).equals("true")) {
				tokens.add(true);
				offset += 4;
				return true;
			}
		} else if (offset + 4 < json.length()) {
			if (json.substring(offset, offset + 5).equals("false")) {
				tokens.add(false);
				offset += 5;
				return true;
			}
		}
		return false;
	}

	private boolean matchWithNull() {
		if (offset + 3 < json.length()) {
			if (json.substring(offset, offset + 4).equals("null")) {
				tokens.add(null);
				offset += 4;
				return true;
			}
		}
		return false;
	}

	private boolean ignoreWhiteSpace() {
		if (json.charAt(offset) == ' ' || json.charAt(offset) == '\t' || json.charAt(offset) == '\n') {
			offset++;
			return true;
		}
		return false;
	}

	private boolean matchJsonSyntax() {
		final String jsonChars = "{}[],:";
		String charac = "" + json.charAt(offset);
		if (jsonChars.contains(charac)) {
			tokens.add(charac);
			offset++;
			return true;
		}
		return false;
	}

	public List<Object> tokenise() {
		while (offset < json.length()) {
			if (matchWithString())
				;
			else if (matchWithNumber())
				;
			else if (matchWithBoolean())
				;
			else if (matchWithNull())
				;
			else if (ignoreWhiteSpace())
				;
			else if (!matchJsonSyntax()) {
				System.err.println("Unexpected character: " + json.charAt(offset));
				System.exit(1);
			}
		}
		return tokens;
	}

}
