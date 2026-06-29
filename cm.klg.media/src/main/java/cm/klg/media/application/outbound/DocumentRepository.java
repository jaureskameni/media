package cm.klg.media.application.outbound;

import cm.klg.media.domain.document.Document;
import cm.klg.media.domain.document.DocumentId;
import java.util.List;

public interface DocumentRepository {
  void insert(Document document);

  Document load(DocumentId id);

  List<Document> loadAll();

  void save(Document document);

  void update(Document document);
}
