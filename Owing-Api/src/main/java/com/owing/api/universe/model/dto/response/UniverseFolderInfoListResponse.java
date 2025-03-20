package com.owing.api.universe.model.dto.response;

import java.util.List;

import com.owing.api.dnd.dto.response.FolderInfoListResponse;
import com.owing.entity.domains.universe.model.UniverseFolder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UniverseFolderInfoListResponse implements FolderInfoListResponse {
	private List<UniverseFolderInfoResponse> folderList;

	public static UniverseFolderInfoListResponse from(List<UniverseFolder> folders) {
		return UniverseFolderInfoListResponse.builder()
			.folderList(folders.stream()
			.map(UniverseFolderInfoResponse::from)
			.toList())
			.build();
	}
}