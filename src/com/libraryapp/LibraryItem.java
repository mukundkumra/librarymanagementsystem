package com.libraryapp;

public sealed interface LibraryItem permits BookItem {
    String getId();
    String getTitle();
    boolean isAvailable();
    void setAvailable(boolean available);
}
