package com.libraryapp;

public final class AdminMember extends AbstractMember {

    private final String role;

    public AdminMember(String id, String name) {
        super(id, name);
        this.role = "ADMIN";
    }

    @Override
    public String getMemberType() {
        return "Admin";
    }

    public String getRole() {
        return role;
    }
}
