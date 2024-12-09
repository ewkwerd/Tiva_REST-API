# Tiva REST API  
A backend service for Tiva, a clothing store, managing products, stock, and orders with real-time updates and calculations.

## Features
- **Real-Time Stock Updates**: Automatically adjusts product stock quantities when creating, updating, or deleting order details.
- **Layered Architecture**: Follows the Layered Architecture Pattern, ensuring a clean separation of concerns across service, repository, and controller layers.
- **Custom Exception Handling**: Handles errors gracefully with user-friendly messages via a global exception handler.
- **CRUD Operations**: Supports full Create, Read, Update, and Delete functionality for managing products, orders, and inventory.
  
## Dependencies
- **Spring Boot Starter Web**: For building REST APIs with Spring MVC and embedded Tomcat.
- **JPA/Hibernate**: For ORM with MySQL using Spring Data JPA and Hibernate.
- **Validation**: For input validation with annotations like `@NotNull`, `@Size`, etc.
- **MySQL Connector/J**: JDBC driver for MySQL database connection.

## API Testing View 
Test the API using tools like **Postman** or **Swagger UI**.

| API Testing View |
|---------------------|
| ![api-testing-view](https://github.com/user-attachments/assets/f942b19b-525e-435b-bc79-09f99f99d9f9) |

## Getting Started
To run this project locally, follow these steps:
1. Clone the repository:  
   `git clone https://github.com/ewkwerd/Tiva_REST-API.git`
2. Set up the MySQL database using the schema file located under `database/tiva_schema.sql`.
3. Configure your `application.properties` with the correct database credentials.
4. Run the application using your preferred IDE or via the command line:  
   `mvn spring-boot:run`
