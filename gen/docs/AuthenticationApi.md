# AuthenticationApi

All URIs are relative to *http://localhost:8081*

Method | HTTP request | Description
------------- | ------------- | -------------
[**login**](AuthenticationApi.md#login) | **POST** /auth/login | Login
[**logout**](AuthenticationApi.md#logout) | **POST** /auth/logout | Logout.
[**register**](AuthenticationApi.md#register) | **POST** /auth/register | Register a new user.


<a name="login"></a>
# **login**
> LoginSuccessDTO login(loginRequestDTO)

Login

This public endpoint is used to login an existing user.

### Example
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

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **loginRequestDTO** | [**LoginRequestDTO**](LoginRequestDTO.md)|  | [optional]

### Return type

[**LoginSuccessDTO**](LoginSuccessDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**200** | Login successful. |  -  |
**401** | Unauthorized |  -  |

<a name="logout"></a>
# **logout**
> ApiMessageDTO logout()

Logout.

This private endpoint is used to logout a logged user.

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.auth.*;
import org.openapitools.client.models.*;
import org.openapitools.client.api.AuthenticationApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("http://localhost:8081");
    
    // Configure HTTP bearer authorization: JWTSecurity
    HttpBearerAuth JWTSecurity = (HttpBearerAuth) defaultClient.getAuthentication("JWTSecurity");
    JWTSecurity.setBearerToken("BEARER TOKEN");

    AuthenticationApi apiInstance = new AuthenticationApi(defaultClient);
    try {
      ApiMessageDTO result = apiInstance.logout();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AuthenticationApi#logout");
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
**200** | Logout successful. |  -  |
**401** | Unauthorized |  -  |

<a name="register"></a>
# **register**
> ApiMessageDTO register(registerDTO)

Register a new user.

This public endpoint is used to register a new user.

### Example
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
    RegisterDTO registerDTO = new RegisterDTO(); // RegisterDTO | 
    try {
      ApiMessageDTO result = apiInstance.register(registerDTO);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling AuthenticationApi#register");
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
 **registerDTO** | [**RegisterDTO**](RegisterDTO.md)|  | [optional]

### Return type

[**ApiMessageDTO**](ApiMessageDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
**201** | Register successful. |  -  |
**400** | Username or Email Already taken. |  -  |

