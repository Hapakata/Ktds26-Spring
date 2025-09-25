package com.ktdsuniversity.edu.board.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktdsuniversity.edu.board.dao.BoardDao;
import com.ktdsuniversity.edu.board.service.BoardService;
import com.ktdsuniversity.edu.board.vo.BoardListVO;
import com.ktdsuniversity.edu.board.vo.BoardVO;
import com.ktdsuniversity.edu.board.vo.RequestCreateBoardVO;
import com.ktdsuniversity.edu.file.dao.FileDao;
import com.ktdsuniversity.edu.file.dao.FilegroupDao;
import com.ktdsuniversity.edu.file.util.MultipartFileHandler;
import com.ktdsuniversity.edu.file.vo.FileGroupVO;
import com.ktdsuniversity.edu.file.vo.FileVO;

@Service
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	private BoardDao boardDao;
	
	@Autowired
	private MultipartFileHandler multipartFileHandler; 
	@Autowired
	private BoardDao baordDao;
	@Autowired
	private FilegroupDao fileGroupDao;
	@Autowired
	private FileDao fileDao;


	@Override
	public BoardVO selectBoardOne(String id) {
		this.boardDao.updateViewCountUp(id);
		return this.boardDao.selectBoardOne(id);
	}

	@Override
	public BoardListVO selectBoardList() {
		List<BoardVO> boardList = this.boardDao.selectBoardList();
		BoardListVO list = new BoardListVO();
		list.setBoardList(boardList);
		return list ;
	}

	public int selectBoardListCount() {
		return this.boardDao.selectBoardListCount();
	}

	@Override
	public boolean writeBoard(RequestCreateBoardVO requestCreateBoardVO) {
		// 카운트 업!
		this.boardDao.updateViewCountUp(requestCreateBoardVO.getId());
		
		// 첨부파일 가져와서 저장!
		List<FileVO> uploadResult = this.multipartFileHandler.upload(requestCreateBoardVO.getFile());
		
		if(uploadResult != null && uploadResult.size() > 0 ) {
			// 1. File Group Insert
			FileGroupVO fileGroupVO = new FileGroupVO();
			fileGroupVO.setFileCount(uploadResult.size());
			int insertGroupCount = this.fileGroupDao.insertFileGroup(fileGroupVO);
			
			// 2. File Insert
			for(FileVO result : uploadResult) {
				result.setFileGroupId(fileGroupVO.getFileGroupId());
				int insertFileCount = this.fileDao.insertFile(result);				
			}
			
			
			requestCreateBoardVO.setFileGroupId(fileGroupVO.getFileGroupId());
		}

		System.out.println(uploadResult);
		return this.boardDao.writeBoard(requestCreateBoardVO);

	}
	
	@Override
	public boolean modifyBoard(RequestCreateBoardVO requestCreateBoardVO) {

		return this.boardDao.modifyBoard(requestCreateBoardVO);
	}
	@Override
	public boolean boardDelete(String id) {
		return this.boardDao.boardDelete(id);
	}





}
