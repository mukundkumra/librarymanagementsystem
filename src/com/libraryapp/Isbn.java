package com.libraryapp;

import java.util.Objects;

public final class Isbn {

    private final String value;

    public Isbn(String value) {
        Objects.requireNonNull(value);
        String normalized = value.replace("-", "").trim();
        if (normalized.length() != 10 && normalized.length() != 13) {
            throw new IllegalArgumentException("ISBN must be 10 or 13 characters.");
        }
        this.value = normalized;
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Isbn other))
            return false;
        return value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value;
    }
}
