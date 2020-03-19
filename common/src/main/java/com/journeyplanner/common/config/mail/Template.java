package com.journeyplanner.common.config.mail;

public enum Template {

    NEW_USER("new_user.vm");

    private final String path;

    Template(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }
}
