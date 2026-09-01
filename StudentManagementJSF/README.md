# Legacy Student Management JSF

A legacy Java web application built to serve as a migration testbed, now configured with **Open Liberty** server support.

## Technology Stack

- **Java Version:** 8 (1.8)
- **Framework:** JSF 2.2 (`javax.faces`)
- **Database:** H2 Database (In-Memory/File)
- **ORM:** Hibernate Core 5.x + JPA 2.2 (`javax.persistence`)
- **Build Tool:** Gradle
- **Server Environment:** Open Liberty (Servlet 4.0 API)

> **Important:** This project explicitly avoids `jakarta.*` namespaces and modern frameworks like Spring Boot or Jakarta EE 9+. It is designed for legacy tooling testing.

## Prerequisites
- Java 8 JDK
- Gradle (Optional, project includes `gradlew`)
- Open Liberty is automatically provisioned via the `liberty-gradle-plugin`.

## Building the Project

Run the build using the provided Gradle wrapper (requires Java 8):

```bash
./gradlew clean build
```

This will run the basic JUnit tests and generate the WAR file at `build/libs/StudentManagementJSF.war` (or `StudentManagementJSF-1.0-SNAPSHOT.war` depending on version config).

## Running with Open Liberty

This project is integrated with the `io.openliberty.tools.gradle.Liberty` plugin. The server configuration is stored at `src/main/liberty/config/server.xml`.

To start the server, run:
```bash
./gradlew libertyStart
```

To run it in the foreground:
```bash
./gradlew libertyRun
```

### Accessing the Application
Once the server is running, navigate to:
**http://localhost:9080/StudentManagementJSF/**

### Demo Credentials
- **Username:** admin
- **Password:** admin123

To stop the server running in the background:
```bash
./gradlew libertyStop
```

---

## Validation Summary

```text
Java Version       : 8
JSF Version        : 2.2
Build Tool         : Gradle
Database           : H2
ORM                : JPA/Hibernate
Application Server : Open Liberty
Namespace          : javax.*
WAR Build          : SUCCESS
Liberty Deployment : SUCCESS
CRUD Operations    : SUCCESS
Jakarta References : 0
```
