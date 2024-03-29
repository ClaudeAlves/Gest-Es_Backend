# openapi-java-client

Gest-ES Api
- API version: 1.0.0
  - Build date: 2022-06-26T16:45:46.363535700+02:00[Europe/Berlin]

This api provides communication with a database for a TB project


*Automatically generated by the [OpenAPI Generator](https://openapi-generator.tech)*


## Requirements

Building the API client library requires:
1. Java 1.7+
2. Maven/Gradle

## Installation

To install the API client library to your local Maven repository, simply execute:

```shell
mvn clean install
```

To deploy it to a remote Maven repository instead, configure the settings of the repository and execute:

```shell
mvn clean deploy
```

Refer to the [OSSRH Guide](http://central.sonatype.org/pages/ossrh-guide.html) for more information.

### Maven users

Add this dependency to your project's POM:

```xml
<dependency>
  <groupId>org.openapitools</groupId>
  <artifactId>openapi-java-client</artifactId>
  <version>1.0.0</version>
  <scope>compile</scope>
</dependency>
```

### Gradle users

Add this dependency to your project's build file:

```groovy
compile "org.openapitools:openapi-java-client:1.0.0"
```

### Others

At first generate the JAR by executing:

```shell
mvn clean package
```

Then manually install the following JARs:

* `target/openapi-java-client-1.0.0.jar`
* `target/lib/*.jar`

## Getting Started

Please follow the [installation](#installation) instruction and execute the following Java code:

```java

// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AuthenticationApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8081");

    AuthenticationApi apiInstance = new AuthenticationApi(defaultClient);
    LoginRequestDTO loginRequestDTO = new LoginRequestDTO(); // LoginRequestDTO | 
    try {
      LoginSuccessDTO result = apiInstance.login(loginRequestDTO);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AuthenticationApi#login");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}

```

## Documentation for API Endpoints

All URIs are relative to *http://localhost:8081*

Class | Method | HTTP request | Description
------------ | ------------- | ------------- | -------------
*AuthenticationApi* | [**login**](docs/AuthenticationApi.md#login) | **POST** /auth/login | Login
*AuthenticationApi* | [**logout**](docs/AuthenticationApi.md#logout) | **POST** /auth/logout | Logout.
*AuthenticationApi* | [**register**](docs/AuthenticationApi.md#register) | **POST** /auth/register | Register a new user.
*CalendarApi* | [**getCalendar**](docs/CalendarApi.md#getCalendar) | **GET** /calendar/{username} | Get a specific user calendar.
*CalendarApi* | [**getCalendarUser**](docs/CalendarApi.md#getCalendarUser) | **GET** /calendar | Get a specific user calendar.
*CalendarApi* | [**getClassCalendar**](docs/CalendarApi.md#getClassCalendar) | **GET** /calendar/class/{className} | Get a class calendar.
*CreationApi* | [**createCourse**](docs/CreationApi.md#createCourse) | **POST** /creation/course | create course.
*CreationApi* | [**createModule**](docs/CreationApi.md#createModule) | **POST** /creation/module | create module.
*CreationApi* | [**createSubject**](docs/CreationApi.md#createSubject) | **POST** /creation/subject | create subject.
*MarkApi* | [**getStudentMarks**](docs/MarkApi.md#getStudentMarks) | **GET** /marks/{studentUsername} | Get marks.
*UserApi* | [**deleteUser**](docs/UserApi.md#deleteUser) | **DELETE** /users/{username} | Delete a specific user.
*UserApi* | [**deleteUsers**](docs/UserApi.md#deleteUsers) | **DELETE** /admin/users | Delete all users.
*UserApi* | [**getUser**](docs/UserApi.md#getUser) | **GET** /users/{username} | Get a specific user.
*UserApi* | [**getUsers**](docs/UserApi.md#getUsers) | **GET** /admin/users | Get all users.
*UserApi* | [**updateUser**](docs/UserApi.md#updateUser) | **PUT** /users/{username} | Update a specific user.


## Documentation for Models

 - [ApiMessageDTO](docs/ApiMessageDTO.md)
 - [CalendarDTO](docs/CalendarDTO.md)
 - [CourseDTO](docs/CourseDTO.md)
 - [LoginRequestDTO](docs/LoginRequestDTO.md)
 - [LoginSuccessDTO](docs/LoginSuccessDTO.md)
 - [ModuleDTO](docs/ModuleDTO.md)
 - [RegisterDTO](docs/RegisterDTO.md)
 - [RoleDTO](docs/RoleDTO.md)
 - [SubjectDTO](docs/SubjectDTO.md)
 - [UserDTO](docs/UserDTO.md)
 - [UserModificationDTO](docs/UserModificationDTO.md)
 - [UserSimpleDTO](docs/UserSimpleDTO.md)


## Documentation for Authorization

Authentication schemes defined for the API:
### JWTSecurity

- **Type**: HTTP basic authentication


## Recommendation

It's recommended to create an instance of `ApiClient` per thread in a multithreaded environment to avoid any potential issues.

## Author

claude-andre.inacioalves@heig-vd.ch

