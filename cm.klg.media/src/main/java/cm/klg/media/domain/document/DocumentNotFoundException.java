package cm.klg.media.domain.document;

import static cm.klg.media.domain.exception.MediaErrorCode.MEDIA_404_001;

import cm.klg.common.base.exception.DomainException;

public class DocumentNotFoundException extends DomainException {
  public DocumentNotFoundException() {
    super(MEDIA_404_001);
  }
}
