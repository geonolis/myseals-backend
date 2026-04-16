# Supabase Connection and Configuration Guide for mySeals Spring Boot Backend

This guide details how to configure your Spring Boot backend to connect to your Supabase PostgreSQL database. It covers obtaining your Supabase connection details and setting them up in your Spring Boot application.

## 1. Obtain Supabase Database Credentials

To connect your Spring Boot application to your Supabase PostgreSQL database, you need the connection string and credentials. Follow these steps to retrieve them:

1.  **Log in to your Supabase Dashboard:** Go to [https://app.supabase.com/](https://app.supabase.com/) and log in to your account.
2.  **Select your Project:** From your dashboard, click on the project you created for mySeals.
3.  **Navigate to Database Settings:** In the left-hand sidebar, click on `Project Settings` (the gear icon), then select `Database`.
4.  **Find Connection String:** Scroll down to the `Connection String` section. You will see a connection string that looks similar to this:

    ```
    postgresql://postgres:[YOUR-PASSWORD]@db.xvabimylohgdjyhzmvzw.supabase.co:5432/postgres
    ```

    *   **Host:** `db.xvabimylohgdjyhzmvzw.supabase.co` (This will be unique to your project)
    *   **Port:** `5432`
    *   **Database Name:** `postgres`
    *   **User:** `postgres`
    *   **Password:** This is the password you set when you created your Supabase project. **Ensure you have this password handy.**

## 2. Configure Spring Boot `application.properties`

Once you have your Supabase credentials, you need to configure your Spring Boot application to use them. Open the `src/main/resources/application.properties` file in your Spring Boot project and add or update the following properties:

```properties
# Supabase PostgreSQL Database Connection
spring.datasource.url=jdbc:postgresql://db.xvabimylohgdjyhzmvzw.supabase.co:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=[YOUR-PASSWORD] # Replace with your actual Supabase password
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA/Hibernate Settings
# Use 'update' for development to automatically create/update schema based on entities.
# For production, consider 'none' and manage schema migrations manually.
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true

# Other configurations (e.g., Auth0, Server Port) would also be here
# spring.security.oauth2.resourceserver.jwt.issuer-uri=...
# server.port=8080
```

**Important:**

*   **Replace `[YOUR-PASSWORD]`** with the actual password for your Supabase `postgres` user.
*   The `spring.datasource.url` should exactly match the host, port, and database name from your Supabase connection string, prefixed with `jdbc:`. The example provided uses `db.xvabimylohgdjyhzmvzw.supabase.co`, but **your host will be different.**
*   The `spring.jpa.hibernate.ddl-auto=update` setting is convenient for development as it automatically updates your database schema based on your JPA entities. For production environments, it's generally recommended to set this to `none` and use a dedicated schema migration tool (like Flyway or Liquibase) for better control over database changes.

## 3. Verify Connection

After configuring `application.properties`:

1.  **Run your Spring Boot application locally.**
2.  **Check the application logs.** You should see messages indicating that Spring Boot is connecting to the PostgreSQL database. If there are connection issues, the logs will provide error details.
3.  **Access an API endpoint** that interacts with the database (e.g., `GET /api/v1/roles`). If the connection is successful and the schema is correctly applied, you should receive a valid response.

By following these steps, your Spring Boot backend will be successfully connected to your Supabase PostgreSQL database, ready to interact with the mySeals schema.
