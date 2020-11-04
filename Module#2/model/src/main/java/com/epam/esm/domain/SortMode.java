package com.epam.esm.domain;

import java.util.Arrays;

/**
 * The enum Sort mode.
 */
public enum SortMode {
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
                .orElse(DATE_ASC);
    }
}
