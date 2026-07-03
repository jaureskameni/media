package cm.klg.media.adapter.rest.inbound;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import cm.klg.generated.map.adapter.rest.inbound.dto.DocumentTypeDTO;
import cm.klg.generated.map.adapter.rest.inbound.dto.DocumentVisibilityDTO;
import cm.klg.media.domain.document.DocumentName;
import cm.klg.media.domain.document.DocumentNameCannotBeBlankException;
import cm.klg.media.domain.document.DocumentSize;
import cm.klg.media.domain.document.DocumentType;
import cm.klg.media.domain.document.DocumentVisibility;
import java.io.ByteArrayInputStream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
class RestMapperTest {

  @InjectMocks private RestMapperImpl objectUnderTest;

  @Mock private MultipartFile file;

  @Test
  void toCreateNewDocumentCommand_shouldMapAllFieldsCorrectly() throws Exception {
    when(file.getOriginalFilename()).thenReturn("photo.jpg");
    when(file.getSize()).thenReturn(2048L);
    when(file.getInputStream()).thenReturn(new ByteArrayInputStream("test".getBytes()));

    var command =
        objectUnderTest.toCreateNewDocumentCommand(
            DocumentTypeDTO.IMAGE_JPEG, DocumentVisibilityDTO.PUBLIC, file);

    assertThat(command).isNotNull();
    assertThat(command.name()).isEqualTo(DocumentName.from("photo.jpg"));
    assertThat(command.size()).isEqualTo(DocumentSize.from(2048));
    assertThat(command.visibility()).isEqualTo(DocumentVisibility.PUBLIC);
    assertThat(command.type()).isEqualTo(DocumentType.IMAGE_JPEG);
    assertThat(command.file()).isNotNull();
  }

  @Test
  void toCreateNewDocumentCommand_shouldThrow_whenFileCannotBeRead() throws Exception {
    when(file.getOriginalFilename()).thenReturn("photo.jpg");
    when(file.getSize()).thenReturn(100L);
    when(file.getInputStream()).thenThrow(new java.io.IOException("IO error"));

    assertThatThrownBy(
            () ->
                objectUnderTest.toCreateNewDocumentCommand(
                    DocumentTypeDTO.IMAGE_PNG, DocumentVisibilityDTO.PRIVATE, file))
        .isInstanceOf(CannotReadDocumentException.class);
  }

  @Test
  void toCreateNewDocumentCommand_shouldThrow_whenOriginalFilenameIsNull() {
    when(file.getOriginalFilename()).thenReturn(null);

    assertThatThrownBy(
            () ->
                objectUnderTest.toCreateNewDocumentCommand(
                    DocumentTypeDTO.IMAGE_PNG, DocumentVisibilityDTO.PRIVATE, file))
        .isInstanceOf(DocumentNameCannotBeBlankException.class);
  }
}
