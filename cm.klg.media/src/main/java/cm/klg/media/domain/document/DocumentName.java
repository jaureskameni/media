package cm.klg.media.domain.document;

public record DocumentName(String value) {
  public DocumentName {
    if (value == null || value.isBlank()) {
      throw new DocumentNameCannotBeBlankException();
    }
  }

  public static DocumentName from(String value) {
    return new DocumentName(value);
  }
}
