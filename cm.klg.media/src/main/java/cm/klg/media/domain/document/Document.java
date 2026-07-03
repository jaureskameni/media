package cm.klg.media.domain.document;

import cm.klg.common.base.domain.CreatedAt;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class Document {
  private final DocumentId id;
  private DocumentName name;
  private DocumentSize size;
  private DocumentVisibility visibility;
  private DocumentType type;
  private DocumentStatus status;
  private CreatedAt createdAt;

  public Document(
      DocumentId id,
      DocumentName name,
      DocumentSize size,
      DocumentVisibility visibility,
      DocumentType type,
      DocumentStatus status,
      CreatedAt createdAt) {
    this.id = id;
    this.name = name;
    this.size = size;
    this.visibility = visibility;
    this.type = type;
    this.status = status;
    this.createdAt = createdAt;
  }

  public static Document of(
      DocumentName name, DocumentSize size, DocumentVisibility visibility, DocumentType type) {
    return new Document(
        DocumentId.generate(),
        name,
        size,
        visibility,
        type,
        DocumentStatus.ACTIVE,
        CreatedAt.from(LocalDateTime.now()));
  }

  public void markAsDeleted() {
    status = DocumentStatus.MARK_AS_DELETED;
  }
}
