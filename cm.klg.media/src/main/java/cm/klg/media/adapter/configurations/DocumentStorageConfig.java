package cm.klg.media.adapter.configurations;

import cm.klg.media.config.StorageProperties;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DocumentStorageConfig implements DocumentStorageConfigurations {
  private final StorageProperties storageProperties;

  @Override
  public String getS3Url() {
    return storageProperties.s3().url();
  }

  @Override
  public String getS3BucketName() {
    return storageProperties.s3().bucketName();
  }

  @Override
  public String getS3Name() {
    return storageProperties.s3().credentials().name();
  }

  @Override
  public String getS3Password() {
    return storageProperties.s3().credentials().secret();
  }
}
