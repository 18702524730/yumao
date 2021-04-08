package com.xsebe.yumao.service;

import com.xsebe.yumao.exception.YumaoException;
import com.xsebe.yumao.model.EvidChainCategory;

public interface EvidChainCategoryService {

    EvidChainCategory get(String id) throws YumaoException;
}
