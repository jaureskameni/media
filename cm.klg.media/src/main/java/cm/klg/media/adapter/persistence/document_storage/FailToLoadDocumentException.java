package cm.klg.media.adapter.persistence.document_storage;

import static cm.klg.media.domain.exception.MediaErrorCode.MEDIA_500_003;

import cm.klg.common.base.exception.DomainException;

public class FailToLoadDocumentException extends DomainException {
  public FailToLoadDocumentException() {
    super(MEDIA_500_003);
  }
}
