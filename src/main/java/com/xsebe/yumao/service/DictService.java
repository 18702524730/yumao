package com.xsebe.yumao.service;

import java.util.List;

import com.xsebe.yumao.exception.YumaoException;
import com.xsebe.yumao.model.Dict;

public interface DictService {

    Dict get(String type, String key) throws YumaoException;
    
    String getValue(String type, String key) throws YumaoException;
    
    List<Dict> getsByType(String type) throws YumaoException;
}
