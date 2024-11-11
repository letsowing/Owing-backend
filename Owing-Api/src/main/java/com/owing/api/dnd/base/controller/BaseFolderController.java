package com.owing.api.dnd.base.controller;

import com.owing.api.dnd.folder.model.dto.request.AddFolderRequest;
import com.owing.api.dnd.folder.model.dto.request.UpdateFolderPositionRequest;
import com.owing.api.dnd.folder.model.dto.request.UpdateFolderTitleRequest;

public abstract class BaseFolderController extends DndController<AddFolderRequest, UpdateFolderTitleRequest, UpdateFolderPositionRequest>{
}
