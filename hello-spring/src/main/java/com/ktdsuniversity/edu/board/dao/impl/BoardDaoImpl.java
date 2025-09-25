package com.ktdsuniversity.edu.board.dao.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktdsuniversity.edu.board.dao.BoardDao;
import com.ktdsuniversity.edu.board.vo.BoardVO;
import com.ktdsuniversity.edu.board.vo.RequestCreateBoardVO;

@Repository
public class BoardDaoImpl
extends SqlSessionDaoSupport 
implements BoardDao{
	
	private final String PATH = "com.ktdsuniversity.edu.board.dao.impl.BoardDaoImpl.";
	
	@Autowired
	@Override
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		super.setSqlSessionTemplate(sqlSessionTemplate);
	}
	
	@Override
	public BoardVO selectBoardOne(String id) {
		return super.getSqlSession().selectOne(this.PATH + "selectBoardOneDao" , id);
	}

	@Override
	public List<BoardVO> selectBoardList() {
		return super.getSqlSession().selectList(this.PATH + "selectBoardList");
	}

	@Override
	public int selectBoardListCount() {
		return super.getSqlSession().selectOne(this.PATH + "selectBoardListCount");
	}

	@Override
	public boolean writeBoard(RequestCreateBoardVO requestCreateBoardVO) {
		System.out.println(requestCreateBoardVO.getContent());

		return super.getSqlSession().update(this.PATH + "writeBoard", requestCreateBoardVO) > 0;
	}

	@Override
	public boolean updateViewCountUp(String id) {
		return super.getSqlSession().update(this.PATH + "updateViewCountUp", id) > 0;
	}

	@Override
	public boolean modifyBoard(RequestCreateBoardVO requestCreateBoardVO) {
		return super.getSqlSession().update(this.PATH + "updateModify", requestCreateBoardVO ) > 0;
	}

	@Override
	public boolean boardDelete(String id) {
		return super.getSqlSession().update(this.PATH + "updateDelete", id)>0;
	}

	

}
