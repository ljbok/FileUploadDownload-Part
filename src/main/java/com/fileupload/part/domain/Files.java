package com.fileupload.part.domain;

import com.fileupload.part.dto.FilesDto;
import jakarta.persistence.*;
import jakarta.servlet.http.Part;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@ToString
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Files {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "path")
    private String path;

    @Column(name = "domain_id")
    private Long domainId; //todo 여러 테이블에서 이 첨부파일 테이블을 함께 사용할 경우 비참조로 임의로 넣어줘야함.

    @Column(name = "domain")
    private String domain; //todo 어떤 게시물 종류에 첨부파일들인지

    @Column(name = "deleteTF")
    @ColumnDefault("0") // default
    private boolean deleteTF; // todo 1, 0 --> 0이면 미삭제 // 1이면 삭제 default 0임.

    @CreationTimestamp
    @Column(name = "upload_date")
    private LocalDateTime uploadDate; //todo 저장된 날짜.

    @Column(name = "size")
    private Long size;

    @Builder
    public Files(String name, String path, Long domainId, String domain, Long size) {
        this.name = name;
        this.path = path;
        this.domainId = domainId;
        this.domain = domain;
        this.size = size;
    }
    
    //todo dto를 entity로 바꿔주는 메소드
    public static Files toEntity(FilesDto filesDto){
        return Files.builder()
                .name(filesDto.getName())
                .path(filesDto.getPath())
                .domainId(filesDto.getDomainId())
                .domain(filesDto.getDomain())
                .size(filesDto.getSize())
                .build();
    }
}
