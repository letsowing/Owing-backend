package com.owing.api.common.constant;

public interface TokenConst {

    String GOOGLE_CLAIM_EMAIL = "email";
    String GOOGLE_CLAIM_NAME = "name";
    String GOOGLE_CLAIM_PICTURE = "picture";
    String CLAIM_NICKNAME = "nickname";
    String CLAIM_PROFILE_URL = "profileUrl";
    String BEARER_TYPE = "Bearer";

    long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30;
    long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 14;
}
