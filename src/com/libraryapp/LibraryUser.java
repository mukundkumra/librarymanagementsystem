package com.libraryapp;

public sealed interface LibraryUser permits AbstractMember {
    String getId();

    String getName();
}
