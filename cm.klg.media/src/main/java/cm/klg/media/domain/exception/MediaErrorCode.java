package cm.klg.media.domain.exception;

import cm.klg.common.base.utils.ErrorCode;
import lombok.Getter;

public enum MediaErrorCode implements ErrorCode {
  // ERROR-400
  MEDIA_400_001("MEDIA-400-001", "Document Size Limit Exceeded"),
  MEDIA_400_002("MEDIA-400-002", "Document Size Cannot Be Negative"),
  MEDIA_400_003("MEDIA-400-003", "Document Name Cannot Be Blank"),

  // ERROR-404
  MEDIA_404_001("MEDIA-404-001", "Document Not Found"),

  // ERROR-500
  MEDIA_500_001("MEDIA-500-001", "Fail to Store Document"),
  MEDIA_500_002("MEDIA-500-002", "Cannot Read Document File From Request"),
  MEDIA_500_003("MEDIA-500-003", "Fail to Load Document From Storage");

  private final String value;
  @Getter private final String description;

  MediaErrorCode(String value, String description) {
    this.value = value;
    this.description = description;
  }

  public String value() {
    return value;
  }
}
