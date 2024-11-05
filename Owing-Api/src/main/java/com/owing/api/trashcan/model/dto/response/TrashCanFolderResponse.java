package com.owing.api.trashcan.model.dto.response;

import java.util.List;

import com.owing.api.trashcan.model.dto.Folder;

public record TrashCanFolderResponse(List<Folder> story, List<Folder> cast, List<Folder> universe) {
}
