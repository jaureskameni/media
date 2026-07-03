package cm.klg.media.domain.document;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class DocumentNameTest {

  @Test
  void from_shouldCreateDocumentName_whenValueIsValid() {
    var name = DocumentName.from("photo.jpg");
    assertThat(name.value()).isEqualTo("photo.jpg");
  }

  @Test
  void constructor_shouldThrow_whenValueIsNull() {
    assertThatThrownBy(() -> new DocumentName(null))
        .isInstanceOf(DocumentNameCannotBeBlankException.class);
  }

  @Test
  void constructor_shouldThrow_whenValueIsBlank() {
    assertThatThrownBy(() -> new DocumentName("  "))
        .isInstanceOf(DocumentNameCannotBeBlankException.class);
  }

  @Test
  void from_shouldReturnSameValue() {
    var name1 = DocumentName.from("doc.pdf");
    var name2 = DocumentName.from("doc.pdf");
    assertThat(name1).isEqualTo(name2);
    assertThat(name1.hashCode()).hasSameHashCodeAs(name2.hashCode());
  }
}
