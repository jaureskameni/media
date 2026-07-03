package cm.klg.media.adapter.rest.inbound;

import cm.klg.common.base.transaction.UseCaseExecutor;
import cm.klg.generated.map.adapter.rest.inbound.api.DocumentApi;
import cm.klg.generated.map.adapter.rest.inbound.dto.CreationResponseDTO;
import cm.klg.generated.map.adapter.rest.inbound.dto.DocumentDTO;
import cm.klg.generated.map.adapter.rest.inbound.dto.DocumentTypeDTO;
import cm.klg.generated.map.adapter.rest.inbound.dto.DocumentVisibilityDTO;
import cm.klg.media.application.usecase.CreateNewDocumentUseCase;
import cm.klg.media.application.usecase.DeleteDocumentUseCase;
import cm.klg.media.application.usecase.GetDocumentUseCase;
import cm.klg.media.application.usecase.GetDocumentsUseCase;
import cm.klg.media.domain.document.DocumentId;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class DocumentController implements DocumentApi {
  private final CreateNewDocumentUseCase createNewDocumentUseCase;
  private final GetDocumentUseCase getDocumentUseCase;
  private final GetDocumentsUseCase getDocumentsUseCase;
  private final DeleteDocumentUseCase deleteDocumentUseCase;
  private final UseCaseExecutor useCaseExecutor;
  private final RestMapper restMapper;

  @Override
  public ResponseEntity<CreationResponseDTO> createDocument(
      DocumentTypeDTO type, DocumentVisibilityDTO visibility, MultipartFile file) {
    DocumentId documentId =
        useCaseExecutor.executeCommand(
            () ->
                createNewDocumentUseCase.execute(
                    restMapper.toCreateNewDocumentCommand(type, visibility, file)));
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new CreationResponseDTO().newId(documentId.value()));
  }

  @Override
  public ResponseEntity<Void> markAsDelete(UUID documentId) {
    useCaseExecutor.runCommand(() -> deleteDocumentUseCase.execute(DocumentId.from(documentId)));
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @Override
  public ResponseEntity<DocumentDTO> getDocumentById(UUID documentId) {
    var result =
        useCaseExecutor.executeCommand(
            () -> getDocumentUseCase.execute(DocumentId.from(documentId)));
    return ResponseEntity.ok(restMapper.toDocumentDTO(result));
  }

  @Override
  public ResponseEntity<List<DocumentDTO>> getAllDocuments() {
    List<GetDocumentsUseCase.Response> result =
        useCaseExecutor.executeQuery(
            () -> useCaseExecutor.executeCommand(getDocumentsUseCase::execute));
    return ResponseEntity.ok(result.stream().map(restMapper::toDocumentDTO).toList());
  }
}
