package cm.klg.media.adapter.configurations;

import cm.klg.media.application.config.Configurations;
import cm.klg.media.config.StorageProperties;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ApplicationConfig implements Configurations {
  private final StorageProperties storageProperties;

  @Override
  public long getMaxDocumentSizeInBytes() {
    return storageProperties.maxDocumentSizeInBytes().toBytes();
  }
}
