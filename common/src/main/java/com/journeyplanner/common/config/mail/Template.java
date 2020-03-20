package com.journeyplanner.common.config.mail;

public enum Template {

    NEW_USER("new_user.vm"),
    BLOCK_USER("block_user.vm"),
    UNBLOCK_USER("unblock_user.vm"),
    RESET_PASSWORD("reset_password.vm");

    private final String path;

    Template(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }
}
