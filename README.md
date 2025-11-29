# Library Management System

## Overview

A complete Java console application that simulates a real-world library system. This project demonstrates core and advanced Java programming concepts while providing a functional workflow for managing books, users, and loan operations.
**Purpose**: Enable borrowing, returning, searching, and administrative management of library resources while showcasing Java 21+ features.
**Current State**: Fully functional library system with user roles, loan records, custom exceptions, and sealed class hierarchy.

## Project Architecture

### Structure

```
src/com/libraryapp
├── LibraryApp.java                 # Main application (interactive menu)
├── Library.java                    # Core system logic (books, users, loans)
├── Data.java                       # Preloaded sample data
├── LibraryItem.java                # Abstract base class for library items
├── BookItem.java                   # Book implementation (ISBN, genre)
├── Isbn.java                       # Custom immutable ISBN type
├── Genre.java                      # Enum for book genres
├── LibraryUser.java                # User behavior interface
├── AbstractMember.java             # Sealed base class for all members
├── StudentMember.java              # Student-specific borrowing limits
├── StaffMember.java                # Staff-specific borrowing privileges
├── AdminMember.java                # Admin privileges (add/remove books)
├── LoanRecord.java                 # Loan tracking (due dates)
├── Borrowable.java                 # Interface for borrowable items
├── LibraryException.java           # Base custom exception
├── BookNotAvailableException.java  # Thrown when trying to borrow unavailable books
└── InvalidCommandException.java    # Handles invalid menu inputs
```

## Key Features

### Library Operations

- Borrow books
- Return books
- Track due dates
- Search books by title
- View all available or borrowed items

### User Roles (Polymorphism)

- Students: Borrow Books
- Staff: Borrow Books
- Admin: Add/Remove books

### Additional Functionalities

- Loan history tracking
- Preloaded books and members
- Clear command menu with validation
- Full error handling through custom exceptions

## Java Concepts Demonstrated

- Classes \& Encapsulation
- `this()` vs `this.` for constructor chaining and attributes
- Method overloading (search operations)
- Varargs (admin bulk operations)
- LVTI (`var`)
- Interfaces (`LibraryUser`, `Borrowable`)
- Inheritance (members extending `AbstractMember`)
- Overriding \& Polymorphism
- `super()` constructor usage
- Custom exceptions (checked \& unchecked)
- Enums (`Genre`)
- Arrays (genre values handling)
- StringBuilder for output
- Date/Time API (`LocalDate` for due dates)
- Defensive copying
- Records (`Isbn` immutable type)
- Lambdas \& Predicate filtering (search)
- Method references
- Switch expressions for menu commands
- Sealed classes for member hierarchy
- Effectively final variables

## How to Use

The main menu provides access to:

- View All Books
- Search Books (by title)
- Borrow Book
- Return Book
- View Active Member Loans
- Admin Options (Add/Remove Book)
- Exit Program

## Sample Data

Preloaded dataset includes:

- Multiple books across genres
- Student, Staff, and Admin members

Allows immediate testing with no setup.

## Technical Details

- **Language**: Java 21 LTS
- **Dependencies**: None (Java Core API only)
- **Packages Used**: `java.util`, `java.time`, `java.lang`, `java.util.function`

### Design Patterns

- Sealed class hierarchy
- Polymorphic role-based behavior
- Repository-style in-memory data management
