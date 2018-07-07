package com.zzzz.controller;

import com.zzzz.dto.TrialReservationParam;
import com.zzzz.service.TrialReservationService;
import com.zzzz.service.TrialReservationServiceException;
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
     * Open for the user who made the reservation. Users can cancel/use the reservation.
     * Open for the owner to approve/reject the reservation
     * @param targetReservationId Target reservation ID
     * @param param status
     * @return Success: 203; Bad request: 400; Not found: 404; Internal: 500
     */
    @PutMapping("/reservation/{id}")
    public ResponseEntity update(@PathVariable("id") String targetReservationId,
                                 @RequestBody TrialReservationParam param) throws SQLException, TrialReservationServiceException {
        // TODO authentication not implemented yet
        trialReservationService.update(targetReservationId, param.getStatus());
        return ResponseEntity.noContent().build();
    }

    
}
