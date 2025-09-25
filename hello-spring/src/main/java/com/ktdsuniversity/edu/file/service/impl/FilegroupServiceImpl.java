package com.ktdsuniversity.edu.file.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.ktdsuniversity.edu.file.dao.FilegroupDao;
import com.ktdsuniversity.edu.file.service.FilegroupService;

@Service
public class FilegroupServiceImpl implements FilegroupService {

    @Autowired
    private FilegroupDao filegroupDao;

}