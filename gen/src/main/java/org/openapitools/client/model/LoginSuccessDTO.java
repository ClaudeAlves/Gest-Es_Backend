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


package org.openapitools.client.model;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * LoginSuccessDTO
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2022-06-26T16:45:46.363535700+02:00[Europe/Berlin]")
public class LoginSuccessDTO {
  public static final String SERIALIZED_NAME_JWT = "jwt";
  @SerializedName(SERIALIZED_NAME_JWT)
  private String jwt;

  public static final String SERIALIZED_NAME_USERNAME = "username";
  @SerializedName(SERIALIZED_NAME_USERNAME)
  private String username;

  public static final String SERIALIZED_NAME_ROLES = "roles";
  @SerializedName(SERIALIZED_NAME_ROLES)
  private List<String> roles = null;


  public LoginSuccessDTO jwt(String jwt) {
    
    this.jwt = jwt;
    return this;
  }

   /**
   * Get jwt
   * @return jwt
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getJwt() {
    return jwt;
  }


  public void setJwt(String jwt) {
    this.jwt = jwt;
  }


  public LoginSuccessDTO username(String username) {
    
    this.username = username;
    return this;
  }

   /**
   * Get username
   * @return username
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public String getUsername() {
    return username;
  }


  public void setUsername(String username) {
    this.username = username;
  }


  public LoginSuccessDTO roles(List<String> roles) {
    
    this.roles = roles;
    return this;
  }

  public LoginSuccessDTO addRolesItem(String rolesItem) {
    if (this.roles == null) {
      this.roles = new ArrayList<String>();
    }
    this.roles.add(rolesItem);
    return this;
  }

   /**
   * Get roles
   * @return roles
  **/
  @javax.annotation.Nullable
  @ApiModelProperty(value = "")

  public List<String> getRoles() {
    return roles;
  }


  public void setRoles(List<String> roles) {
    this.roles = roles;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LoginSuccessDTO loginSuccessDTO = (LoginSuccessDTO) o;
    return Objects.equals(this.jwt, loginSuccessDTO.jwt) &&
        Objects.equals(this.username, loginSuccessDTO.username) &&
        Objects.equals(this.roles, loginSuccessDTO.roles);
  }

  @Override
  public int hashCode() {
    return Objects.hash(jwt, username, roles);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LoginSuccessDTO {\n");
    sb.append("    jwt: ").append(toIndentedString(jwt)).append("\n");
    sb.append("    username: ").append(toIndentedString(username)).append("\n");
    sb.append("    roles: ").append(toIndentedString(roles)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

