package cm.klg.media.domain.document;

public record DocumentSizePolicy(long maxSizeInBytes) {
  public DocumentSizePolicy {
    if (maxSizeInBytes < 0) {
      throw new IllegalArgumentException("maxSizeInBytes must not be negative");
    }
  }

  public void validate(DocumentSize size) {
    if (size.value() > maxSizeInBytes) {
      throw new DocumentSizeLimitExceededException();
    }
  }
}
