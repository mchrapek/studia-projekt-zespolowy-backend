package com.journeyplanner.common.config.mail;

public enum Template {

    NEW_USER("new_user.vm"),
    BLOCK_USER("block_user.vm"),
    UNBLOCK_USER("unblock_user.vm"),
    RESET_PASSWORD("reset_password.vm"),
    RESET_PASSWORD_ADMIN("reset_password_admin.vm"),

    NEW_RESERVATION_CREATED("new_reservation_created.vm"),
    RESERVATION_CANCELED("reservation_canceled.vm"),
    JOURNEY_CANCELED("journey_canceled.vm"),

    ACCOUNT_CHARGED("account_charged.vm"),
    PAYMENT_ERROR("payment_error.vm"),
    PAYMENT_LOAD("payment_load.vm"),
    PAYMENT_RETURN("payment_return.vm");

    private final String path;

    Template(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }
}
