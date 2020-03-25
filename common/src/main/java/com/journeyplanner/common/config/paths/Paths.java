package com.journeyplanner.common.config.paths;

public class Paths {

    public final static String[] GET_PERMIT_ALL_PATHS = {
            "/catalogue",
            "/catalogue/photos/**",
            "/catalogue/journeys",
            "/catalogue/journeys/{\\d+}/photos"
    };

    public final static String[] POST_PERMIT_ALL_PATHS = {
            "/users/register",
            "/users/reset",
            "/users/password"
    };

    public final static String[] PUT_PERMIT_ALL_PATHS = {

    };

    public final static String[] GET_USER_PATHS = {
            "/users/details",
            "/users/avatar",
            "/reservations",
            "/reservations/**",
            "/billing/payments/**",
            "/billing/accounts",
            "/billing/accounts/charge"
    };

    public final static String[] POST_USER_PATHS = {
            "/users/details",
            "/users/avatar",
            "/billing/payments/**",
            "/catalogue/journeys/{\\d+}/reservation"
    };

    public final static String[] GET_ADMIN_PATHS = {
            "/users",
            "/users/details/**",
            "/users/guides",
            "/catalogue/journeys/{\\d+}/photos"
    };

    public final static String[] POST_ADMIN_PATHS = {
            "/users/block",
            "/users/reset/request",
            "/register/guides",
            "/catalogue/journeys"
    };

    public final static String[] PUT_ADMIN_PATHS = {
            "/catalogue/journeys/{\\d+}",
            "/catalogue/journeys/{\\d+}/guides"
    };

    public final static String[] DELETE_ADMIN_PATHS = {
            "/users/block",
            "/catalogue/journeys/**"
    };

    public final static String[] GET_GUIDE_PATHS = {
            "/catalogue/journeys/{\\d+}/guides"
    };
}
