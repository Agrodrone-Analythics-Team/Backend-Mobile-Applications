# Agro Drone Analytics Platform

## Summary
Agro Drone Analytics Platform, illustrating development with Java, Spring Boot Framework, and Spring Data JPA on MySQL Database. It also illustrates open-api documentation configuration and integration with Swagger UI.

## Features
- RESTful API
- OpenAPI Documentation
- Swagger UI
- Spring Boot Framework
- Spring Data JPA
- Validation
- MySQL Database
- Domain-Driven Design

## Bounded Contexts
This version is divided into three bounded contexts: Detection, Profiles, flight, and Identity and Access Management (IAM).

### Profiles Context

The Profiles Context is responsible for managing the profiles of the users. It includes the following features:

- Create a new profile.
- Get a profile by id.
- Get all profiles.

This context includes also an anti-corruption layer to communicate with the Learning Context. The anti-corruption layer is responsible for managing the communication between the Profiles Context and the Learning Context. It offers the following capabilities to other bounded contexts:
- Create a new Profile, returning ID of the created Profile on success.
- Get a Profile by Email, returning the associated Profile ID on success.

### Identity and Access Management (IAM) Context

The IAM Context is responsible for managing platform users, including the sign in and sign up processes. It applies JSON Web Token based authorization and Password hashing. It also adds a request authorization middleware to Spring Boot Pipeline, in order to validate included token in request header on endpoints that require authorization. Its capabilities include:
- Create a new User (Sign Up).
- Authenticate a User (Sign In).
- Get a User by ID.
- Get All Users.
- Get All Roles.
- Use Spring Security features to implement an authorization pipeline based on request filtering.
- Generate and validate JSON Web Tokens.
- Apply Password hashing.

This context includes also an anti-corruption layer. The anti-corruption layer is responsible for managing the communication between the IAM Context and other bounded Contexts. Its capabilities include:

- Create a new User, returning ID of the created User on success. If roles are provided, it will also assign them to the user, otherwise the default role is assigned.
- Get a User by ID, returning the associated User ID on success.
- Get a User by Username, returning the associated User ID on success.

In this version, Open API documentation includes support for JSON Web Token based authorization.

## OpenAPI Documentation
The OpenAPI documentation is available at the following URL:
```http://localhost:8080/swagger-ui.html```

### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.2.5/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.2.5/maven-plugin/reference/html/#build-image)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/3.2.5/reference/htmlsingle/index.html#data.sql.jpa-and-spring-data)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/3.2.5/reference/htmlsingle/index.html#using.devtools)
* [Validation](https://docs.spring.io/spring-boot/docs/3.2.5/reference/htmlsingle/index.html#io.validation)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.2.5/reference/htmlsingle/index.html#web)

### Guides

The following guides illustrate how to use some features concretely:

* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Accessing data with MySQL](https://spring.io/guides/gs/accessing-data-mysql/)
* [Validation](https://spring.io/guides/gs/validating-form-input/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)