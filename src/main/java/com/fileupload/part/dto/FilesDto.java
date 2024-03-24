package com.fileupload.part.dto;

import jakarta.servlet.http.Part;
import lombok.Getter;
import lombok.NoArgsConstructor;

//todo requestDto 안 쓰는 파일
@Getter
@NoArgsConstructor
public class FilesDto {

    String name;
    String path;
    Long domainId;
    String domain;
    Long size;
}
