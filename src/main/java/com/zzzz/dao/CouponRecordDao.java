package com.zzzz.dao;

import com.zzzz.po.CouponRecord;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;
import java.util.List;

public interface CouponRecordDao {
    int insert(CouponRecord record) throws SQLException;
    boolean checkExistenceById(long couponRecordId) throws SQLException;
    CouponRecord getById(long couponRecordId) throws SQLException;
    long countTotal(@Param("userId") long userId,
                    @Param("enterpriseId") Long enterpriseId,
                    @Param("couponRecordId") Long couponRecordId,
                    @Param("couponId") Long couponId) throws SQLException;
    List<CouponRecord> list(@Param("usePagination") boolean usePagination,
                            @Param("starting") Long starting,
                            @Param("pageSize") Long pageSize,
                            @Param("userId") long userId,
                            @Param("enterpriseId") Long enterpriseId,
                            @Param("couponRecordId") Long couponRecordId,
                            @Param("couponId") Long couponId) throws SQLException;
}
