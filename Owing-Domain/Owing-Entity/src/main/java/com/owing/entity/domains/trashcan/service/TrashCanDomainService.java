package com.owing.entity.domains.trashcan.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.owing.entity.folders.trashcan.adaptor.TrashCanFolderAdaptor;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TrashCanDomainService {

	private final TrashCanFolderAdaptor trashCanAdaptor;
}
