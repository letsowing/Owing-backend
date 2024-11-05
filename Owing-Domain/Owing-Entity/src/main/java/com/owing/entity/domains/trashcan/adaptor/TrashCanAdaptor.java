package com.owing.entity.domains.trashcan.adaptor;

import org.springframework.stereotype.Component;

import com.owing.entity.domains.member.error.MemberErrorCode;
import com.owing.entity.domains.member.error.exception.MemberException;
import com.owing.entity.domains.member.model.Member;
import com.owing.entity.domains.member.repository.MemberRepository;
import com.owing.entity.domains.trashcan.error.TrashCanErrorCode;
import com.owing.entity.domains.trashcan.error.exception.TrashCanException;
import com.owing.entity.domains.trashcan.model.TrashCan;
import com.owing.entity.domains.trashcan.repository.TrashCanRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TrashCanAdaptor {
    private final TrashCanRepository trashCanRepository;

    public TrashCan findById(Long trashId) {
        return trashCanRepository.findById(trashId).orElseThrow(() -> TrashCanException.of(TrashCanErrorCode.TRASH_CAN_NOT_FOUND,
            String.format("요청된 Trash Id: %d", trashId)));
    }
}
