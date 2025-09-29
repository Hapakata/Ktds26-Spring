package com.ktdsuniversity.edu.file.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ktdsuniversity.edu.file.service.FileService;
import com.ktdsuniversity.edu.file.util.MultipartFileHandler;
import com.ktdsuniversity.edu.file.vo.FileVO;
import com.ktdsuniversity.edu.file.vo.RequestDownloadVO;

@Controller
public class FileController {

    private final MultipartFileHandler multipartFileHandler;

    @Autowired
    private FileService fileService;

    FileController(MultipartFileHandler multipartFileHandler) {
        this.multipartFileHandler = multipartFileHandler;
    }
    
    
    
    @GetMapping("/file/{boardId}/{fileGroupId}/{fileId}")
    public ResponseEntity<Resource> doDownlloadAction(@PathVariable("boardId") String boardId,
    								@PathVariable("fileGroupId") String fileGroupId,
    								@PathVariable("fileId") String fileId) {
    	RequestDownloadVO requestDownloadVO = new RequestDownloadVO();
    	requestDownloadVO.setBoardId(boardId);
    	requestDownloadVO.setFileGroupId(fileGroupId);
    	requestDownloadVO.setFileId(fileId);
    	
    	FileVO downloadFile = this.fileService.readFileVO(requestDownloadVO);
    	
    	String filename = downloadFile.getFileDisplayName();
    	long fileSize = downloadFile.getFileSize();
    	String filePath = downloadFile.getFilePath();
    	String mimeType = downloadFile.getFileType();
    	
    	try {
			filename = URLEncoder.encode(filename, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			System.out.println("한글변환중 오류!!");
		}
    	
    	// Download 시작
    	File file = new File(filePath);
    	
    	// Http Response Headers
    	// File 을 다운로드 한다는 정보를 작성
    	HttpHeaders header = new HttpHeaders();
    	// 다운로드할 파일의 이름은 무엇?
    	header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);
    	// 다운로드할 파일의 타입은 무엇?
    	header.add(HttpHeaders.CONTENT_TYPE, mimeType);
    	// 다운로드할 파일의 크기는 얼마?
    	header.add(HttpHeaders.CONTENT_LENGTH, fileSize + "");
    	
    	// Java 에서 브라우저로 파일을 전송
    	InputStreamResource downloadResource = null;
    	try {
			downloadResource = new InputStreamResource( new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	
    	return ResponseEntity.ok()
    						 .headers(header)
    						 .body(downloadResource);
    }
}
