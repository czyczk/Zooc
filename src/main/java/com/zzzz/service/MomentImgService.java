package com.zzzz.service;

import com.zzzz.po.MomentImg;

import java.sql.SQLException;
import java.util.List;

public interface MomentImgService {
    void updateImgUrls(String momentId, List<String> imgUrls) throws MomentImgServiceException, SQLException;

    List<MomentImg> list(String momentId) throws MomentImgServiceException, SQLException;


}
