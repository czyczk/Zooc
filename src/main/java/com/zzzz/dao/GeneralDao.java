package com.zzzz.dao;

import java.sql.SQLException;

public interface GeneralDao {
    long getLastInsertId() throws SQLException;
}
