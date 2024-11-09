package com.owing.api.story.service.folder;

import com.owing.api.common.util.MemberUtils;
import com.owing.api.dnd.folder.model.mapper.BaseFolderMapper;
import com.owing.api.dnd.folder.service.UpdateFolderUseCase;
import com.owing.common.annotation.UseCase;
import com.owing.entity.dnd.base.service.BaseDndDomainService;
import com.owing.entity.domains.story.model.StoryFolder;

@UseCase
public class UpdateStoryFolderUseCase extends UpdateFolderUseCase<StoryFolder> {
    public UpdateStoryFolderUseCase(MemberUtils memberUtils, BaseDndDomainService<StoryFolder> baseDndDomainService,
        BaseFolderMapper<StoryFolder> dndMapper) {
        super(memberUtils, baseDndDomainService, dndMapper);
    }

}
