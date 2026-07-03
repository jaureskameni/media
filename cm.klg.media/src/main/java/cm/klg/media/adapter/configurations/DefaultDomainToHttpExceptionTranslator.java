package cm.klg.media.adapter.configurations;

import cm.klg.common.base.exception.BadRequestException;
import cm.klg.common.base.exception.HttpErrorException;
import cm.klg.common.base.exception.InternalException;
import cm.klg.common.base.exception.ResourceNotFoundException;
import cm.klg.common.base.transaction.DomainToHttpExceptionTranslator;
import cm.klg.media.domain.document.DocumentNameCannotBeBlankException;
import cm.klg.media.domain.document.DocumentNotFoundException;
import cm.klg.media.domain.document.DocumentSizeCannotBeNegativeException;
import cm.klg.media.domain.document.DocumentSizeLimitExceededException;
import java.util.Optional;

public record DefaultDomainToHttpExceptionTranslator() implements DomainToHttpExceptionTranslator {
  @Override
  public HttpErrorException translate(RuntimeException ex) {
    var message = Optional.ofNullable(ex.getMessage()).orElse("missing error code");
    return switch (ex) {
      case DocumentNameCannotBeBlankException _,
          DocumentSizeLimitExceededException _,
          DocumentSizeCannotBeNegativeException _ ->
          new BadRequestException(message, ex);
      case DocumentNotFoundException _ -> new ResourceNotFoundException(message, ex);
      default -> new InternalException(message, ex);
    };
  }
}
