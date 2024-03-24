package com.fileupload.part.service;

import com.fileupload.part.domain.Files;
import com.fileupload.part.domain.FilesRepository;
import com.fileupload.part.dto.FilesDto;
import com.fileupload.part.dto.FilesResponseDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import jakarta.transaction.Transactional;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FileServiceImpl implements FileService{

    private final FilesRepository filesRepository;

    public FileServiceImpl(FilesRepository filesRepository) {
        this.filesRepository = filesRepository;
    }

    //todo 다중, 단일 둘다 사용가능한 파일 업로드
    @Transactional
    @Override
    public List<FilesResponseDto> fileUpload(HttpServletRequest request) throws ServletException, IOException {

        List<FilesResponseDto> filesResponseDtoList = new ArrayList<>(); //todo 반환시켜줄 respDto 리스트 빈 객체

       Collection<Part> fileCollection = request.getParts(); //todo 폼의 모든 데이터들을 가지고 오겠다.
        for(Part filePart : fileCollection){
            if(!filePart.getName().equals("file")) continue;

            String fileName = filePart.getSubmittedFileName(); //todo 확장자명을 포함한 파일이름
            long fileSize = filePart.getSize();
            InputStream fis = filePart.getInputStream();

            // String realPath = request.getServletContext().getRealPath() //todo -> 자바가 지정한 파일이 저장될 경로 : 권장
            // String realPath = request.getServletContext().getRealPath("\\FileDownloads") //todo -> 자바가 지정한 파일이 저장될 경로 + "\\마지막 임의 경로 "

            String myPath = "C:\\Users\\wnsqh\\Desktop\\FileDownloads"; //todo 내가 지정한 임의 경로

            File folder = new File(myPath);
            if (!folder.exists()){
                try {
                    folder.mkdir(); //todo 파일을 저장할 경로에 디렉터리가 존재하지 않으면 생성하겠다.
                }catch (Exception e) {
                    e.getStackTrace();
                }
            }

            //todo 실제 이 파일의 파일명을 포함한 전체 경로가 db에 저장되어야 한다.
            String filePath = myPath + "\\" + UUID.randomUUID() + "-" +fileName; //todo 파일 중복으로 인한 덮어쓰기를 예방하기 위한 uuid 붙여줌.

            FileOutputStream fos = new FileOutputStream(filePath);

            byte[] buf = new byte[1024]; //todo 파일 처리할 byte 단위
            int size;
             //todo 단위로 연산한 파일 크기 읽어서 size 변수에 저장
            while ( (size = fis.read(buf)) != -1) {
                fos.write(buf, 0, size);
            }

            Files files = Files.builder()
                    .name(fileName)
                    .path(filePath)
                    .domainId(1L)
                    .domain("NOTICE")
                    .size(fileSize)
                    .build();

            files = filesRepository.save(files); //todo INSERT 쿼리문 DB에 전달 실행.
            FilesResponseDto filesResponseDto = new FilesResponseDto().toRespDto(files);

            filesResponseDtoList.add(filesResponseDto); //todo 마지막에 반환시켜줄 리스트에 하나씩 쌓는다 어떤 파일을 업로드 했는지 정보를

            fos.close();
            fis.close();

        } // end of for

        return  filesResponseDtoList;
    }

    // 파일 리스트 전체 출력
    @Override
    public List<FilesResponseDto> getAllFile() {
        if ( filesRepository.findAll().isEmpty() ) {
           throw new IllegalArgumentException("not existed any file");
        }
        FilesResponseDto filesResponseDto = new FilesResponseDto();

        return filesRepository.findAll().stream().map(files -> new FilesResponseDto().toRespDto(files))
                .collect(Collectors.toList());
    }

    @Override
    public FilesResponseDto getFile(String domain, Long domainId, Long fileId) {
        Files findFiles = filesRepository.findByDomainAndDomainIdAndId(domain, domainId, fileId).orElseThrow(() -> new IllegalArgumentException("not existed file"));
        log.info("findFiles -> {}", findFiles);
        return new FilesResponseDto().toRespDto(findFiles);
    }
}
