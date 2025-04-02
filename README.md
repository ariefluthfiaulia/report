# Report Application

## Steps to Run the Application

### 1. Clone the Repository
Clone the repository from GitHub and navigate to the repository directory:
```sh
git clone https://github.com/ariefluthfiaulia/report.git
cd report
```

### 2. Prepare PostgreSQL
Run PostgreSQL using Docker:
```sh
docker-compose up -d
```

### 3. Setup Database
The database will be created automatically by Docker with the following credentials:
- **Database Name**: `reportdb`
- **Username**: `postgres`
- **Password**: `password`
- **Port**: `5432`

### 4. Build and Run Application
Build the application using Maven:
```sh
./mvnw clean install
```
Run the application:
```sh
./mvnw spring-boot:run
```

### 5. Access Application
Open your browser and go to: [http://localhost:8080](http://localhost:8080). You will be redirected to the login page. Use the following credentials:

#### Admin Role:
- **Username**: `admin`
- **Password**: `admin`

#### Employee Role:
- **Username**: `employee`
- **Password**: `password`
- **Username**: `Tom`
- **Password**: `password`

### 6. Application Features
- Log in as **admin** to view reports for all employees.
- Log in as **employee** to view personal reports.
- Filter reports by **date range**.
- **Pagination** for easier navigation.

---

## Steps to Use the Reporting Page

### 1. Log in to the Application
Enter your username and password:
```sh
# Login as Admin to view all reports:
Username: admin
Password: admin

# Or login as Employee to view personal reports:
Username: employee / Tom
Password: password
```

### 2. Access the Report Page
After logging in, you will be redirected to `/report`. By default, the report displays data from the last **1 month**.

### 3. Filter Data by Date
Use the filter form at the top:
- Set **Start Date**.
- Set **End Date**.
- Click **Submit** to apply the filter.

### 4. Reading the Report
The table displays three columns:
- **Employee Name**: Name of the employee.
- **Project Name**: Name of the project.
- **Total Hours**: Total working hours.

### 5. Page Navigation
The data is displayed **10 rows per page**. Use the navigation buttons below the table:
- **Previous**: Go to the previous page.
- **Page Numbers**: Jump to a specific page.
- **Next**: Go to the next page.

### 6. Logout
Click the **logout** button in the top-right corner to sign out. You will be redirected back to the login page.

---

## Application Design
### 1. Application Architecture
- The application follows a **3-layer architecture**:
  - **Controller Layer**: Handles HTTP requests and responses, and interacts with the service layer.
  - **Service Layer**: Contains business logic and interacts with the repository layer.
  - **Repository Layer**: Handles data access and interacts with the database using JPA.

### 2. Spring Boot Component Choices
- **JPA**: Chosen for its **entity mapping** features, making data conversion simpler and code more efficient.
- **Liquibase**: Used for **database migration** due to its flexible implementation options (SQL, JSON, YAML, XML).
- **Lombok**: Reduces Java boilerplate code and improves readability.

### 3. Security & Role-Based Access Control
The application uses **Spring Security** for authentication and authorization:
- **Authentication**: Managed with `InMemoryUserDetailsManager`, storing user details in memory.
- **Roles**:
  - **ADMIN**: Can view all reports.
  - **EMPLOYEE**: Can only view personal reports.

### 4. Challenges and Solutions
- **Choosing the Right Query Approach**:
  - Initially planned to use **full JPQL**, but its lack of `EPOCH` support led to a hybrid approach.
  - **Solution**: Use **Native Query in JPA** for full PostgreSQL feature support.

- **Optimizing Query Performance**:
  - **Analyzing Inefficient Subqueries**:
    - The original query contained inefficient subqueries, which were replaced with **JOINs** for better performance.
    - **Solution**: replaced with **JOINs** for better performance
  - **Database Partitioning**:
    - The `time_record` table is partitioned by date to improve query performance on large datasets. This allows for faster data retrieval by limiting the amount of data scanned.
  - **Database Indexing**:
    - Appropriate indexes have been added to frequently queried columns, such as `time_from`, and `employee.name`, to speed up data retrieval.
    - **Solution**: Identify and index columns based on query patterns and performance analysis.

