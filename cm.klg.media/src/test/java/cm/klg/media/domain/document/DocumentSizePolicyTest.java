package cm.klg.media.domain.document;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class DocumentSizePolicyTest {

  @Test
  void constructor_shouldThrow_whenMaxSizeIsNegative() {
    assertThatThrownBy(() -> new DocumentSizePolicy(-1))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void validate_shouldNotThrow_whenSizeIsWithinLimit() {
    var policy = new DocumentSizePolicy(1024);
    var size = DocumentSize.from(512);
    assertThatNoException().isThrownBy(() -> policy.validate(size));
  }

  @Test
  void validate_shouldNotThrow_whenSizeEqualsLimit() {
    var policy = new DocumentSizePolicy(1024);
    var size = DocumentSize.from(1024);
    assertThatNoException().isThrownBy(() -> policy.validate(size));
  }

  @Test
  void validate_shouldThrow_whenSizeExceedsLimit() {
    var policy = new DocumentSizePolicy(1024);
    var size = DocumentSize.from(2048);
    assertThatThrownBy(() -> policy.validate(size))
        .isInstanceOf(DocumentSizeLimitExceededException.class);
  }
}
