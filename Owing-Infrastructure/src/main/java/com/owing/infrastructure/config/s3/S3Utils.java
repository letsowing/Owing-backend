package com.owing.infrastructure.config.s3;

/* 파일 이름을 설정해주는 클래스 */
public class S3Utils {
	public static final String FILE_EXTENSION_SEPARATOR = ".";

	public static String getFileName(String originalFileName) {
		int fileExtensionIndex = originalFileName.lastIndexOf(FILE_EXTENSION_SEPARATOR);
		return originalFileName.substring(0, fileExtensionIndex); //파일 이름
	}

	public static String buildFileName(String originalFileName) {
		int fileExtensionIndex = originalFileName.lastIndexOf(FILE_EXTENSION_SEPARATOR); //파일 확장자 구분선
		String fileExtension = originalFileName.substring(fileExtensionIndex); //파일 확장자
		String fileName = originalFileName.substring(0, fileExtensionIndex); //파일 이름
		String now = String.valueOf(System.currentTimeMillis()); //파일 업로드 시간

		return fileName + "_" + now + fileExtension;
	}
}

