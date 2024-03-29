/*
 * Gest-ES Api
 * This api provides communication with a database for a TB project
 *
 * The version of the OpenAPI document: 1.0.0
 * Contact: claude-andre.inacioalves@heig-vd.ch
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package org.openapitools.client.api;

import org.openapitools.client.ApiCallback;
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.ApiResponse;
import org.openapitools.client.Configuration;
import org.openapitools.client.Pair;
import org.openapitools.client.ProgressRequestBody;
import org.openapitools.client.ProgressResponseBody;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;



import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarkApi {
    private ApiClient localVarApiClient;

    public MarkApi() {
        this(Configuration.getDefaultApiClient());
    }

    public MarkApi(ApiClient apiClient) {
        this.localVarApiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return localVarApiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.localVarApiClient = apiClient;
    }

    /**
     * Build call for getStudentMarks
     * @param studentUsername  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Get student marks successfull. </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getStudentMarksCall(String studentUsername, final ApiCallback _callback) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/marks/{studentUsername}"
            .replaceAll("\\{" + "studentUsername" + "\\}", localVarApiClient.escapeString(studentUsername.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] { "JWTSecurity" };
        return localVarApiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getStudentMarksValidateBeforeCall(String studentUsername, final ApiCallback _callback) throws ApiException {
        
        // verify the required parameter 'studentUsername' is set
        if (studentUsername == null) {
            throw new ApiException("Missing the required parameter 'studentUsername' when calling getStudentMarks(Async)");
        }
        

        okhttp3.Call localVarCall = getStudentMarksCall(studentUsername, _callback);
        return localVarCall;

    }

    /**
     * Get marks.
     * This endpoint is used to get the marks of a student
     * @param studentUsername  (required)
     * @return List&lt;Integer&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Get student marks successfull. </td><td>  -  </td></tr>
     </table>
     */
    public List<Integer> getStudentMarks(String studentUsername) throws ApiException {
        ApiResponse<List<Integer>> localVarResp = getStudentMarksWithHttpInfo(studentUsername);
        return localVarResp.getData();
    }

    /**
     * Get marks.
     * This endpoint is used to get the marks of a student
     * @param studentUsername  (required)
     * @return ApiResponse&lt;List&lt;Integer&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Get student marks successfull. </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<List<Integer>> getStudentMarksWithHttpInfo(String studentUsername) throws ApiException {
        okhttp3.Call localVarCall = getStudentMarksValidateBeforeCall(studentUsername, null);
        Type localVarReturnType = new TypeToken<List<Integer>>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Get marks. (asynchronously)
     * This endpoint is used to get the marks of a student
     * @param studentUsername  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Get student marks successfull. </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getStudentMarksAsync(String studentUsername, final ApiCallback<List<Integer>> _callback) throws ApiException {

        okhttp3.Call localVarCall = getStudentMarksValidateBeforeCall(studentUsername, _callback);
        Type localVarReturnType = new TypeToken<List<Integer>>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
}
