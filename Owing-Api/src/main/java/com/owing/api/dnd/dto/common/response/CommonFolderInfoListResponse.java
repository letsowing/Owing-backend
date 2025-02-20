package com.owing.api.dnd.dto.common.response;

import java.util.List;

import com.owing.api.dnd.dto.response.FolderInfoListResponse;
import com.owing.core.dnd.model.DndFolder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonFolderInfoListResponse implements FolderInfoListResponse {
	private List<CommonFolderInfoResponse> folderList;

	public static <T extends DndFolder> CommonFolderInfoListResponse from(List<T> folders) {
        return CommonFolderInfoListResponse.builder()
            .folderList(folders.stream()
			.map(CommonFolderInfoResponse::from)
			.toList())
            .build();
    }
}
