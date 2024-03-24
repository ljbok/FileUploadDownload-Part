package com.fileupload.part.controller;

import com.fileupload.part.dto.FilesDto;
import com.fileupload.part.dto.FilesResponseDto;
import com.fileupload.part.service.FileService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.util.List;



//todo 파일업로드할 때 경로, 처리 사이즈 단위, ,저장 가능 파일 사이즈 , 요청할 수 파일 사이즈 최대
@MultipartConfig(
        //location = "C:/User/fakeuser/file" //todo 다른 os에서는 디폴트 경로가 c가 아닐 수도 있으므로 비권장
        fileSizeThreshold = 1024*1024, //todo 아마 1mb 일 거임 검색 ㄱㄱ
        maxFileSize = 1024*1024*50, //todo 50mb
        maxRequestSize = 1024*1024*50 //todo 50mb
)

@Slf4j
@RestController
@RequestMapping("/api/file")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("upload")
    public List<FilesResponseDto> fileUpload(HttpServletRequest request) throws ServletException, IOException {
        //todo
        // 파일업로드하는 서비스 메소드 실행
        return fileService.fileUpload(request);
    }

    @GetMapping("download")
    public void fileDownload(@RequestParam("domain") String domain,
                             @RequestParam("domainId") String domainId,
                             @RequestParam("fileId") String fileId,
                             HttpServletResponse response) throws IOException {
        //todo
        //파일다운로드 하는 서비스 메소드 실행
        FilesResponseDto filesInfo = fileService.getFile(domain.toUpperCase(), Long.parseLong(domainId), Long.parseLong(fileId));
        try{
            byte[] files = FileUtils.readFileToByteArray(new File(filesInfo.getPath()));

            Blob blob = new javax.sql.rowset.serial.SerialBlob(files);

            response.setContentType("application/octet-stream");
            response.setContentLength(files.length);
            response.setHeader("Content-Disposition","attachment; fileName=\""+ URLEncoder.encode(filesInfo.getName(), StandardCharsets.UTF_8)+"\";");
            response.setHeader("Content-Transfer-Encoding","binary");
            log.info(response.toString());

            response.getOutputStream().write(files);
            response.getOutputStream().flush();

        } catch (Exception e) {
            log.error(e.getMessage());
            e.getStackTrace();
        }

        response.getOutputStream().close();
    }

}
