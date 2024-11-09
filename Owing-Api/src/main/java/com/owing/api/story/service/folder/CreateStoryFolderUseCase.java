package com.owing.api.story.service.folder;

import com.owing.api.common.util.MemberUtils;
import com.owing.api.dnd.folder.model.mapper.BaseFolderMapper;
import com.owing.api.dnd.folder.service.CreateFolderUseCase;
import com.owing.common.annotation.UseCase;
import com.owing.core.dnd.base.service.BaseDndDomainService;
import com.owing.entity.domains.story.model.StoryFolder;

@UseCase
public class CreateStoryFolderUseCase extends CreateFolderUseCase<StoryFolder> {

    public CreateStoryFolderUseCase(MemberUtils memberUtils, BaseDndDomainService<StoryFolder> baseDndDomainService,
        BaseFolderMapper<StoryFolder> dndMapper) {
        super(memberUtils, baseDndDomainService, dndMapper);
    }
}
