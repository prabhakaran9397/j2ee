package com.prabs.json.reader;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class JsonParser {

	List<Object> tokens;
	int offset;

	public Map<String, Object> parse(String json) {
		tokens = (new JsonLexer(json)).tokenise();
		if (!tokens.get(0).equals("{")) {
			System.err.println("It is not a Json");
			System.exit(1);
		}
		offset = 1;
		return parseJson();
	}

	private Object parseObject() {
		if (tokens.get(offset) == null) {
			return tokens.get(offset++);
		} else if (tokens.get(offset).equals("{")) {
			offset++;
			return parseJson();
		} else if (tokens.get(offset).equals("[")) {
			offset++;
			return parseArray();
		} else {
			return tokens.get(offset++);
		}
	}

	private Map<String, Object> parseJson() {
		Map<String, Object> object = new LinkedHashMap<>();
		if (tokens.get(offset).equals("}")) {
			offset++;
			return object;
		}
		while (true) {
			if (!(tokens.get(offset) instanceof String)) {
				System.err.println("Key should be a string but got " + tokens.get(offset));
				System.exit(1);
			}
			String key = (String) tokens.get(offset);
			offset++;

			if (!(tokens.get(offset).equals(":"))) {
				System.err.println("Colon missing but got " + tokens.get(offset));
				System.exit(1);
			}
			offset++;

			Object value = parseObject();
			object.put(key, value);

			if (tokens.get(offset).equals("}")) {
				offset++;
				return object;
			}

			if (!(tokens.get(offset).equals(","))) {
				System.err.println("Comma missing but got " + tokens.get(offset));
				System.exit(1);
			}
			offset++;
		}
	}

	private List<Object> parseArray() {
		List<Object> object = new LinkedList<>();
		if (tokens.get(offset).equals("]")) {
			offset++;
			return object;
		}
		while (true) {
			object.add(parseObject());

			if (tokens.get(offset).equals("]")) {
				offset++;
				return object;
			}

			if (!(tokens.get(offset).equals(","))) {
				System.err.println("Comma missing but got " + tokens.get(offset));
				System.exit(1);
			}
			offset++;
		}
	}
}
