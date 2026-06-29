package cm.klg.media.adapter.persistence.document_storage;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cm.klg.media.adapter.configurations.DocumentStorageConfigurations;
import cm.klg.media.domain.document.Document;
import cm.klg.media.domain.document.DocumentName;
import cm.klg.media.domain.document.DocumentSize;
import cm.klg.media.domain.document.DocumentType;
import cm.klg.media.domain.document.DocumentVisibility;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import java.io.ByteArrayInputStream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MinioDocumentStorageTest {

  @Mock private MinioClient minioClient;
  @Mock private DocumentStorageConfigurations configurations;

  @InjectMocks private MinioDocumentStorage objectUnderTest;

  @Test
  void store_shouldPutObjectInBucket() throws Exception {
    when(configurations.getS3BucketName()).thenReturn("media-bucket");

    var document =
        Document.of(
            DocumentName.from("photo.jpg"),
            DocumentSize.from(1024),
            DocumentVisibility.PUBLIC,
            DocumentType.IMAGE_JPEG);

    var file = new ByteArrayInputStream("test".getBytes());

    objectUnderTest.store(document, file);

    verify(minioClient).putObject(any(PutObjectArgs.class));
  }

  @Test
  void store_shouldThrowFailToStoreDocumentException_whenMinioFails() throws Exception {
    when(configurations.getS3BucketName()).thenReturn("media-bucket");

    var document =
        Document.of(
            DocumentName.from("photo.jpg"),
            DocumentSize.from(1024),
            DocumentVisibility.PUBLIC,
            DocumentType.IMAGE_JPEG);

    var file = new ByteArrayInputStream("test".getBytes());

    doThrow(new java.io.IOException("MinIO error"))
        .when(minioClient)
        .putObject(any(PutObjectArgs.class));

    assertThatThrownBy(() -> objectUnderTest.store(document, file))
        .isInstanceOf(FailToStoreDocumentException.class);
  }
}
