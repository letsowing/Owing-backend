package com.owing.entity.domains.member.error.exception;

import com.owing.common.error.exception.OwingException;
import com.owing.entity.domains.member.error.RefreshTokenErrorCode;

public class RefreshTokenException extends OwingException {

  private RefreshTokenException(RefreshTokenErrorCode errorCode, String description) {
    super(errorCode, description);
  }

  public static RefreshTokenException of(RefreshTokenErrorCode errorCode) {
    return new RefreshTokenException(errorCode, null);
  }

  public static RefreshTokenException of(RefreshTokenErrorCode errorCode, String description) {
    return new RefreshTokenException(errorCode, description);
  }
}
