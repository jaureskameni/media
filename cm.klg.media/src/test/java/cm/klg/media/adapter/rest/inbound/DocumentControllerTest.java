package cm.klg.media.adapter.rest.inbound;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cm.klg.common.base.transaction.UseCaseExecutor;
import cm.klg.generated.map.adapter.rest.inbound.dto.CreationResponseDTO;
import cm.klg.generated.map.adapter.rest.inbound.dto.DocumentDTO;
import cm.klg.media.application.usecase.GetDocumentUseCase;
import cm.klg.media.application.usecase.GetDocumentsUseCase;
import cm.klg.media.domain.document.Document;
import cm.klg.media.domain.document.DocumentId;
import cm.klg.media.domain.document.DocumentName;
import cm.klg.media.domain.document.DocumentSize;
import cm.klg.media.domain.document.DocumentType;
import cm.klg.media.domain.document.DocumentVisibility;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@ExtendWith(MockitoExtension.class)
class DocumentControllerTest {

  @Mock private UseCaseExecutor useCaseExecutor;
  @Mock private RestMapper restMapper;

  @InjectMocks private DocumentController objectUnderTest;

  @Test
  void createDocument_shouldReturnCreatedWithNewId_whenSuccessful() {
    var documentId = new DocumentId(UUID.randomUUID());

    when(useCaseExecutor.executeCommand(any())).thenReturn(documentId);

    var result =
        given()
            .standaloneSetup(objectUnderTest)
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .when()
            .post("/documents?type=IMAGE_JPEG&visibility=PUBLIC")
            .then()
            .statusCode(HttpStatus.CREATED.value())
            .extract()
            .as(CreationResponseDTO.class);

    assertThat(result.getNewId()).isEqualTo(documentId.value());
  }

  @Test
  void getDocument_shouldReturnDocumentDTOWithContent_whenFound() {
    var documentId = UUID.randomUUID();
    var document =
        Document.of(
            DocumentName.from("photo.jpg"),
            DocumentSize.from(1024),
            DocumentVisibility.PUBLIC,
            DocumentType.IMAGE_JPEG);
    var content = new ByteArrayInputStream("test-image-content".getBytes());
    var documentWithContent = new GetDocumentUseCase.Response(document, content);
    var expectedDTO = new DocumentDTO().name("photo.jpg");

    when(useCaseExecutor.executeCommand(any())).thenReturn(documentWithContent);
    when(restMapper.toDocumentDTO(documentWithContent)).thenReturn(expectedDTO);

    var result =
        given()
            .standaloneSetup(objectUnderTest)
            .when()
            .get("/documents/{documentId}", documentId)
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .extract()
            .as(DocumentDTO.class);

    assertThat(result.getName()).isEqualTo("photo.jpg");
  }

  @Test
  void getDocuments_shouldReturnDocumentDTOList_whenFound() {
    var document =
        Document.of(
            DocumentName.from("photo.jpg"),
            DocumentSize.from(1024),
            DocumentVisibility.PUBLIC,
            DocumentType.IMAGE_JPEG);
    var content = new ByteArrayInputStream("content".getBytes());
    var response = new GetDocumentsUseCase.Response(document, content);
    var responses = List.of(response);
    var documentDTO = new DocumentDTO().name("photo.jpg");

    when(useCaseExecutor.executeQuery(any())).thenReturn(responses);
    when(restMapper.toDocumentDTO(response)).thenReturn(documentDTO);

    var result =
        given()
            .standaloneSetup(objectUnderTest)
            .when()
            .get("/documents")
            .then()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .jsonPath()
            .getList(".", DocumentDTO.class);

    assertThat(result).hasSize(1).containsExactly(documentDTO);
  }

  @Test
  void deleteDocument_shouldReturnNoContent_whenSuccessful() {
    var documentId = UUID.randomUUID();

    given()
        .standaloneSetup(objectUnderTest)
        .when()
        .delete("/documents/{documentId}", documentId)
        .then()
        .statusCode(HttpStatus.NO_CONTENT.value());

    verify(useCaseExecutor).runCommand(any());
  }
}
