package cz.martindobias.vivocatch.beans;

import java.util.HashMap;
import java.util.Map;

public enum ParameterType {
    STRING() {
        @Override
        public Object parse(String value) {
            if(value == null) {
                return null;
            }
            return trim(value);
        }

        @Override
        public String convert(Object value) {
            return (String) value;
        }
    },
    INTEGER() {
        @Override
        public Object parse(String value) {
            if(value == null) {
                return null;
            }
            return Integer.parseInt(trim(value));
        }

        @Override
        public String convert(Object value) {
            return String.valueOf(value);
        }
    },
    BOOLEAN() {
        @Override
        public Object parse(String value) {
            if(value == null) {
                return null;
            }
            return BOOLEAN_MAP.get(trim(value));
        }

        @Override
        public String convert(Object value) {
            return String.valueOf(value);
        }
    };

    private static String trim(String value) {
        value = value.trim();
        if(value.startsWith("\'") && value.endsWith("\'")) {
            return value.substring(1, value.length() - 1);
        }
        return value;
    }

    private static final Map<String, Boolean> BOOLEAN_MAP;
    static {
        BOOLEAN_MAP = new HashMap<String, Boolean>(6);
        BOOLEAN_MAP.put("0", false);
        BOOLEAN_MAP.put("1", true);
        BOOLEAN_MAP.put("false", false);
        BOOLEAN_MAP.put("true", true);
        BOOLEAN_MAP.put("no", false);
        BOOLEAN_MAP.put("yes", true);
        BOOLEAN_MAP.put("disable", false);
        BOOLEAN_MAP.put("enable", true);
    }

    public abstract Object parse(String value);
    public abstract String convert(Object value);
}
