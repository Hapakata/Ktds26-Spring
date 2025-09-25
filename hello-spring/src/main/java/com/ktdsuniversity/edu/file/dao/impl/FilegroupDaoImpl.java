package com.ktdsuniversity.edu.file.dao.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktdsuniversity.edu.file.dao.FilegroupDao;
import com.ktdsuniversity.edu.file.vo.FileGroupVO;

@Repository
public class FilegroupDaoImpl extends SqlSessionDaoSupport implements FilegroupDao {

    private final String NAME_SPACE = "com.ktdsuniversity.edu.file.dao.impl.FilegroupDaoImpl.";

    @Autowired
    @Override
    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        super.setSqlSessionTemplate(sqlSessionTemplate);
    }

	@Override
	public int insertFileGroup(FileGroupVO fileGroupVO) {
		return super.getSqlSession().insert(this.NAME_SPACE + "insertFileGroup", fileGroupVO);
	}


}