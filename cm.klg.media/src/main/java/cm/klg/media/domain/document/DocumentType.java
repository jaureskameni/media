package cm.klg.media.domain.document;

public enum DocumentType {
  IMAGE_JPEG("image/jpeg"),
  IMAGE_JPG("image/jpg"),
  IMAGE_PNG("image/png");

  private final String mimeType;

  DocumentType(String mimeType) {
    this.mimeType = mimeType;
  }

  public String mimeType() {
    return mimeType;
  }
}
