package com.owing.ai.domains.image.utils;

import com.owing.ai.domains.image.dto.request.GenerateUniverseImageRequest;

public class PromptUtils {

	/**
	 * UniverseFileRequestDto 를 기반으로 작품 세계관 일러스트레이션 이미지를 위한 프롬프트를 생성하는 메서드
	 *
	 * @param imageGenerateRequest 작품 정보가 담긴 DTO
	 * @return 생성된 작품 세계관 일러스트레이션 프롬프트
	 */
	public static String createPrompt(GenerateUniverseImageRequest imageGenerateRequest) {

		return "A lively and playful scene of a cheerful golden retriever puppy and a mischievous tabby kitten playing together in a sunny backyard. The puppy is mid-pounce, with its floppy ears bouncing and a joyful expression on its face, while the kitten leaps gracefully, swiping a paw toward the puppy's wagging tail. The grass is lush and dotted with colorful flowers, and a few fallen leaves add an autumnal touch. In the background, there’s a wooden fence, a small garden bench, and a few scattered toys like a rubber ball and a yarn ball. The sunlight creates soft, warm highlights on the animals' fur, and their dynamic poses capture the energy of their playful interaction. The style is vibrant and realistic, emphasizing their fur textures and the motion of the scene.";

		// return String.format(
		// 	"다음 정보에 따라 작품의 표지 일러스트레이션 이미지를 만드세요. 작품의 분위기와 주요 내용을 시각적으로 표현해야 합니다. 다음은 작품의 정보입니다 " +
		// 		"작품 제목: [%s] \n" +
		// 		"작품 설명: [%s] \n" +
		// 		"이미지는 캐릭터, 배경, 주요 사건, 또는 작품의 분위기를 시각적으로 나타내야 합니다. \n" +
		// 		"이미지 스타일: 전반적으로 현실적이면서도 작품의 장르와 분류에 맞는 예술적 디테일을 적용하세요. \n" +
		// 		"이미지는 주로 밝고 선명한 색감을 사용하되, 장르나 분류에 따라 어두운 색상도 적절히 혼합하세요. \n\n" +
		// 		"이미지는 하나이고, 제작된 표지 이미지는 독자의 관심을 끌 수 있도록 세밀하고 몰입감 있게 표현해주세요.",
		// 	universeSaveRequest.getName(),
		// 	universeSaveRequest.getDescription()
		// );

	}

}
