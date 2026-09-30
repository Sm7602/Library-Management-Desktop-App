# Architecture

The application is organized into layers:

- `model` contains application data models.
- `dao` handles JDBC database operations.
- `service` contains business logic and validation.
- `gui` contains the Swing desktop interface.
- `util` contains database and validation utilities.
- `exception` contains the application exception type.

The application follows a layered desktop architecture:

GUI -> Service -> DAO -> JDBC -> MySQL
