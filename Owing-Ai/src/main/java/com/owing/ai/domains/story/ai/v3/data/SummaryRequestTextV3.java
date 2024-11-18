package com.owing.ai.domains.story.ai.v3.data;

import com.owing.ai.domains.story.ai.v2.model.entity.StoryInfoType;

public class SummaryRequestTextV3 {
	public static String defaultSystemTest = """
		당신은 뛰어난 내용 분석요약기입니다.
		title에는 요약할 내용의 제목을, analysis에는 요약할 내용을 입력해주세요. analysis는 json을 string 형식으로 입력해주세요.
		""";

	public static String universeSummary = """
		세계관의 핵심 정보를 추출하여 간결하고 명확하게 요약하세요.
		예시) 기본 설정, 배경, 규칙 등
		""";

	public static String characterSummary = """
		캐릭터의 핵심 정보를 추출하여 간결하고 명확하게 요약하세요.
		예시) 이름, 성격, 목표, 행동 패턴, 동기 등
		""";

	public static String projectSummary = """
		작품의 제목, 장르, 주제 등을 간결하고 명확하게 요약하세요.
		""";

	public static String storyUniverseSummary = """
		원고에서 세계관의 배경, 규칙, 주요 설정을 추출하여 간결하고 명확하게 요약하세요. 
		""";
	public static String storyCastSummary = """
		원고에서 인물 간의 관계를 추출하세요. 관계의 성격(협력, 적대, 연대 등)과 변화가 적절히 설명되어야 합니다.
	 	관계: (캐릭터 A ↔ 캐릭터 B)
	   	관계 성격: (우정, 적대, 협력 등)
	   	관계의 변화: (관계가 변한 경우 그 변화의 이유와 과정)
		""";

	public static String storyPlotSummary = """
		원고에서 사건의 시간적 흐름과 인과관계를 명확히 추출하세요. 사건 순서와 결과가 논리적으로 연결되어야 합니다.
		- 사건 이름
		  시간: (사건이 발생한 시점)
		  내용: (사건의 주요 내용)
		  결과: (사건의 직접적인 결과)
		""";

	public static String getTemplate(StoryInfoType type) {
		return switch (type) {
			case UNIVERSE -> defaultSystemTest + universeSummary;
			case CAST -> defaultSystemTest + characterSummary;
			case PROJECT -> defaultSystemTest + projectSummary;
			case STORY -> defaultSystemTest + storyUniverseSummary + storyCastSummary + storyPlotSummary;
			default -> "";
		};
	}
}
