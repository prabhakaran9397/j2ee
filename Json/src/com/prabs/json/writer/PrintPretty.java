package com.prabs.json.writer;

import java.util.List;
import java.util.Map;

public class PrintPretty {

	public void printJson(Map<String, Object> json) {
		printJson(json, 1);
	}

	private void printObject(Object value, int depth) {
		if (value instanceof String) {
			printString((String) value);
		} else if (value instanceof Number) {
			printNumber((Number) value);
		} else if (value instanceof Boolean) {
			printBoolean((Boolean) value);
		} else if (value instanceof List) {
			printArray((List<Object>) value);
		} else if (value instanceof Map) {
			printJson((Map<String, Object>) value, depth + 1);
		} else if (value == null) {
			printNull();
		}
	}

	private void printJson(Map<String, Object> json, int depth) {
		System.out.print("{");
		String[] keys = new String[json.keySet().size()];
		keys = json.keySet().toArray(keys);
		for (int i = 0; i < keys.length; ++i) {
			printCommaWith(i);
			System.out.println();
			printTab(depth);
			printString(keys[i]);
			System.out.print(": ");
			printObject(json.get(keys[i]), depth);
		}
		System.out.println();
		printTab(depth - 1);
		System.out.print("}");
	}

	private void printArray(List<Object> array) {
		System.out.print("[");
		String[] keys = new String[array.size()];
		keys = array.toArray(keys);
		for (int i = 0; i < keys.length; ++i) {
			printCommaWith(i);
			printObject(keys[i], 1);
		}
		System.out.print("]");
	}

	private void printString(String string) {
		System.out.print('"' + string + '"');
	}

	private void printNumber(Number number) {
		System.out.print(number);
	}

	private void printBoolean(Boolean bool) {
		System.out.print(bool);
	}

	private void printNull() {
		System.out.print("null");
	}

	private void printCommaWith(int count) {
		if (count > 0) {
			System.out.print(", ");
		}
	}

	private void printTab(int depth) {
		for (int i = 0; i < depth; ++i) {
			System.out.print("   ");
		}
	}

}
