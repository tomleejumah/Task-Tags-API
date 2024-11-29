# Task and Tag Management API

## Overview
This is a Spring Boot-based REST API for managing tasks and tags, providing comprehensive functionality for task tracking and organization.

## Features
- Create, update, and delete tasks
- Assign multiple tags to tasks
- Flexible task filtering
- Detailed tag management
- Global error handling
- Swagger API documentation

## Technical Stack
- **Language**: Java
- **Framework**: Spring Boot
- **Database**: MySQL/H2
- **ORM**: Spring Data JPA
- **Documentation**: Swagger

## Prerequisites
- Java 17+
- Maven
- MySQL or H2 Database

## Project Structure
```
com/
└── example/
    └── taskmanagement/
        ├── configuration/
        ├── controller/
        ├── ControllerAdvice/
        ├── dto/
        ├── exception/
        │   └── types/
        │   └── GlobalExceptionHandler.java
        ├── model/
        ├── repository/
        ├── response/
        └── service/
```

## Configuration

### Database Configuration
Choose between MySQL or H2:

#### MySQL
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/taskmanager
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

## Running the Application
1. Clone the repository
2. Navigate to project directory
3. Run with Maven:
```bash
mvn clean install
mvn spring-boot:run
```

## API Endpoints

### Task Endpoints
- `POST /tasks/create/`: Create a new task
- `GET /tasks`: Retrieve all tasks
- `GET /tasks?completed=boolean`: filter by cmplete status
- `PUT /tasks/{id}`: Update a task
- `DELETE /tasks/{id}`: Delete a task

### Tag Endpoints
- `POST /tags/create/`: Create a new tag
- `GET /tags`: Retrieve all tags with tasks assciated count
- `GET tags/{id}`: Get tag with ID
- `GET /tags/{id}/tasks`: Get tag and associated tasks
- `GET /tags?dateCreated="date"`:Filter tag with date created

## Swagger Documentation
Access Swagger UI at:
`http://localhost:8085/swagger-ui.html`

## Filtering Tasks
Support filtering by:
- Completion status
- Tags
- Creation date

Example Request:
```bash
GET /api/tasks?completed=false&tags=urgent,important
```

## Error Handling
~Used GlobalException Handling with the following classes
- Consistent error responses
- Validation error details
- Resource not found handling

## Postman Collection
[![Run in Postman](https://run.pstmn.io/button.svg)](https://www.postman.com/)

## Contributing
1. Fork the repository
2. Create your feature branch
3. Commit changes
4. Push to the branch
5. Create a Pull Request



## Contact
Tomlee
tommlyjumah@gmail.com
```

Would you like me to elaborate on any section of the README or provide additional details specific to your implementation?
