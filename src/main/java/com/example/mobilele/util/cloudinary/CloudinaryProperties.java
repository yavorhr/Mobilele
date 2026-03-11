package com.example.mobilele.util.cloudinary;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "cloudinary")
@Component
public class CloudinaryProperties {
  private String cloudName;
  private String rootFolder;
  private String baseUrl;

  public CloudinaryProperties() {
  }

  public String getCloudName() {
    return cloudName;
  }

  public CloudinaryProperties setCloudName(String cloudName) {
    this.cloudName = cloudName;
    return this;
  }

  public String getRootFolder() {
    return rootFolder;
  }

  public CloudinaryProperties setRootFolder(String rootFolder) {
    this.rootFolder = rootFolder;
    return this;
  }

  public String getBaseUrl() {
    return baseUrl;
  }

  public CloudinaryProperties setBaseUrl(String baseUrl) {
    this.baseUrl = baseUrl;
    return this;
  }
}
