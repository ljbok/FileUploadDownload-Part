package com.fileupload.part.service;

import com.fileupload.part.dto.FilesDto;
import com.fileupload.part.dto.FilesResponseDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.List;

public interface FileService {

    public List<FilesResponseDto> fileUpload(HttpServletRequest request) throws ServletException, IOException;

    // 전체 파일 리스트 출력
    public List<FilesResponseDto> getAllFile();

    // 파일 다운로드를 위해 파일 정보 가지고오는 메소드
    public FilesResponseDto getFile(String domain, Long domainId, Long fileId);
}
