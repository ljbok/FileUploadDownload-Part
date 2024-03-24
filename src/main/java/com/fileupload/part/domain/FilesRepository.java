package com.fileupload.part.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.io.File;
import java.util.List;
import java.util.Optional;

public interface FilesRepository extends JpaRepository<Files, Long> {

    // domain과 domainId로 파일 정보 가지고 오기
    Optional<Files> findByDomainAndDomainIdAndId(String domain, Long domainId, Long fileId);
}
