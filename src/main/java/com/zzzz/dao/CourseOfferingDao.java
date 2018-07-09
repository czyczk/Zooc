package com.zzzz.dao;

import com.zzzz.po.CourseOffering;

import java.sql.SQLException;

public interface CourseOfferingDao {
    int insert(CourseOffering courseOffering) throws SQLException;
    boolean checkExistenceById(long courseOfferingId) throws SQLException;
    CourseOffering getById(long courseOfferingId) throws SQLException;
    int update(CourseOffering courseOffering) throws SQLException;
    int delete(long courseOfferingId) throws SQLException;
}
