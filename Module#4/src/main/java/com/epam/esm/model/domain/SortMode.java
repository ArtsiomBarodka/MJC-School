package com.epam.esm.model.domain;

import org.apache.commons.text.WordUtils;
import org.springframework.lang.NonNull;

import java.util.Arrays;

/**
 * The enum Sort mode.
 */
public enum SortMode {
    /**
     * Id asc sort mode.
     */
    ID_ASC,
    /**
     * Id desc sort mode.
     */
    ID_DESC,
    /**
     * Last update date asc sort mode.
     */
    LAST_UPDATE_DATE_ASC,
    /**
     * Last update date desc sort mode.
     */
    LAST_UPDATE_DATE_DESC,
    /**
     * Create date asc sort mode.
     */
    CREATE_DATE_ASC,
    /**
     * Create date desc sort mode.
     */
    CREATE_DATE_DESC,
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

    /**
     * Split entry.
     *
     * @param sortMode the sort mode
     * @return the entry
     */
    public static Entry split(@NonNull SortMode sortMode){
        String[] result = sortMode.name().split(DELIMITER);
        return new Entry(result[result.length - 1], Arrays.copyOf(result, result.length - 1));
    }

    /**
     * The type Entry.
     */
    public static class Entry{
        private final String destination;
        private final String field;

        /**
         * Instantiates a new Entry.
         *
         * @param destination the destination
         * @param field       the field
         */
        Entry(String destination, String... field) {
            this.destination = destination.toLowerCase();
            this.field = toCamelCaseArray(field);
        }

        /**
         * Gets destination.
         *
         * @return the destination
         */
        public String getDestination() {
            return destination;
        }

        /**
         * Gets field.
         *
         * @return the field
         */
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
