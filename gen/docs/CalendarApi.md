# CalendarApi

All URIs are relative to *http://localhost:8081*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getCalendar**](CalendarApi.md#getCalendar) | **GET** /calendar/{username} | Get a specific user calendar.
[**getCalendarUser**](CalendarApi.md#getCalendarUser) | **GET** /calendar | Get a specific user calendar.
[**getClassCalendar**](CalendarApi.md#getClassCalendar) | **GET** /calendar/class/{className} | Get a class calendar.


<a name="getCalendar"></a>
# **getCalendar**
> CalendarDTO getCalendar(username)

Get a specific user calendar.

This endpoint is used to get the calendar of a user

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.CalendarApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8081");
    
    // Configure HTTP bearer authorization: JWTSecurity
    HttpBearerAuth JWTSecurity = (HttpBearerAuth) defaultClient.getAuthentication("JWTSecurity");
    JWTSecurity.setBearerToken("BEARER TOKEN");

    CalendarApi apiInstance = new CalendarApi(defaultClient);
    String username = "username_example"; // String | 
    try {
      CalendarDTO result = apiInstance.getCalendar(username);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CalendarApi#getCalendar");
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
 **username** | **String**|  |

### Return type

[**CalendarDTO**](CalendarDTO.md)

### Authorization

[JWTSecurity](../README.md#JWTSecurity)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Get user calendar successfull. |  -  |

<a name="getCalendarUser"></a>
# **getCalendarUser**
> CalendarDTO getCalendarUser()

Get a specific user calendar.

This endpoint is used to get the calendar of the authenticated user

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.CalendarApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8081");
    
    // Configure HTTP bearer authorization: JWTSecurity
    HttpBearerAuth JWTSecurity = (HttpBearerAuth) defaultClient.getAuthentication("JWTSecurity");
    JWTSecurity.setBearerToken("BEARER TOKEN");

    CalendarApi apiInstance = new CalendarApi(defaultClient);
    try {
      CalendarDTO result = apiInstance.getCalendarUser();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CalendarApi#getCalendarUser");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**CalendarDTO**](CalendarDTO.md)

### Authorization

[JWTSecurity](../README.md#JWTSecurity)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Get users calendar successfull. |  -  |

<a name="getClassCalendar"></a>
# **getClassCalendar**
> CalendarDTO getClassCalendar(className)

Get a class calendar.

This endpoint is used to get the calendar of a class

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.CalendarApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8081");
    
    // Configure HTTP bearer authorization: JWTSecurity
    HttpBearerAuth JWTSecurity = (HttpBearerAuth) defaultClient.getAuthentication("JWTSecurity");
    JWTSecurity.setBearerToken("BEARER TOKEN");

    CalendarApi apiInstance = new CalendarApi(defaultClient);
    String className = "className_example"; // String | 
    try {
      CalendarDTO result = apiInstance.getClassCalendar(className);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CalendarApi#getClassCalendar");
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
 **className** | **String**|  |

### Return type

[**CalendarDTO**](CalendarDTO.md)

### Authorization

[JWTSecurity](../README.md#JWTSecurity)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Get class calendar successfull. |  -  |

