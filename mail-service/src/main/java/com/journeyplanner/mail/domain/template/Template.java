package com.journeyplanner.mail.domain.template;

public enum Template {

    NEW("new.vm");

    private final String path;

    Template(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }
}
