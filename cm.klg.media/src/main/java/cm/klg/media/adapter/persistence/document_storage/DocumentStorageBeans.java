package cm.klg.media.adapter.persistence.document_storage;

import cm.klg.media.adapter.configurations.DocumentStorageConfig;
import cm.klg.media.adapter.configurations.DocumentStorageConfigurations;
import cm.klg.media.application.outbound.DocumentStorageRepository;
import cm.klg.media.config.StorageProperties;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DocumentStorageBeans {

  @Bean
  public DocumentStorageConfigurations documentStorageConfigurations(
      StorageProperties storageProperties) {
    return new DocumentStorageConfig(storageProperties);
  }

  @Bean
  public MinioClient minioSdkClient(DocumentStorageConfigurations documentStorageConfigurations) {
    return MinioClient.builder()
        .endpoint(documentStorageConfigurations.getS3Url())
        .credentials(
            documentStorageConfigurations.getS3Name(),
            documentStorageConfigurations.getS3Password())
        .build();
  }

  @Bean
  public DocumentStorageRepository documentStorageRepository(
      MinioClient minioSdkClient, DocumentStorageConfigurations documentStorageConfigurations) {
    return new MinioDocumentStorage(minioSdkClient, documentStorageConfigurations);
  }
}
