package cm.klg.media.application.usecase;

import cm.klg.media.application.outbound.DocumentRepository;
import cm.klg.media.application.outbound.DocumentStorageRepository;
import cm.klg.media.domain.document.Document;
import cm.klg.media.domain.document.DocumentId;
import cm.klg.media.domain.document.DocumentName;
import cm.klg.media.domain.document.DocumentSize;
import cm.klg.media.domain.document.DocumentSizePolicy;
import cm.klg.media.domain.document.DocumentType;
import cm.klg.media.domain.document.DocumentVisibility;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateNewDocumentUseCase {
  private final DocumentRepository documentRepository;
  private final DocumentStorageRepository documentStorageRepository;
  private final DocumentSizePolicy documentSizePolicy;

  public DocumentId execute(Command command) {
    documentSizePolicy.validate(command.size);
    Document document = Document.of(command.name, command.size, command.visibility, command.type);
    documentStorageRepository.store(document, command.file);
    documentRepository.insert(document);
    return document.getId();
  }

  public record Command(
      DocumentName name,
      DocumentSize size,
      DocumentVisibility visibility,
      DocumentType type,
      InputStream file) {}
}
