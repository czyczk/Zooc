package com.zzzz.dao;

import com.zzzz.po.TrialReservation;
import com.zzzz.vo.TrialReservationDetail;

import java.sql.SQLException;

public interface TrialReservationDao {
    int insert(TrialReservation reservation) throws SQLException;
    TrialReservation getById(long reservationId) throws SQLException;
    TrialReservationDetail getVoById(long reservationId) throws SQLException;

    /**
     * Update a reservation. Only the status is open for modification.
     * @param reservation New reservation. Only the `status` field will be used
     * @return Number of rows affected
     */
    int update(TrialReservation reservation) throws SQLException;
}
