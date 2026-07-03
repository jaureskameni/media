package cm.klg.media.application.usecase;

import cm.klg.media.application.outbound.DocumentRepository;
import cm.klg.media.application.outbound.DocumentStorageRepository;
import cm.klg.media.domain.document.Document;
import cm.klg.media.domain.document.DocumentId;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetDocumentUseCase {
  private final DocumentRepository documentRepository;
  private final DocumentStorageRepository documentStorageRepository;

  public Response execute(DocumentId id) {
    var document = documentRepository.load(id);
    var content = documentStorageRepository.load(document);
    return new Response(document, content);
  }

  public record Response(Document metadata, InputStream content) {}
}
