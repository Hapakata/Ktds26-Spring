package com.ktdsuniversity.edu.board.vo;

import com.ktdsuniversity.edu.file.vo.FileGroupVO;

public class BoardVO {
	
	private int number;
	private String id;
	private String subject;
	private String content;
	private String email;
	private int viewCnt;
	private String crtDt;
	private String mdfyDt;
	private String delYn;
	
	private FileGroupVO fileGroupVO;
	
	public FileGroupVO getFileGroupVO() {
		return fileGroupVO;
	}
	public void setFileGroupVO(FileGroupVO fileGroupVO) {
		this.fileGroupVO = fileGroupVO;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getViewCnt() {
		return viewCnt;
	}
	public void setViewCnt(int viewCnt) {
		this.viewCnt = viewCnt;
	}

	public String getCrtDt() {
		return crtDt;
	}
	public void setCrtDt(String crtDt) {
		this.crtDt = crtDt;
	}
	public String getMdfyDt() {
		return mdfyDt;
	}
	public void setMdfyDt(String mdfyDt) {
		this.mdfyDt = mdfyDt;
	}
	public String getDelYn() {
		return delYn;
	}
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}
	@Override
	public String toString() {
		return "BoardVO [number=" + number + ", id=" + id + ", subject=" + subject + ", content=" + content + ", email="
				+ email + ", viewCnt=" + viewCnt + ", crtDt=" + crtDt + ", mdfyDt=" + mdfyDt + ", delYn=" + delYn
				+ ", fileGroupVO=" + fileGroupVO + "]";
	}

	
	
	}
