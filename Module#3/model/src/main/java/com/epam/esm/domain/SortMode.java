package com.epam.esm.domain;


import org.apache.commons.text.WordUtils;
import org.springframework.lang.NonNull;

import java.util.Arrays;

public enum SortMode {
    ID_ASC,
    ID_DESC,
    LAST_UPDATE_DATE_ASC,
    LAST_UPDATE_DATE_DESC,
    CREATE_DATE_ASC,
    CREATE_DATE_DESC,
    NAME_ASC,
    NAME_DESC;

    private static final String DELIMITER = "_";

    public static SortMode of(String name) {
        return Arrays.stream(SortMode.values())
                .filter(v -> v.name().equalsIgnoreCase(name))
                .findFirst()
                .orElse(ID_ASC);
    }

    public static Entry split(@NonNull SortMode sortMode){
        String[] result = sortMode.name().split(DELIMITER);
        return new Entry(result[result.length - 1], Arrays.copyOf(result, result.length - 1));
    }

    public static class Entry{
        private final String destination;
        private final String field;

        Entry(String destination, String... field) {
            this.destination = destination.toLowerCase();
            this.field = toCamelCaseArray(field);
        }

        public String getDestination() {
            return destination;
        }

        public String getField() {
            return field;
        }

        private String toCamelCaseArray(String[] s) {
            s[0] = s[0].toLowerCase();
            if (s.length == 1) {
                return s[0];
            }
            for (int i = 1; i < s.length; i++) {
                s[i] = WordUtils.capitalizeFully(s[i]);
            }
            return String.join("", s);
        }
    }
}
