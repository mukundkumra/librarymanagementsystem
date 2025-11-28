package com.libraryapp;

public final class StudentMember extends AbstractMember {

    private final String course;

    public StudentMember(String id, String name, String course) {
        super(id, name);
        this.course = course;
    }

    public String getCourse() {
        return course;
    }

    @Override
    public String getMemberType() {
        return "Student";
    }
}
