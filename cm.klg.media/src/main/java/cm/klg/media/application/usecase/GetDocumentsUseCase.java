package cm.klg.media.application.usecase;

import cm.klg.media.application.outbound.DocumentRepository;
import cm.klg.media.application.outbound.DocumentStorageRepository;
import cm.klg.media.domain.document.Document;
import java.io.InputStream;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetDocumentsUseCase {
  private final DocumentRepository documentRepository;
  private final DocumentStorageRepository documentStorageRepository;

  public List<Response> execute() {
    return documentRepository.loadAll().stream()
        .map(
            document -> {
              InputStream content = documentStorageRepository.load(document);
              return new Response(document, content);
            })
        .toList();
  }

  public record Response(Document metadata, InputStream content) {}
}
