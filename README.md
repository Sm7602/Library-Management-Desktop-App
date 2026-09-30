# Library Management Desktop Application

A Java Swing desktop application for managing books, members, borrowing/returning transactions, authentication, searching, and library reports.

## Technologies

- Java 17
- Swing
- JDBC
- MySQL
- Maven
- JUnit 5

## Features

- User authentication and authorization
- Book CRUD operations
- Member CRUD operations
- Search books by title, author, ISBN, or genre
- Member search
- Borrow and return books
- Due dates and overdue detection
- Dashboard statistics
- Popular books report
- Overdue books report
- Member activity report
- Validation and error handling
- Unit tests
- Executable JAR packaging

## Database Setup

1. Install MySQL.
2. Open `database/schema.sql`.
3. Execute the script.
4. Execute `database/sample_data.sql`.
5. Open `src/main/java/com/library/util/DatabaseConnection.java`.
6. Set the MySQL username and password.

Default sample login:

- Username: `admin`
- Password: `admin123`

## Run

```bash
mvn clean test
mvn package
java -jar target/library-management-desktop-app-1.0.0.jar
```

## Project Structure

```text
src/main/java/com/library/
├── model
├── dao
├── service
├── gui
├── util
└── exception

database/
tests
docs
resources
```
