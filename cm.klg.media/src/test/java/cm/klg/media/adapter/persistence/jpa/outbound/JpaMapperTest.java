package cm.klg.media.adapter.persistence.jpa.outbound;

import static org.assertj.core.api.Assertions.assertThat;

import cm.klg.media.domain.document.Document;
import cm.klg.media.domain.document.DocumentName;
import cm.klg.media.domain.document.DocumentSize;
import cm.klg.media.domain.document.DocumentType;
import cm.klg.media.domain.document.DocumentVisibility;
import org.junit.jupiter.api.Test;

class JpaMapperTest {

  private final JpaMapperImpl objectUnderTest = new JpaMapperImpl();

  @Test
  void toDocumentDomainDomainJpa_shouldMapAllFields() {
    var document =
        Document.of(
            DocumentName.from("photo.jpg"),
            DocumentSize.from(2048),
            DocumentVisibility.PUBLIC,
            DocumentType.IMAGE_JPEG);

    var result = objectUnderTest.toDocumentJpa(document);

    assertThat(result.getId()).isEqualTo(document.getId().value());
    assertThat(result.getName()).isEqualTo(document.getName().value());
    assertThat(result.getSize()).isEqualTo(document.getSize().value());
    assertThat(result.getVisibility()).isEqualTo(document.getVisibility().name());
    assertThat(result.getType()).isEqualTo(document.getType().name());
    assertThat(result.getStatus()).isEqualTo(document.getStatus().name());
    assertThat(result.getCreatedAt()).isEqualTo(document.getCreatedAt().value());
  }
}
