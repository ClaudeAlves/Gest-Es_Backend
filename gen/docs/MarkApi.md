# MarkApi

All URIs are relative to *http://localhost:8081*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getStudentMarks**](MarkApi.md#getStudentMarks) | **GET** /marks/{studentUsername} | Get marks.


<a name="getStudentMarks"></a>
# **getStudentMarks**
> List&lt;Integer&gt; getStudentMarks(studentUsername)

Get marks.

This endpoint is used to get the marks of a student

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.MarkApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8081");
    
    // Configure HTTP bearer authorization: JWTSecurity
    HttpBearerAuth JWTSecurity = (HttpBearerAuth) defaultClient.getAuthentication("JWTSecurity");
    JWTSecurity.setBearerToken("BEARER TOKEN");

    MarkApi apiInstance = new MarkApi(defaultClient);
    String studentUsername = "studentUsername_example"; // String | 
    try {
      List<Integer> result = apiInstance.getStudentMarks(studentUsername);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling MarkApi#getStudentMarks");
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
 **studentUsername** | **String**|  |

### Return type

**List&lt;Integer&gt;**

### Authorization

[JWTSecurity](../README.md#JWTSecurity)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Get student marks successfull. |  -  |

