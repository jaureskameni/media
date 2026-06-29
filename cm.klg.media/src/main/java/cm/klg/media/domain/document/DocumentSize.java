package cm.klg.media.domain.document;

public record DocumentSize(long value) {
  public DocumentSize {
    if (value < 0) {
      throw new DocumentSizeCannotBeNegativeException();
    }
  }

  public static DocumentSize from(long value) {
    return new DocumentSize(value);
  }
}
