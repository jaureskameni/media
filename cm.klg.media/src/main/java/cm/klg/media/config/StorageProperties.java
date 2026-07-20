package cm.klg.media.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jk-dev.storage")
public record StorageProperties(long maxDocumentSizeInBytes, S3 s3) {
  public record S3(String url, String bucketName, Credentials credentials) {}

  public record Credentials(String name, String secret) {}
}
