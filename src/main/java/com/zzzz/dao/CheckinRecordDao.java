package com.zzzz.dao;

import com.zzzz.po.CheckinRecord;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface CheckinRecordDao {
    int insert(CheckinRecord checkinRecord) throws SQLException;
    CheckinRecord getByPk(@Param("userId") long userId,
                          @Param("enterpriseId") long enterpriseId,
                          @Param("date") String date) throws SQLException;
    List<CheckinRecord> list(@Param("userId") long userId,
              @Param("enterpriseId") long enterpriseId,
              @Param("year") int year,
              @Param("month") short month) throws SQLException;
}
