package com.owing.entity.domains.member.model;

import lombok.Getter;

@Getter
public enum OauthProvider {
	GOOGLE, NAVER, KAKAO;

	public static boolean isGoogle(OauthProvider provider) {
		return provider.equals(OauthProvider.GOOGLE);
	}
}
