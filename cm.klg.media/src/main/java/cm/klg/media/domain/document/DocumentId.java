package cm.klg.media.domain.document;

import java.util.Objects;
import java.util.UUID;

public record DocumentId(UUID value) {
  public DocumentId {
    Objects.requireNonNull(value, "Document id is required");
  }

  public static DocumentId generate() {
    return new DocumentId(UUID.randomUUID());
  }

  public static DocumentId from(UUID value) {
    return new DocumentId(value);
  }
}
