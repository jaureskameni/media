package cm.klg.media.domain.document;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class DocumentSizeTest {

  @Test
  void from_shouldCreateDocumentSize_whenValueIsValid() {
    var size = DocumentSize.from(1024);
    assertThat(size.value()).isEqualTo(1024);
  }

  @Test
  void from_shouldCreateDocumentSize_whenValueIsZero() {
    var size = DocumentSize.from(0);
    assertThat(size.value()).isZero();
  }

  @Test
  void constructor_shouldThrow_whenValueIsNegative() {
    assertThatThrownBy(() -> new DocumentSize(-1))
        .isInstanceOf(DocumentSizeCannotBeNegativeException.class);
  }

  @Test
  void from_shouldReturnSameValue() {
    var size1 = DocumentSize.from(2048);
    var size2 = DocumentSize.from(2048);
    assertThat(size1).isEqualTo(size2);
    assertThat(size1.hashCode()).hasSameHashCodeAs(size2.hashCode());
  }
}
