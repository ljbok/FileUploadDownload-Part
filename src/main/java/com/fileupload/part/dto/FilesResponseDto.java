package com.fileupload.part.dto;

import com.fileupload.part.domain.Files;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class FilesResponseDto {

    private Long id;
    private String name;
    private String path;
    private Long domainId;
    private String domain;
    private boolean deleteTF;
    private LocalDateTime uploadDate;
    private Long size;

    @Builder
    public FilesResponseDto(Long id, String name, String path, Long domainId, String domain, boolean deleteTF, LocalDateTime uploadDate, Long size) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.domainId = domainId;
        this.domain = domain;
        this.deleteTF = deleteTF;
        this.uploadDate = uploadDate;
        this.size = size;
    }

    public FilesResponseDto toRespDto(Files files) {

        return FilesResponseDto.builder()
                .id(files.getId())
                .name(files.getName())
                .path(files.getPath())
                .domainId(files.getDomainId())
                .domain(files.getDomain())
                .deleteTF(files.isDeleteTF())
                .uploadDate(files.getUploadDate())
                .size(files.getSize())
                .build();
    }
}
