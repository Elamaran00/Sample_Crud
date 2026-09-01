# Legacy Student Management JSF

A legacy Java web application built to serve as a migration testbed.

## Technology Stack

- **Java Version:** 8 (1.8)
- **Framework:** JSF 2.2 (`javax.faces`)
- **Database:** H2 Database (In-Memory/File)
- **ORM:** Hibernate Core 5.x + JPA 2.2 (`javax.persistence`)
- **Build Tool:** Gradle
- **Server Environment:** Servlet 4.0 API (Targeted for Apache Tomcat 9)

> **Important:** This project explicitly avoids `jakarta.*` namespaces and modern frameworks like Spring Boot or Jakarta EE 9+. It is designed for legacy tooling testing.

## Prerequisites
- Java 8 JDK
- Gradle (Optional, project includes `gradlew`)
- Tomcat 9 (For deployment)

## Building the Project

Run the build using the provided Gradle wrapper (requires Java 8):

```bash
./gradlew clean build
```

This will run the basic JUnit tests and generate the WAR file at `build/libs/StudentManagementJSF-1.0-SNAPSHOT.war`.

## Running Locally

Deploy the generated WAR file to the `webapps` folder of an Apache Tomcat 9 installation.
Start Tomcat and access the application at:
`http://localhost:8080/StudentManagementJSF-1.0-SNAPSHOT/`

### Demo Credentials
- **Username:** admin
- **Password:** admin123
