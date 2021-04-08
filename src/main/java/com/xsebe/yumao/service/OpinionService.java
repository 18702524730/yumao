package com.xsebe.yumao.service;

import com.xsebe.yumao.exception.YumaoException;
import com.xsebe.yumao.model.User;

public interface OpinionService {

    void addOpinion(User user,  String opinionTitle, String opinionContent, String opinionEmail, String opinionTelphone) throws YumaoException;

}
