package cm.klg.media.application.outbound;

import cm.klg.media.domain.document.Document;
import java.io.InputStream;

public interface DocumentStorageRepository {
  void store(Document document, InputStream file);

  InputStream load(Document document);
}
