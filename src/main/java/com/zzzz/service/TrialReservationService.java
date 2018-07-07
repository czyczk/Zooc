package com.zzzz.service;

import com.zzzz.po.TrialReservation;
import com.zzzz.vo.TrialReservationDetail;

import java.sql.SQLException;
import java.util.Date;

public interface TrialReservationService {
    /**
     * Insert a new reservation. The assigned ID will be returned. The status is by default `PENDING`.
     * @param userId User ID
     * @param trialId Trial ID
     * @param time The time the controller receives the request
     * @param message Message (Optional)
     * @return The ID of the new trial reservation.
     * @throws TrialReservationServiceException An exception is thrown if the insertion is unsuccessful.
     */
    long insert(String userId, String trialId, Date time, String message) throws SQLException, TrialReservationServiceException;

    /**
     * Get a reservation by its ID.
     * @param reservationId Reservation ID
     * @return The reservation
     * @throws TrialReservationServiceException An exception is thrown if the query is unsuccessful.
     */
    TrialReservation getById(String reservationId) throws SQLException, TrialReservationServiceException;

    /**
     * Get the VO of a reservation by its ID.
     * @param reservationId Reservation ID
     * @return The VO of the reservation
     * @throws TrialReservationServiceException An exception is thrown if the query is unsuccessful.
     */
    TrialReservationDetail getVoById(String reservationId) throws SQLException, TrialReservationServiceException;

    /**
     * Update a reservation. Only the status is open for modification.
     * The reservation won't be changed if the status is null.
     * @param targetReservationId Target reservation ID
     * @param status New status
     * @throws TrialReservationServiceException An exception is thrown if the update is unsuccessful.
     */
    void update(String targetReservationId, String status) throws SQLException, TrialReservationServiceException;
}
