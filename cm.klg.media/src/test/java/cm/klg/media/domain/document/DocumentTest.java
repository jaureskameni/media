package cm.klg.media.domain.document;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class DocumentTest {

  @Test
  void of_shouldCreateActiveDocumentWithGeneratedId() {
    var name = DocumentName.from("photo.jpg");
    var size = DocumentSize.from(1024);
    var document = Document.of(name, size, DocumentVisibility.PUBLIC, DocumentType.IMAGE_JPEG);

    assertThat(document.getId()).isNotNull();
    assertThat(document.getName()).isEqualTo(name);
    assertThat(document.getSize()).isEqualTo(size);
    assertThat(document.getVisibility()).isEqualTo(DocumentVisibility.PUBLIC);
    assertThat(document.getType()).isEqualTo(DocumentType.IMAGE_JPEG);
    assertThat(document.getStatus()).isEqualTo(DocumentStatus.ACTIVE);
    assertThat(document.getCreatedAt()).isNotNull();
  }

  @Test
  void markAsDeleted_shouldSetStatusToMarkAsDeleted() {
    var document =
        Document.of(
            DocumentName.from("doc.pdf"),
            DocumentSize.from(512),
            DocumentVisibility.PRIVATE,
            DocumentType.IMAGE_PNG);

    document.markAsDeleted();

    assertThat(document.getStatus()).isEqualTo(DocumentStatus.MARK_AS_DELETED);
  }
}
