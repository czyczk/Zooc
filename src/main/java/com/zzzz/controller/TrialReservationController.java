package com.zzzz.controller;

import com.zzzz.dto.TrialReservationParam;
import com.zzzz.service.TrialReservationService;
import com.zzzz.service.TrialReservationServiceException;
import com.zzzz.vo.ListResult;
import com.zzzz.vo.TrialReservationDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Date;

@RestController
@RequestMapping("/api/v1")
public class TrialReservationController {
    private final TrialReservationService trialReservationService;

    @Autowired
    public TrialReservationController(TrialReservationService trialReservationService) {
        this.trialReservationService = trialReservationService;
    }

    /**
     * Reserve a trial. Open for users who have logged in.
     * @param trialId Trial ID
     * @param param userId, message (Optional)
     * @return Success: New reservation ID; Bad request: 400; Not found: 404; Internal: 500
     */
    @PostMapping("/trial/{id}/reservation")
    public ResponseEntity<Long> reserve(@PathVariable("id") String trialId,
                                        @RequestBody TrialReservationParam param) throws SQLException, TrialReservationServiceException {
        // TODO authentication not implemented yet
        long lastId = trialReservationService.insert(param.getUserId(), trialId, new Date(), param.getMessage());
        return ResponseEntity.ok(lastId);
    }

    /**
     * Get the detail of a reservation. Open for the user who reserved the trial and the owner of the trial.
     * @param reservationId Reservation ID
     * @return Success: Reservation detail; Bad request: 400; Not found: 404; Internal: 500
     */
    @GetMapping("/reservation/{id}")
    public ResponseEntity<TrialReservationDetail> getDetailById(@PathVariable("id") String reservationId) throws SQLException, TrialReservationServiceException {
        // TODO authentication not implemented yet
        TrialReservationDetail result = trialReservationService.getVoById(reservationId);
        return ResponseEntity.ok(result);
    }

    /**
     * Update the status of a reservation.
     * Open for the user who made the reservation. Users can cancel/use the reservation and modify the message before it's used.
     * Open for the owner to approve/reject the reservation
     * @param targetReservationId Target reservation ID
     * @param param message, status
     * @return Success: 203; Bad request: 400; Not found: 404; Internal: 500
     */
    @PutMapping("/reservation/{id}")
    public ResponseEntity update(@PathVariable("id") String targetReservationId,
                                 @RequestBody TrialReservationParam param) throws SQLException, TrialReservationServiceException {
        // TODO authentication not implemented yet
        trialReservationService.update(targetReservationId, param.getMessage(), param.getStatus());
        return ResponseEntity.noContent().build();
    }

    /**
     * Get a list containing trial reservations meeting the requirements.
     * @param userId The ID of the user to which the trial reservations belong
     * @param usePagination Use pagination or not (`false` by default)
     * @param targetPage Target page (required when using pagination)
     * @param pageSize Page size (required when using pagination)
     * @param reservationId Reservation ID (optional)
     * @param trialId Trial ID (optional)
     * @param trialNameContaining Trial name containing (optional)
     * @param status Status (optional)
     * @return Success: List; Bad request: 400; Not found: 404; Internal: 500
     */
    @GetMapping("/user/{id}/reservation/list")
    public ResponseEntity<ListResult<TrialReservationDetail>> getUserHistory(@PathVariable("id") String userId,
                                                                             String usePagination,
                                                                             String targetPage, String pageSize,
                                                                             String reservationId,
                                                                             String trialId, String trialNameContaining,
                                                                             String status) throws SQLException, TrialReservationServiceException {
        // TODO authentication not implemented yet
        ListResult<TrialReservationDetail> result = trialReservationService.list(usePagination, targetPage, pageSize, reservationId, userId, null, trialId, trialNameContaining, status);
        return ResponseEntity.ok(result);
    }

    /**
     * Get a list containing trial reservations meeting the requirements.
     * @param enterpriseId The ID of the enterprise to which the reservations belong
     * @param usePagination Use pagination or not (`false` by default)
     * @param targetPage Target page (required when using pagination)
     * @param pageSize Page size (required when using pagination)
     * @param reservationId Reservation ID (optional)
     * @param userId User ID (optional)
     * @param trialId Trial ID (optional)
     * @param trialNameContaining Trial name containing (optional)
     * @param status Status (optional)
     * @return Success: List; Bad request: 400; Not found: 404; Internal: 500
     */
    @GetMapping("/enterprise/{id}/reservation/list")
    public ResponseEntity<ListResult<TrialReservationDetail>> list(@PathVariable("id") String enterpriseId,
                                                                  String usePagination,
                                                                  String targetPage, String pageSize,
                                                                  String reservationId,
                                                                  String userId,
                                                                  String trialId, String trialNameContaining,
                                                                  String status) throws SQLException, TrialReservationServiceException {
        // TODO authentication not implemented yet
        ListResult<TrialReservationDetail> result = trialReservationService.list(usePagination, targetPage, pageSize, reservationId, userId, enterpriseId, trialId, trialNameContaining, status);
        return ResponseEntity.ok(result);
    }
}
