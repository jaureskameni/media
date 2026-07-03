package cm.klg.media.domain.document;

import static cm.klg.media.domain.exception.MediaErrorCode.MEDIA_400_002;

import cm.klg.common.base.exception.DomainException;

public class DocumentSizeCannotBeNegativeException extends DomainException {
  public DocumentSizeCannotBeNegativeException() {
    super(MEDIA_400_002);
  }
}
