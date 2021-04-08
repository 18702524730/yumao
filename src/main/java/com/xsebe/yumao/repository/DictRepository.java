package com.xsebe.yumao.repository;

import java.util.List;

import com.xsebe.yumao.model.Dict;

public interface DictRepository {

    Dict get(String type, String key);
    
    List<Dict> getsByType(String type);
}
