package cm.klg.media.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.unit.DataSize;

@Component
@ConfigurationProperties(prefix = "jk-dev.storage")
public record StorageProperties(DataSize maxDocumentSizeInBytes, S3 s3) {
  public record S3(String url, String bucketName, Credentials credentials) {}

  public record Credentials(String name, String secret) {}
}
