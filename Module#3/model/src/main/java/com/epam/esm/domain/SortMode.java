package com.epam.esm.domain;


import org.springframework.lang.NonNull;

import java.util.Arrays;

/**
 * The enum Sort mode.
 */
public enum SortMode {
    ID_ASC,
    ID_DESC,
    /**
     * Date asc sort mode.
     */
    DATE_ASC,
    /**
     * Date desc sort mode.
     */
    DATE_DESC,
    /**
     * Name asc sort mode.
     */
    NAME_ASC,
    /**
     * Name desc sort mode.
     */
    NAME_DESC;

    private static final String DELIMITER = "_";

    /**
     * Of sort mode.
     *
     * @param name the name
     * @return the sort mode
     */
    public static SortMode of(String name) {
        return Arrays.stream(SortMode.values())
                .filter(v -> v.name().equalsIgnoreCase(name))
                .findFirst()
                .orElse(ID_ASC);
    }

    public static Entry split(@NonNull SortMode sortMode){
        String[] result = sortMode.name().split(DELIMITER);
        return new Entry(result[1],result[0]);
    }

    public static class Entry{
        private final String destination;
        private final String field;

        Entry(String destination, String field) {
            this.destination = destination.toLowerCase();
            this.field = field.toLowerCase();
        }

        public String getDestination() {
            return destination;
        }

        public String getField() {
            return field;
        }
    }
}
