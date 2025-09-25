package com.ktdsuniversity.edu.board.vo;

import java.util.List;

public class BoardListVO {
	public List<BoardVO> boardList;
	public int listCount;
	
	@Override
	public String toString() {
		return "BoardListVO [boardList=" + boardList + ", listCount=" + listCount + "]";
	}
	public List<BoardVO> getBoardList() {
		return boardList;
	}
	public void setBoardList(List<BoardVO> boardList) {
		this.boardList = boardList;
	}
	public int getListCount() {
		return listCount;
	}
	public void setListCount(int listCount) {
		this.listCount = listCount;
	}
}
