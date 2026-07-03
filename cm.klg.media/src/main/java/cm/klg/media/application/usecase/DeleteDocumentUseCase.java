package cm.klg.media.application.usecase;

import cm.klg.media.application.outbound.DocumentRepository;
import cm.klg.media.domain.document.DocumentId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteDocumentUseCase {
  private final DocumentRepository documentRepository;

  public void execute(DocumentId id) {
    var document = documentRepository.load(id);
    document.markAsDeleted();
    documentRepository.update(document);
  }
}
