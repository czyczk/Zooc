package com.zzzz.service;

import com.zzzz.po.CouponRecord;
import com.zzzz.vo.ListResult;

import java.sql.SQLException;

public interface CouponRecordService {
    CouponRecord getById(String couponRecordId) throws CouponRecordServiceException, SQLException;
    ListResult<CouponRecord> list(String usePagination,
                                  String targetPage, String pageSize,
                                  String userId, String enterpriseId,
                                  String couponRecordId, String couponId) throws CouponRecordServiceException, SQLException;
}
