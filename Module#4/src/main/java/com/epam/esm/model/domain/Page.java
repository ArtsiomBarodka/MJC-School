package com.epam.esm.model.domain;

import lombok.Data;

@Data
public class Page {
    private static final Integer SIZE_DEFAULT = 20;
    private static final Integer PAGE_DEFAULT = 0;

    private Integer page;
    private Integer size;

    public Page(Integer page, Integer size) {
        setPageOrDefault(page);
        setSizeOrDefault(size);
    }

    public Page(Integer page) {
        setPageOrDefault(page);
        this.size = SIZE_DEFAULT;
    }

    public Page() {
        this.page = PAGE_DEFAULT;
        this.size = SIZE_DEFAULT;
    }

    public int getOffset() {
        return page * size;
    }

    private void setPageOrDefault(Integer page) {
        this.page = page == null ? PAGE_DEFAULT : page;
    }

    private void setSizeOrDefault(Integer size) {
        this.size = size == null ? SIZE_DEFAULT : size;
    }
}
