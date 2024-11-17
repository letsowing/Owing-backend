package com.owing.entity.domains.trashcan.model;

import java.util.List;

public record Folder(Long id, String name, String description, List<File> files) {
}
