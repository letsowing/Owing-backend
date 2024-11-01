package com.owing.common.error.code;

import com.owing.common.constant.OwingHttpStatus;

public interface OwingErrorCode{
	OwingHttpStatus getStatus();
	String getCode();
	String getMessage();
}
