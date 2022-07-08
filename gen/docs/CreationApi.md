# CreationApi

All URIs are relative to *http://localhost:8081*

Method | HTTP request | Description
------------- | ------------- | -------------
[**createCourse**](CreationApi.md#createCourse) | **POST** /creation/course | create course.
[**createModule**](CreationApi.md#createModule) | **POST** /creation/module | create module.
[**createSubject**](CreationApi.md#createSubject) | **POST** /creation/subject | create subject.


<a name="createCourse"></a>
# **createCourse**
> ApiMessageDTO createCourse(courseDTO)

create course.

This endpoint is used to create a course.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.CreationApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8081");
    
    // Configure HTTP bearer authorization: JWTSecurity
    HttpBearerAuth JWTSecurity = (HttpBearerAuth) defaultClient.getAuthentication("JWTSecurity");
    JWTSecurity.setBearerToken("BEARER TOKEN");

    CreationApi apiInstance = new CreationApi(defaultClient);
    CourseDTO courseDTO = new CourseDTO(); // CourseDTO | 
    try {
      ApiMessageDTO result = apiInstance.createCourse(courseDTO);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CreationApi#createCourse");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **courseDTO** | [**CourseDTO**](CourseDTO.md)|  | [optional]

### Return type

[**ApiMessageDTO**](ApiMessageDTO.md)

### Authorization

[JWTSecurity](../README.md#JWTSecurity)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**201** | Creation successful. |  -  |

<a name="createModule"></a>
# **createModule**
> ApiMessageDTO createModule(moduleDTO)

create module.

This endpoint is used to create a module.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.CreationApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8081");
    
    // Configure HTTP bearer authorization: JWTSecurity
    HttpBearerAuth JWTSecurity = (HttpBearerAuth) defaultClient.getAuthentication("JWTSecurity");
    JWTSecurity.setBearerToken("BEARER TOKEN");

    CreationApi apiInstance = new CreationApi(defaultClient);
    ModuleDTO moduleDTO = new ModuleDTO(); // ModuleDTO | 
    try {
      ApiMessageDTO result = apiInstance.createModule(moduleDTO);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CreationApi#createModule");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **moduleDTO** | [**ModuleDTO**](ModuleDTO.md)|  | [optional]

### Return type

[**ApiMessageDTO**](ApiMessageDTO.md)

### Authorization

[JWTSecurity](../README.md#JWTSecurity)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**201** | Creation successful. |  -  |

<a name="createSubject"></a>
# **createSubject**
> ApiMessageDTO createSubject(subjectDTO)

create subject.

This endpoint is used to create a subject.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.CreationApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8081");
    
    // Configure HTTP bearer authorization: JWTSecurity
    HttpBearerAuth JWTSecurity = (HttpBearerAuth) defaultClient.getAuthentication("JWTSecurity");
    JWTSecurity.setBearerToken("BEARER TOKEN");

    CreationApi apiInstance = new CreationApi(defaultClient);
    SubjectDTO subjectDTO = new SubjectDTO(); // SubjectDTO | 
    try {
      ApiMessageDTO result = apiInstance.createSubject(subjectDTO);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CreationApi#createSubject");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **subjectDTO** | [**SubjectDTO**](SubjectDTO.md)|  | [optional]

### Return type

[**ApiMessageDTO**](ApiMessageDTO.md)

### Authorization

[JWTSecurity](../README.md#JWTSecurity)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**201** | Creation successful. |  -  |

