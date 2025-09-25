package com.ktdsuniversity.edu.board.service;

import com.ktdsuniversity.edu.board.vo.BoardListVO;
import com.ktdsuniversity.edu.board.vo.BoardVO;
import com.ktdsuniversity.edu.board.vo.RequestCreateBoardVO;

public interface BoardService {
	
	public BoardVO selectBoardOne(String id);
	public BoardListVO selectBoardList ();
	public int selectBoardListCount();
	public boolean writeBoard(RequestCreateBoardVO requestCreateBoardVO);
	public boolean modifyBoard(RequestCreateBoardVO requestCreateBoardVO);
	public boolean boardDelete(String id);
}
