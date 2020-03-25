package com.journeyplanner.user.domain.user;

enum UserRole {

    USER("USER"),
    GUIDE("GUIDE"),
    ADMIN("ADMIN");

    private final String roleName;

    UserRole(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
