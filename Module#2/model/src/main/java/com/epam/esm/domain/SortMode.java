package com.epam.esm.domain;

import java.util.Arrays;

public enum SortMode {
    DATE_ASC,
    DATE_DESC,
    NAME_ASC,
    NAME_DESC;

    public static SortMode of(String name){
        return Arrays.stream(SortMode.values())
                .filter(v->v.name().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(()->new IllegalArgumentException("Sort mode is not exist: " + name));
    }
}
