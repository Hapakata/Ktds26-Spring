package com.ktdsuniversity.edu.file.web;

import java.util.Map;
import java.util.Properties;
import com.ktdsuniversity.edu.file.util.MultipartFileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.ktdsuniversity.edu.file.service.FileService;

@Controller
public class FileController {

    private final MultipartFileHandler multipartFileHandler;

    @Autowired
    private FileService fileService;

    FileController(MultipartFileHandler multipartFileHandler) {
        this.multipartFileHandler = multipartFileHandler;
    }
    
    
    
    @GetMapping("/test")
    public String test() {
    	
    	Map<String,String> envMap = System.getenv();
    	Properties props = System.getProperties();
    	System.out.println(envMap);
    	System.out.println(props);
    	return "ddddddd+========";
    }

}
