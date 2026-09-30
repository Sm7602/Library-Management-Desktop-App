# Installation Guide

## Requirements

- Java 17 or later
- MySQL
- Maven

## Database

Create the database by executing:

`database/schema.sql`

Then load the sample records:

`database/sample_data.sql`

Configure the MySQL credentials in:

`src/main/java/com/library/util/DatabaseConnection.java`

## Build

```bash
mvn clean test
mvn package
```

## Start

```bash
java -jar target/library-management-desktop-app-1.0.0.jar
```
