# ToDo List Application

This is a simple ToDo list application built with Spring Framework. The application allows users to create, read, update, and delete tasks.

## Features

- Add new tasks
- View all tasks
- Edit tasks
- Delete tasks


## Technologies Used

- Java
- Spring Boot
- Spring MVC
- Spring Security
- Spring Data JPA
- H2 Database (for development and testing)
- Thymeleaf (for server-side rendering of the views)

## Getting Started

### Prerequisites

To run this project, you need to have the following installed:

- JDK 21 or higher
- Maven 4.0.0 or higher

### Running the Application

1. Clone the repository:

    ```sh
    git clone https://github.com/yourusername/todo-list-spring.git
    cd todo-list-spring
    ```

2. Build the project using Maven:

    ```sh
    mvn clean install
    ```

3. Run the application:

    ```sh
    mvn spring-boot:run
    ```

4. The application will be accessible at `http://localhost:5555/login`.

### Using the Application

1. **Add a New Task:**
    - Navigate to `http://localhost:5555//main/add`
    - Fill in the task details and submit the form.

2. **View All Tasks:**
    - Navigate to `http://localhost:5555/main`
    - This page displays all the tasks with options to edit or delete each task.

3. **Edit a Task:**
    - Navigate to `http://localhost:5555//notes/{id}`
    - Modify the task details and submit the form.

4. **Delete a Task:**
    - On the tasks listing page, click the delete button next to the task you wish to remove.
    - Navigate to `http://localhost:5555//notes/{id}/delete`

## Project Structure

```plaintext
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── example/
│   │           └── todo/
│   │               ├── controller/
│   │               │   ├── MainController.java
|   |               |   └── TodoController.java
│   │               ├── model/
│   │               │   ├── ForgotPassword.java
|   |               |   ├── Notes.java
|   |               |   └── People.java
│   │               ├── repository/
│   │               │   ├── noteRepository.java
|   |               |   └── peopleRepository.java
│   │               ├── service/
│   │               │   ├── CodeGeneratorService.java
│   │               │   ├── EmailSenderService.java
│   │               │   ├── noteServices.java
│   │               │   └── peopleServices.java
│   │               └── TodoListApplication.java
│   └── resources/
│       ├── templates/
│       │   ├── changepassword.html
│       │   ├── email.html
│       │   ├── email-content.html
│       │   ├── index.html
│       │   ├── login.html
│       │   ├── notes.html
│       │   └── registration.html
│       ├── application.properties
│       └── data.sql
└── test/
    └── java/
        └── com/
            └── example/
                └── todo/
                    └── TodoApplicationTests.java
