package cm.klg.media.adapter.configurations;

import static org.assertj.core.api.Assertions.assertThat;

import cm.klg.common.base.exception.BadRequestException;
import cm.klg.common.base.exception.InternalException;
import cm.klg.media.domain.document.DocumentNameCannotBeBlankException;
import cm.klg.media.domain.document.DocumentSizeCannotBeNegativeException;
import cm.klg.media.domain.document.DocumentSizeLimitExceededException;
import org.junit.jupiter.api.Test;

class DefaultDomainToHttpExceptionTranslatorTest {

  private final DefaultDomainToHttpExceptionTranslator objectUnderTest =
      new DefaultDomainToHttpExceptionTranslator();

  @Test
  void translate_shouldReturnBadRequest_whenDocumentNameCannotBeBlank() {
    var result = objectUnderTest.translate(new DocumentNameCannotBeBlankException());
    assertThat(result).isInstanceOf(BadRequestException.class);
  }

  @Test
  void translate_shouldReturnBadRequest_whenDocumentSizeCannotBeNegative() {
    var result = objectUnderTest.translate(new DocumentSizeCannotBeNegativeException());
    assertThat(result).isInstanceOf(BadRequestException.class);
  }

  @Test
  void translate_shouldReturnBadRequest_whenDocumentSizeLimitExceeded() {
    var result = objectUnderTest.translate(new DocumentSizeLimitExceededException());
    assertThat(result).isInstanceOf(BadRequestException.class);
  }

  @Test
  void translate_shouldReturnInternalException_whenUnknownException() {
    var result = objectUnderTest.translate(new RuntimeException("unknown"));
    assertThat(result).isInstanceOf(InternalException.class);
  }
}
