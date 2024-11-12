package com.owing.api.story.service.story;

import com.owing.api.common.util.MemberUtils;
import com.owing.api.dnd.file.service.DeleteFileUseCase;
import com.owing.api.trashcan.model.mapper.TrashCanMapper;
import com.owing.common.annotation.UseCase;
import com.owing.core.dnd.base.service.BaseDndDomainService;
import com.owing.entity.domains.story.model.Story;
import com.owing.entity.domains.story.model.StoryFolder;
import com.owing.entity.domains.trashcan.service.TrashCanDomainService;

import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class DeleteStoryUseCase extends DeleteFileUseCase<Story, StoryFolder> {

    private final MemberUtils memberUtils;
    private final BaseDndDomainService<Story> baseDndDomainService;
    private final TrashCanDomainService trashCanDomainService;
    private final TrashCanMapper trashCanMapper;

    @Override
    protected MemberUtils memberUtils() {
        return memberUtils;
    }

    @Override
    protected BaseDndDomainService<Story> baseDndDomainService() {
        return baseDndDomainService;
    }

    @Override
    protected TrashCanDomainService trashCanDomainService() {
        return trashCanDomainService;
    }

    @Override
    protected TrashCanMapper trashCanMapper() {
        return trashCanMapper;
    }
}
