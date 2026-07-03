package cm.klg.media.adapter.persistence.document_storage;

import cm.klg.media.adapter.configurations.DocumentStorageConfigurations;
import cm.klg.media.application.outbound.DocumentStorageRepository;
import cm.klg.media.domain.document.Document;
import io.minio.GetObjectArgs;
import io.minio.PutObjectArgs;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MinioDocumentStorage implements DocumentStorageRepository {
  private final io.minio.MinioClient minioClient;
  private final DocumentStorageConfigurations configurations;

  @Override
  public InputStream load(Document document) {
    try {
      return minioClient.getObject(
          GetObjectArgs.builder()
              .bucket(configurations.getS3BucketName())
              .object(document.getId().value().toString())
              .build());
    } catch (InsufficientDataException
        | InternalException
        | InvalidResponseException
        | ServerException
        | XmlParserException
        | IOException
        | NoSuchAlgorithmException
        | ErrorResponseException
        | InvalidKeyException _) {
      throw new FailToLoadDocumentException();
    }
  }

  @Override
  public void store(Document document, InputStream file) {
    try {
      minioClient.putObject(
          PutObjectArgs.builder()
              .bucket(configurations.getS3BucketName())
              .contentType(document.getType().mimeType())
              .object(document.getId().value().toString())
              .stream(file, document.getSize().value(), -1)
              .build());
    } catch (InsufficientDataException
        | InternalException
        | InvalidResponseException
        | ServerException
        | XmlParserException
        | IOException
        | NoSuchAlgorithmException
        | ErrorResponseException
        | InvalidKeyException _) {
      throw new FailToStoreDocumentException();
    }
  }
}
