# mySeals Spring Boot Backend Setup Guide

This guide provides instructions for setting up and running the mySeals Spring Boot backend application. It covers database configuration with Supabase, authentication with Auth0, and deployment on Render.

## 1. Project Structure

The project follows a standard Spring Boot structure:

```
myseals-backend/
├── src/
│   ├── main/
│   │   ├── java/com/myseals/
│   │   │   ├── MySealsApplication.java
│   │   │   ├── config/             # Spring Security and other configurations
│   │   │   │   └── SecurityConfig.java
│   │   │   │   └── CustomJwtAuthenticationConverter.java
│   │   │   ├── controller/         # REST API controllers
│   │   │   │   ├── AuditLogController.java
│   │   │   │   ├── OfficeController.java
│   │   │   │   ├── RoleController.java
│   │   │   │   ├── SealAssignmentController.java
│   │   │   │   ├── SealBatchController.java
│   │   │   │   ├── SealController.java
│   │   │   │   ├── SealUsageController.java
│   │   │   │   └── UserController.java
│   │   │   ├── dto/                # Data Transfer Objects (Request/Response)
│   │   │   │   ├── AuditLogResponseDTO.java
│   │   │   │   ├── OfficeRequestDTO.java
│   │   │   │   ├── OfficeResponseDTO.java
│   │   │   │   ├── RoleResponseDTO.java
│   │   │   │   ├── SealAssignmentRequestDTO.java
│   │   │   │   ├── SealAssignmentResponseDTO.java
│   │   │   │   ├── SealBatchRequestDTO.java
│   │   │   │   ├── SealBatchResponseDTO.java
│   │   │   │   ├── SealRequestDTO.java
│   │   │   │   ├── SealResponseDTO.java
│   │   │   │   ├── SealUsageRequestDTO.java
│   │   │   │   ├── SealUsageResponseDTO.java
│   │   │   │   ├── StockMovementRequestDTO.java
│   │   │   │   ├── StockMovementResponseDTO.java
│   │   │   │   ├── UserRequestDTO.java
│   │   │   │   └── UserResponseDTO.java
│   │   │   ├── model/              # JPA Entities and Enums
│   │   │   │   ├── AuditActionType.java
│   │   │   │   ├── AuditLog.java
│   │   │   │   ├── MovementType.java
│   │   │   │   ├── Office.java
│   │   │   │   ├── Role.java
│   │   │   │   ├── Seal.java
│   │   │   │   ├── SealAssignment.java
│   │   │   │   ├── SealBatch.java
│   │   │   │   ├── SealStatus.java
│   │   │   │   ├── SealUsage.java
│   │   │   │   ├── StockMovement.java
│   │   │   │   └── User.java
│   │   │   ├── repository/         # Spring Data JPA repositories
│   │   │   │   ├── AuditLogRepository.java
│   │   │   │   ├── OfficeRepository.java
│   │   │   │   ├── RoleRepository.java
│   │   │   │   ├── SealAssignmentRepository.java
│   │   │   │   ├── SealBatchRepository.java
│   │   │   │   ├── SealRepository.java
│   │   │   │   ├── SealUsageRepository.java
│   │   │   │   ├── StockMovementRepository.java
│   │   │   │   └── UserRepository.java
│   │   │   └── service/            # Business logic services
│   │   │       ├── AuditLogService.java
│   │   │       ├── OfficeService.java
│   │   │       ├── RoleService.java
│   │   │       ├── SealAssignmentService.java
│   │   │       ├── SealBatchService.java
│   │   │       ├── SealService.java
│   │   │       ├── SealUsageService.java
│   │   │       ├── StockMovementService.java
│   │   │       └── UserService.java
│   │   └── resources/
│   │       └── application.properties
├── pom.xml
└── README.md
```

## 2. Prerequisites

Before you begin, ensure you have the following installed:

*   **Java Development Kit (JDK) 17 or higher**
*   **Maven 3.6.x or higher**
*   **Git**
*   **A Supabase Project** (with PostgreSQL database set up as per previous instructions)
*   **An Auth0 Account** (with applications and API configured as per previous instructions)

## 3. Database Setup (Supabase)

1.  **Create Supabase Project:** If you haven't already, create a new project on Supabase.
2.  **Run DDL Script:** Execute the `myseals_complete_schema.sql` script (provided previously) in your Supabase SQL Editor to set up all necessary tables, enums, and initial data.
3.  **Obtain Connection String:** From your Supabase project dashboard, navigate to `Project Settings` -> `Database`. Copy the `Connection string` for your database. It will look something like:
    `postgresql://postgres:[YOUR-PASSWORD]@db.xvabimylohgdjyhzmvzw.supabase.co:5432/postgres`

## 4. Auth0 Configuration

1.  **Auth0 Tenant:** Ensure you have an Auth0 tenant set up.
2.  **Auth0 API:** Create an Auth0 API for your backend. Note down its `Audience` (Identifier) and `Domain`.
3.  **Auth0 Applications:** Create Auth0 applications for your web and mobile clients (Single Page App and Native App respectively). These will be used by the frontend to obtain tokens.
4.  **Custom Claims (Optional but Recommended):** If you plan to use role-based authorization directly from the JWT, configure Auth0 Rules or Actions to add user roles to the JWT as custom claims. For example, a custom claim `https://myseals.com/roles` containing a list of roles.

    *   **Example Auth0 Rule (for adding roles to JWT):**

        ```javascript
        function addRolesToAccessToken(user, context, callback) {
          const namespace = "https://myseals.com/";
          context.accessToken[namespace + "roles"] = user.app_metadata.roles || [];
          callback(null, user, context);
        }
        ```

        (You would need to manage `app_metadata.roles` for your users in Auth0.)

## 5. Spring Boot Application Configuration

1.  **Clone the Repository:** Clone your Spring Boot project to your local machine.

2.  **Update `pom.xml`:** Ensure your `pom.xml` includes the necessary dependencies for Spring Web, Spring Data JPA, Spring Security, OAuth2 Resource Server, PostgreSQL driver, Lombok, Hibernate Types for JSONB, and Jackson Databind. (The provided `pom.xml` already includes these).

3.  **Configure `application.properties`:**

    Open `src/main/resources/application.properties` and update the following:

    ```properties
    # Supabase PostgreSQL Database Connection
    spring.datasource.url=postgresql://postgres:[YOUR-PASSWORD]@db.xvabimylohgdjyhzmvzw.supabase.co:5432/postgres
    spring.datasource.username=postgres
    spring.datasource.password=[YOUR-PASSWORD] # Replace with your actual Supabase password
    spring.datasource.driver-class-name=org.postgresql.Driver

    # JPA/Hibernate Settings
    spring.jpa.hibernate.ddl-auto=update # Use 'update' for development, 'none' for production
    spring.jpa.show-sql=true
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
    spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true

    # Auth0 / Spring Security Configuration
    spring.security.oauth2.resourceserver.jwt.issuer-uri=https://dev-your-domain.us.auth0.com/ # Replace with your Auth0 Domain
    spring.security.oauth2.resourceserver.jwt.jwk-set-uri=https://dev-your-domain.us.auth0.com/.well-known/jwks.json # Replace with your Auth0 Domain

    # Server Port
    server.port=8080
    ```

    **Important:** Replace `[YOUR-PASSWORD]` with your actual Supabase database password and `https://dev-your-domain.us.auth0.com/` with your Auth0 tenant domain.

4.  **Review `SecurityConfig.java` and `CustomJwtAuthenticationConverter.java`:**

    *   `SecurityConfig.java` sets up basic JWT authentication for all endpoints except those explicitly marked as public (`/api/v1/public/**`).
    *   `CustomJwtAuthenticationConverter.java` is crucial for extracting custom roles from your Auth0 JWT. **You MUST adjust the `jwt.getClaimAsStringList("https://myseals.com/roles")` line to match the actual custom claim name you configured in Auth0 for roles.** If you don't add custom roles, this converter will only provide default OAuth2 scopes as authorities.

## 6. Running the Application

### Local Development

1.  **Build the project:**
    ```bash
    mvn clean install
    ```
2.  **Run the application:**
    ```bash
    mvn spring-boot:run
    ```
    The application will start on `http://localhost:8080`.

### Deployment to Render

1.  **Connect to Git:** Ensure your Spring Boot project is pushed to a Git repository (e.g., GitHub, GitLab, Bitbucket).
2.  **Create New Web Service on Render:**
    *   Go to your Render Dashboard.
    *   Click `New` -> `Web Service`.
    *   Connect your Git repository.
    *   **Build Command:** `mvn clean install -DskipTests`
    *   **Start Command:** `java -jar target/myseals-backend-0.0.1-SNAPSHOT.jar` (Adjust `myseals-backend-0.0.1-SNAPSHOT.jar` to match your actual JAR file name if it changes).
    *   **Environment Variables:** Add all the `spring.datasource.*` and `spring.security.oauth2.resourceserver.jwt.*` properties from your `application.properties` as environment variables in Render. **Crucially, ensure your Supabase password and Auth0 domain are correctly set here.**
    *   Choose a free instance type for testing/demo.
3.  **Deploy:** Render will automatically build and deploy your application. You can monitor the logs in the Render dashboard.

## 7. API Endpoints Overview

Here's a summary of the main API endpoints. All endpoints are secured with JWT authentication unless explicitly noted.

### Roles
*   `GET /api/v1/roles`: Get all roles.
*   `GET /api/v1/roles/{id}`: Get a role by ID.

### Offices
*   `GET /api/v1/offices`: Get all offices.
*   `GET /api/v1/offices/{id}`: Get an office by ID.
*   `POST /api/v1/offices`: Create a new office. (Requires `OfficeRequestDTO`)
*   `PUT /api/v1/offices/{id}`: Update an existing office. (Requires `OfficeRequestDTO`)
*   `DELETE /api/v1/offices/{id}`: Delete an office.

### Users
*   `GET /api/v1/users`: Get all users.
*   `GET /api/v1/users/{id}`: Get a user by ID.
*   `POST /api/v1/users`: Create a new user. (Requires `UserRequestDTO`)
*   `PUT /api/v1/users/{id}`: Update an existing user. (Requires `UserRequestDTO`)
*   `DELETE /api/v1/users/{id}`: Delete a user.

### Seal Batches
*   `GET /api/v1/seal-batches`: Get all seal batches.
*   `GET /api/v1/seal-batches/{id}`: Get a seal batch by ID.
*   `POST /api/v1/seal-batches`: Create a new seal batch. (Requires `SealBatchRequestDTO`)
*   `PUT /api/v1/seal-batches/{id}`: Update an existing seal batch. (Requires `SealBatchRequestDTO`)
*   `DELETE /api/v1/seal-batches/{id}`: Delete a seal batch.

### Seals
*   `GET /api/v1/seals`: Get all seals.
*   `GET /api/v1/seals/{id}`: Get a seal by ID.
*   `POST /api/v1/seals`: Create a new seal. (Requires `SealRequestDTO`)
*   `PUT /api/v1/seals/{id}`: Update an existing seal. (Requires `SealRequestDTO`)
*   `DELETE /api/v1/seals/{id}`: Delete a seal.

### Seal Assignments
*   `GET /api/v1/seal-assignments`: Get all seal assignments.
*   `GET /api/v1/seal-assignments/{id}`: Get a seal assignment by ID.
*   `POST /api/v1/seal-assignments`: Create a new seal assignment. (Requires `SealAssignmentRequestDTO`)
*   `PUT /api/v1/seal-assignments/{id}`: Update an existing seal assignment. (Requires `SealAssignmentRequestDTO`)
*   `DELETE /api/v1/seal-assignments/{id}`: Delete a seal assignment.

### Seal Usage
*   `GET /api/v1/seal-usage`: Get all seal usage records.
*   `GET /api/v1/seal-usage/{id}`: Get a seal usage record by ID.
*   `POST /api/v1/seal-usage`: Create a new seal usage record. (Requires `SealUsageRequestDTO`)
*   `DELETE /api/v1/seal-usage/{id}`: Delete a seal usage record.

### Stock Movements
*   `GET /api/v1/stock-movements`: Get all stock movements.
*   `GET /api/v1/stock-movements/{id}`: Get a stock movement by ID.
*   `POST /api/v1/stock-movements`: Create a new stock movement. (Requires `StockMovementRequestDTO`)
*   `DELETE /api/v1/stock-movements/{id}`: Delete a stock movement.

### Audit Logs
*   `GET /api/v1/audit-logs`: Get all audit logs.
*   `GET /api/v1/audit-logs/{id}`: Get an audit log by ID.

---

**Note:** This guide assumes you have a basic understanding of Spring Boot, Maven, PostgreSQL, and Auth0. Refer to their official documentation for more in-depth information.
