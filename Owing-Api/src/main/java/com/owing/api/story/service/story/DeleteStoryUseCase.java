package com.owing.api.story.service.story;

import com.owing.api.common.util.MemberUtils;
import com.owing.api.dnd.file.service.DeleteFileUseCase;
import com.owing.common.annotation.UseCase;
import com.owing.core.dnd.base.service.BaseDndDomainService;
import com.owing.entity.domains.story.model.Story;
import com.owing.entity.domains.story.model.StoryFolder;

@UseCase
public class DeleteStoryUseCase extends DeleteFileUseCase<Story, StoryFolder> {

    public DeleteStoryUseCase(MemberUtils memberUtils, BaseDndDomainService<Story> baseDndDomainService) {
        super(memberUtils, baseDndDomainService);
    }
}
