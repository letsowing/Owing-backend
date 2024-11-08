package com.owing.api.story.service.folder;

import com.owing.api.dnd.folder.model.mapper.BaseFolderMapper;
import com.owing.api.dnd.folder.service.ReadFolderUseCase;
import com.owing.common.annotation.UseCase;
import com.owing.entity.dnd.base.service.DndDomainService;
import com.owing.entity.domains.story.model.StoryFolder;

@UseCase
public class ReadStoryFolderUseCase extends ReadFolderUseCase<StoryFolder> {
    public ReadStoryFolderUseCase(BaseFolderMapper<StoryFolder> dndMapper,
        DndDomainService<StoryFolder> dndDomainService) {
        super(dndMapper, dndDomainService);
    }
}
