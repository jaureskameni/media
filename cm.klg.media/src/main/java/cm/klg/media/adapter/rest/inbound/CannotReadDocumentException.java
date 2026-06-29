package cm.klg.media.adapter.rest.inbound;

import static cm.klg.media.domain.exception.MediaErrorCode.MEDIA_500_002;

import cm.klg.common.base.exception.DomainException;

public class CannotReadDocumentException extends DomainException {
  public CannotReadDocumentException() {
    super(MEDIA_500_002);
  }
}
