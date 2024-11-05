package com.owing.api.trashcan.model.dto;

import java.util.List;

public record Folder(Long id, String name, String description, List<File> files) {
}
