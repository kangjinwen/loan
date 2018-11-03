package com.company.common.utils.upload;

public enum packTemplate {

    gzDefault("广尊标准");

    private String name;

    private packTemplate(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
