/*
 * KeyVaultClient
 * The key vault client performs cryptographic key operations and vault operations against the Key Vault service.
 *
 * OpenAPI spec version: 2016-10-01
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.microsoft.identity.internal.test.keyvault.model;

import java.util.Objects;

import com.google.gson.annotations.SerializedName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Properties of the key pair backing a certificate.
 */
@ApiModel(description = "Properties of the key pair backing a certificate.")
public class KeyProperties {
  @SerializedName("exportable")
  private Boolean exportable = null;

  @SerializedName("kty")
  private String kty = null;

  @SerializedName("key_size")
  private Integer keySize = null;

  @SerializedName("reuse_key")
  private Boolean reuseKey = null;

  public KeyProperties exportable(Boolean exportable) {
    this.exportable = exportable;
    return this;
  }

   /**
   * Indicates if the private key can be exported.
   * @return exportable
  **/
  @ApiModelProperty(value = "Indicates if the private key can be exported.")
  public Boolean isExportable() {
    return exportable;
  }

  public void setExportable(Boolean exportable) {
    this.exportable = exportable;
  }

  public KeyProperties kty(String kty) {
    this.kty = kty;
    return this;
  }

   /**
   * The key type.
   * @return kty
  **/
  @ApiModelProperty(value = "The key type.")
  public String getKty() {
    return kty;
  }

  public void setKty(String kty) {
    this.kty = kty;
  }

  public KeyProperties keySize(Integer keySize) {
    this.keySize = keySize;
    return this;
  }

   /**
   * The key size in bytes. For example;  1024 or 2048.
   * @return keySize
  **/
  @ApiModelProperty(value = "The key size in bytes. For example;  1024 or 2048.")
  public Integer getKeySize() {
    return keySize;
  }

  public void setKeySize(Integer keySize) {
    this.keySize = keySize;
  }

  public KeyProperties reuseKey(Boolean reuseKey) {
    this.reuseKey = reuseKey;
    return this;
  }

   /**
   * Indicates if the same key pair will be used on certificate renewal.
   * @return reuseKey
  **/
  @ApiModelProperty(value = "Indicates if the same key pair will be used on certificate renewal.")
  public Boolean isReuseKey() {
    return reuseKey;
  }

  public void setReuseKey(Boolean reuseKey) {
    this.reuseKey = reuseKey;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    KeyProperties keyProperties = (KeyProperties) o;
    return Objects.equals(this.exportable, keyProperties.exportable) &&
        Objects.equals(this.kty, keyProperties.kty) &&
        Objects.equals(this.keySize, keyProperties.keySize) &&
        Objects.equals(this.reuseKey, keyProperties.reuseKey);
  }

  @Override
  public int hashCode() {
    return Objects.hash(exportable, kty, keySize, reuseKey);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class KeyProperties {\n");
    
    sb.append("    exportable: ").append(toIndentedString(exportable)).append("\n");
    sb.append("    kty: ").append(toIndentedString(kty)).append("\n");
    sb.append("    keySize: ").append(toIndentedString(keySize)).append("\n");
    sb.append("    reuseKey: ").append(toIndentedString(reuseKey)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
