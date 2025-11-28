package com.libraryapp;

public abstract non-sealed class AbstractMember implements LibraryUser {

    private final String id;
    private final String name;

    protected AbstractMember(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public abstract String getMemberType();

    @Override
    public String toString() {
        return getMemberType() + " " + name + " (" + id + ")";
    }
}
