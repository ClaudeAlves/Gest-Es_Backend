# UserApi

All URIs are relative to *http://localhost:8081*

Method | HTTP request | Description
------------- | ------------- | -------------
[**deleteUser**](UserApi.md#deleteUser) | **DELETE** /users/{username} | Delete a specific user.
[**deleteUsers**](UserApi.md#deleteUsers) | **DELETE** /admin/users | Delete all users.
[**getUser**](UserApi.md#getUser) | **GET** /users/{username} | Get a specific user.
[**getUsers**](UserApi.md#getUsers) | **GET** /admin/users | Get all users.
[**updateUser**](UserApi.md#updateUser) | **PUT** /users/{username} | Update a specific user.


<a name="deleteUser"></a>
# **deleteUser**
> ApiMessageDTO deleteUser(username)

Delete a specific user.

This private endpoint is used to remove one user.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.UserApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8081");
    
    // Configure HTTP bearer authorization: JWTSecurity
    HttpBearerAuth JWTSecurity = (HttpBearerAuth) defaultClient.getAuthentication("JWTSecurity");
    JWTSecurity.setBearerToken("BEARER TOKEN");

    UserApi apiInstance = new UserApi(defaultClient);
    String username = "username_example"; // String | 
    try {
      ApiMessageDTO result = apiInstance.deleteUser(username);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling UserApi#deleteUser");
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

[**ApiMessageDTO**](ApiMessageDTO.md)

### Authorization

[JWTSecurity](../README.md#JWTSecurity)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | User deletion successful. |  -  |

<a name="deleteUsers"></a>
# **deleteUsers**
> ApiMessageDTO deleteUsers()

Delete all users.

This private endpoint is used to remove all users.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.UserApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8081");
    
    // Configure HTTP bearer authorization: JWTSecurity
    HttpBearerAuth JWTSecurity = (HttpBearerAuth) defaultClient.getAuthentication("JWTSecurity");
    JWTSecurity.setBearerToken("BEARER TOKEN");

    UserApi apiInstance = new UserApi(defaultClient);
    try {
      ApiMessageDTO result = apiInstance.deleteUsers();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling UserApi#deleteUsers");
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

[**ApiMessageDTO**](ApiMessageDTO.md)

### Authorization

[JWTSecurity](../README.md#JWTSecurity)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Users deletion successful. |  -  |

<a name="getUser"></a>
# **getUser**
> UserDTO getUser(username)

Get a specific user.

This private endpoint is used to get one user.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.UserApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8081");
    
    // Configure HTTP bearer authorization: JWTSecurity
    HttpBearerAuth JWTSecurity = (HttpBearerAuth) defaultClient.getAuthentication("JWTSecurity");
    JWTSecurity.setBearerToken("BEARER TOKEN");

    UserApi apiInstance = new UserApi(defaultClient);
    String username = "username_example"; // String | 
    try {
      UserDTO result = apiInstance.getUser(username);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling UserApi#getUser");
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

[**UserDTO**](UserDTO.md)

### Authorization

[JWTSecurity](../README.md#JWTSecurity)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Get user successful. |  -  |

<a name="getUsers"></a>
# **getUsers**
> List&lt;UserSimpleDTO&gt; getUsers()

Get all users.

This private endpoint is used to get all users.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.UserApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8081");
    
    // Configure HTTP bearer authorization: JWTSecurity
    HttpBearerAuth JWTSecurity = (HttpBearerAuth) defaultClient.getAuthentication("JWTSecurity");
    JWTSecurity.setBearerToken("BEARER TOKEN");

    UserApi apiInstance = new UserApi(defaultClient);
    try {
      List<UserSimpleDTO> result = apiInstance.getUsers();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling UserApi#getUsers");
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

[**List&lt;UserSimpleDTO&gt;**](UserSimpleDTO.md)

### Authorization

[JWTSecurity](../README.md#JWTSecurity)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Get users successful. |  -  |

<a name="updateUser"></a>
# **updateUser**
> UserDTO updateUser(username, userModificationDTO)

Update a specific user.

This private endpoint is used to modify one user.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.UserApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8081");
    
    // Configure HTTP bearer authorization: JWTSecurity
    HttpBearerAuth JWTSecurity = (HttpBearerAuth) defaultClient.getAuthentication("JWTSecurity");
    JWTSecurity.setBearerToken("BEARER TOKEN");

    UserApi apiInstance = new UserApi(defaultClient);
    String username = "username_example"; // String | 
    UserModificationDTO userModificationDTO = new UserModificationDTO(); // UserModificationDTO | 
    try {
      UserDTO result = apiInstance.updateUser(username, userModificationDTO);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling UserApi#updateUser");
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
 **userModificationDTO** | [**UserModificationDTO**](UserModificationDTO.md)|  | [optional]

### Return type

[**UserDTO**](UserDTO.md)

### Authorization

[JWTSecurity](../README.md#JWTSecurity)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | User update successful. |  -  |

