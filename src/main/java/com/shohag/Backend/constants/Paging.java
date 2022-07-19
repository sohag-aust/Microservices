package com.shohag.Backend.constants;

public enum Paging {

    DEFAULT_PAGE_NO("0"),
    DEFAULT_PAGE_SIZE("5");

    private String value;

    Paging(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
