package com.libraryapp;

public final class StaffMember extends AbstractMember {

    private final String department;

    public StaffMember(String id, String name, String department) {
        super(id, name);
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public String getMemberType() {
        return "Staff";
    }
}
