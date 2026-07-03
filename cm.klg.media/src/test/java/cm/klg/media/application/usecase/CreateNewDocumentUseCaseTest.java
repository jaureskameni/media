package cm.klg.media.application.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import cm.klg.media.application.outbound.DocumentRepository;
import cm.klg.media.application.outbound.DocumentStorageRepository;
import cm.klg.media.domain.document.DocumentName;
import cm.klg.media.domain.document.DocumentSize;
import cm.klg.media.domain.document.DocumentSizeLimitExceededException;
import cm.klg.media.domain.document.DocumentSizePolicy;
import cm.klg.media.domain.document.DocumentType;
import cm.klg.media.domain.document.DocumentVisibility;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateNewDocumentUseCaseTest {

  @Mock private DocumentRepository documentRepository;
  @Mock private DocumentStorageRepository documentStorageRepository;
  @Mock private DocumentSizePolicy documentSizePolicy;

  @InjectMocks private CreateNewDocumentUseCase objectUnderTest;

  @Test
  void execute_shouldStoreDocumentAndReturnId_whenSizeIsValid() {
    var command =
        new CreateNewDocumentUseCase.Command(
            DocumentName.from("photo.jpg"),
            DocumentSize.from(1024),
            DocumentVisibility.PUBLIC,
            DocumentType.IMAGE_JPEG,
            new ByteArrayInputStream("test".getBytes()));

    var documentId = objectUnderTest.execute(command);

    assertThat(documentId).isNotNull();
    assertThat(documentId.value()).isNotNull();
    verify(documentSizePolicy).validate(command.size());
    verify(documentStorageRepository).store(any(), any(InputStream.class));
    verify(documentRepository).insert(any());
  }

  @Test
  void execute_shouldThrow_whenSizeExceedsLimit() {
    var command =
        new CreateNewDocumentUseCase.Command(
            DocumentName.from("large.jpg"),
            DocumentSize.from(999999),
            DocumentVisibility.PUBLIC,
            DocumentType.IMAGE_PNG,
            new ByteArrayInputStream("test".getBytes()));

    doThrow(new DocumentSizeLimitExceededException())
        .when(documentSizePolicy)
        .validate(command.size());

    assertThatThrownBy(() -> objectUnderTest.execute(command))
        .isInstanceOf(DocumentSizeLimitExceededException.class);
  }
}
