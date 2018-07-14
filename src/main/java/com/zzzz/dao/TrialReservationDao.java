package com.zzzz.dao;

import com.zzzz.po.TrialReservation;
import com.zzzz.po.TrialReservationStatusEnum;
import com.zzzz.vo.TrialReservationDetail;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;
import java.util.List;

public interface TrialReservationDao {
    int insert(TrialReservation reservation) throws SQLException;
    TrialReservation getById(long reservationId) throws SQLException;
    TrialReservationDetail getVoById(long reservationId) throws SQLException;

    /**
     * Update a reservation. Only the message and the status are open for modification.
     * @param reservation New reservation. Only the `message` and the `status` field will be used
     * @return Number of rows affected
     */
    int update(TrialReservation reservation) throws SQLException;

    /**
     * Count the number of trial reservations meeting the requirements.
     * @param reservationId Reservation ID (optional)
     * @param userId User ID (optional)
     * @param enterpriseId Enterprise ID (optional)
     * @param trialId Trial ID (optional)
     * @param trialNameContaining Trial name containing (optional)
     * @param status Status (optional)
     * @return The number of trial reservations meeting the requirements
     * @throws SQLException An exception is thrown if the query is not successful.
     */
    long countTotal(@Param("reservationId") Long reservationId,
                    @Param("userId") Long userId,
                    @Param("enterpriseId") Long enterpriseId,
                    @Param("trialId") Long trialId,
                    @Param("trialNameContaining") String trialNameContaining,
                    @Param("status")TrialReservationStatusEnum status) throws SQLException;

    /**
     * Get a list of all trial reservations meeting the requirements.
     * @param usePagination Use pagination or not
     * @param starting Starting index (required when using pagination)
     * @param pageSize Page size (required when using pagination)
     * @param reservationId Reservation ID (optional)
     * @param userId User ID (optional)
     * @param enterpriseId Enterprise ID (optional)
     * @param trialId Trial ID (optional)
     * @param trialNameContaining Trial name containing (optional)
     * @param status Status (optional)
     * @return A list of all trial reservations meeting the requirements
     * @throws SQLException An exception is thrown if the query is not successful.
     */
    List<TrialReservationDetail> list(@Param("usePagination") boolean usePagination,
                                      @Param("starting") Long starting,
                                      @Param("pageSize") Long pageSize,
                                      @Param("reservationId") Long reservationId,
                                      @Param("userId") Long userId,
                                      @Param("enterpriseId") Long enterpriseId,
                                      @Param("trialId") Long trialId,
                                      @Param("trialNameContaining") String trialNameContaining,
                                      @Param("status")TrialReservationStatusEnum status) throws SQLException;
}
