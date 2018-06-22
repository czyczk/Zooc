package com.zzzz.dao;

import com.zzzz.po.Administrator;

import java.sql.SQLException;

public interface AdministratorDao {
    int insert(Administrator administrator) throws SQLException;
    boolean checkExistenceById(long administratorId) throws SQLException;
    Administrator getById(long administratorId) throws SQLException;
}
