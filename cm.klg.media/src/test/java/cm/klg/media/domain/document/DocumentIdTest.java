package cm.klg.media.domain.document;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.UUID;
import org.junit.jupiter.api.Test;

class DocumentIdTest {

  @Test
  void generate_shouldCreateNonNullId() {
    var id = DocumentId.generate();
    assertThat(id.value()).isNotNull();
  }

  @Test
  void generate_shouldCreateUniqueIds() {
    var id1 = DocumentId.generate();
    var id2 = DocumentId.generate();
    assertThat(id1.value()).isNotEqualTo(id2.value());
  }

  @Test
  void from_shouldCreateDocumentIdWithGivenUuid() {
    var uuid = UUID.randomUUID();
    var id = DocumentId.from(uuid);
    assertThat(id.value()).isEqualTo(uuid);
  }

  @Test
  void constructor_shouldThrow_whenValueIsNull() {
    assertThatThrownBy(() -> new DocumentId(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessage("Document id is required");
  }
}
