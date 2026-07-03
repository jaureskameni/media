package cm.klg.media.domain.document;

import static cm.klg.media.domain.exception.MediaErrorCode.MEDIA_400_003;

import cm.klg.common.base.exception.DomainException;

public class DocumentNameCannotBeBlankException extends DomainException {
  public DocumentNameCannotBeBlankException() {
    super(MEDIA_400_003);
  }
}
