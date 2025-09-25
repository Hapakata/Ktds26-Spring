package com.ktdsuniversity.edu.board.dao;

import java.util.List;

import com.ktdsuniversity.edu.board.vo.BoardVO;
import com.ktdsuniversity.edu.board.vo.RequestCreateBoardVO;

public interface BoardDao {
	public BoardVO selectBoardOne(String id);
	public List<BoardVO> selectBoardList();
	public int selectBoardListCount();
	public boolean writeBoard(RequestCreateBoardVO requestCreateBoardVO);
	public boolean updateViewCountUp(String id);
	public boolean modifyBoard(RequestCreateBoardVO requestCreateBoardVO);
	public boolean boardDelete(String id);
}
