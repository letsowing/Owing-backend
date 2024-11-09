package com.owing.api.story.service.folder;

import com.owing.api.common.util.MemberUtils;
import com.owing.api.dnd.folder.service.DeleteFolderUseCase;
import com.owing.common.annotation.UseCase;
import com.owing.core.dnd.base.service.BaseDndDomainService;
import com.owing.entity.domains.story.model.StoryFolder;

@UseCase
public class DeleteStoryFolderUseCase extends DeleteFolderUseCase<StoryFolder> {

    public DeleteStoryFolderUseCase(MemberUtils memberUtils, BaseDndDomainService<StoryFolder> baseDndDomainService) {
        super(memberUtils, baseDndDomainService);
    }
}
