package com.zzzz.dao;

import com.zzzz.po.User;

import java.sql.SQLException;

public interface UserDao {
    int insert(User user) throws SQLException;
    boolean checkExistenceById(long userId) throws SQLException;
    boolean checkExistenceByEmail(String email) throws SQLException;
    User getById(long userId) throws SQLException;
    User getByEmail(String email) throws SQLException;
    int update(User user) throws SQLException;

    // TODO
    // Cascade when deleted. Implemented later.
}
