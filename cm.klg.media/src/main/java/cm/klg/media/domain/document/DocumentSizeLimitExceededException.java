package cm.klg.media.domain.document;

import static cm.klg.media.domain.exception.MediaErrorCode.MEDIA_400_001;

import cm.klg.common.base.exception.DomainException;

public class DocumentSizeLimitExceededException extends DomainException {
  public DocumentSizeLimitExceededException() {
    super(MEDIA_400_001);
  }
}
