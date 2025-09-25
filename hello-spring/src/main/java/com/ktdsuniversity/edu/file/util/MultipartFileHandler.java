package com.ktdsuniversity.edu.file.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.ktdsuniversity.edu.file.vo.FileVO;

/**
 * Bean Container 에 담긴 객체는
 * yml 의 설정을 참조받을 수 있다 == yml 의 설정을 읽어올 수 있다.
 */
@Component
public class MultipartFileHandler {
	
	// application.yml 의 설정을 읽어온다
	@Value("${app.multipart.whitelist}") // SpEL 라고 부름
	private List<String> whitelist;
	
	@Value("${app.multipart.base-dir.windows}")
	private String windowsBaseDirectory;
	
	@Value("${app.multipart.base-dir.macos}")
	private String macosBaseDirectory;
	
	@Value("${app.multipart.obfuscation.enable}")
	private boolean obfuscationEnable;
	
	@Value("${app.multipart.obfuscation.hide-ext.enable}")
	private boolean hideExtEnable;
	
	private Tika tika;
	
	public MultipartFileHandler() {
		this.tika = new Tika();
	}
	
	public List<FileVO> upload(List<MultipartFile> file){
		if(file == null) {
			return null;
		}
		
		List<FileVO> uploadResultList = new ArrayList<>();
		for(MultipartFile uploadFile: file) {
			FileVO uploadResult = this.upload(uploadFile);
			if(uploadResult != null) {
				uploadResultList.add(uploadResult);
			}
		}
		
		return uploadResultList;
	}
	
	public FileVO upload(MultipartFile file) {
		
		// 업로드된 파일이 없으면 null 을 반환.
		if(file == null || file.isEmpty()) {
			return null;
		}
		
		
		/*
		 * Windows : Windows 10
		 * Macos : Mac OS X
		 * Linux : Linux
		 */
		String os = System.getProperty("os.name");
		
		/*
		 * Windows : C:\\Users\\user
		 * Macos: /Users/codemakers
		 */
		String userHome = System.getProperty("user.home");
		
		String filename = file.getOriginalFilename();
		if (this.obfuscationEnable) {
			filename = this.makeObfuscationName(file.getOriginalFilename());
		}
		// 파일 저장. 
		// OS별 파일의 경로를 지정.
		String directory = this.windowsBaseDirectory;
		if(os.toLowerCase().startsWith("mac")) {
			directory = userHome + this.macosBaseDirectory;
		}
		
		// 업로드한 파일이 저장될 경로.
		File storePath = new File( directory , filename );
		
		if( !storePath.getParentFile().exists() ) {
			storePath.getParentFile().mkdir();
		}
		
		// 업로드 한 파일을 저장
		try {
			file.transferTo(storePath);
		} catch (IllegalStateException | IOException e) {
			return null;
		}
		
		// 업로드한 결과를 반환
		FileVO uploadResult = new FileVO();
		uploadResult.setFileSize(storePath.length());
		uploadResult.setFileDisplayName(file.getOriginalFilename());
		uploadResult.setFileName(storePath.getName());
		uploadResult.setFilePath(storePath.getAbsolutePath());
		
//		uploadResult.setFileType("파일의 MimeType 을 작성");
		if(!this.availableStoreFile(storePath, uploadResult, file.getOriginalFilename())) {
			storePath.delete();
			System.out.println("업로드 불가능한 파일의 확장자 입니다!");
			return null;
		}
		
		return uploadResult;
	}
	
	private String makeObfuscationName(String filename) {
		
		// 확장자 분리.
		String ext = filename.substring(filename.lastIndexOf(".")); // .jpg
		
		// 파일명 난독화를 위해 난수 생성.
		String newFilename = UUID.randomUUID().toString();
		
		// 확장자 분리 여부에 따라 이름에 확장자 조합 또는 폐기.
		if( !this.hideExtEnable ) {
			newFilename += ext;
		}
		
		// 완성된 파일의 이름을 반환시킨다.
		return newFilename;
	}
	
	private boolean availableStoreFile(File file, FileVO uploadResult, String displayFilename) {
		try {
			String mimeType = this.tika.detect(file);
			uploadResult.setFileType(mimeType);
			
			if (mimeType.equals("text/plain")) {
				displayFilename.substring(displayFilename.lastIndexOf("."));
			}
			
			System.out.println(file.getName() + ", " + mimeType);
			return this.whitelist.contains(mimeType);
		} catch (IOException e) {
			return false;
		}
	
		
	}

}
