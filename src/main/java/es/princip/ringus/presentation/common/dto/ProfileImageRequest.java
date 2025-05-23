package es.princip.ringus.presentation.common.dto;

import es.princip.ringus.infra.storage.domain.ProfileImage;
import jakarta.validation.constraints.NotBlank;

public record ProfileImageRequest(
        String fileName,
        String filePath
) {
    public ProfileImage toEntity() {
        return ProfileImage.builder()
                .fileName(fileName)
                .filePath(filePath)
                .build();
    }
}