package cm.klg.media.adapter.persistence.document_storage;

import static cm.klg.media.domain.exception.MediaErrorCode.MEDIA_500_001;

import cm.klg.common.base.exception.DomainException;

public class FailToStoreDocumentException extends DomainException {
  public FailToStoreDocumentException() {
    super(MEDIA_500_001);
  }
}
