package com.fileupload.part;

import com.fileupload.part.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("api/filelist")
public class IndexController {

    private final FileService fileService;

    @GetMapping
    public ModelAndView fileListPage(ModelAndView mav){
        mav.setViewName("index");
        mav.addObject("fileList", fileService.getAllFile());

        return mav;
    }
}
