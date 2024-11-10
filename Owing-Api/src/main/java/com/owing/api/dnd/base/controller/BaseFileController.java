package com.owing.api.dnd.base.controller;

import com.owing.api.dnd.file.model.dto.request.AddFileRequest;
import com.owing.api.dnd.file.model.dto.request.UpdateFilePositionRequest;
import com.owing.api.dnd.file.model.dto.request.UpdateFileRequest;

public abstract class BaseFileController extends DndController<AddFileRequest, UpdateFileRequest, UpdateFilePositionRequest>{
}
