package com.zzzz.controller;

import com.zzzz.dto.CheckinParam;
import com.zzzz.po.CheckinRecord;
import com.zzzz.service.CheckinService;
import com.zzzz.service.CheckinServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CheckinController {
    private final CheckinService checkinService;

    @Autowired
    public CheckinController(CheckinService checkinService) {
        this.checkinService = checkinService;
    }

    /**
     * Check in.
     * Corresponding points will be assigned to the user in the enterprise.
     * @param param userId, enterpriseId
     * @return Success: 204; Bad request: 400; Not found: 404; Internal: 500
     */
    @PostMapping("/checkin")
    public ResponseEntity checkin(@RequestBody CheckinParam param) throws SQLException, CheckinServiceException {
        checkinService.checkIn(param.getUserId(), param.getEnterpriseId());
        return ResponseEntity.noContent().build();
    }

    /**
     * Check if the user has checked in in the enterprise on the day specified.
     * @param userId User ID
     * @param enterpriseId Enterprise ID
     * @param date Unix epoch
     * @return Success: boolean; Bad request: 400; Not found: 404; Internal: 500
     */
    @GetMapping("/checkin")
    public ResponseEntity<Boolean> checkCheckedInOrNot(String userId, String enterpriseId, String date) throws SQLException, CheckinServiceException {
        boolean result = checkinService.checkCheckedInOrNot(userId, enterpriseId, date);
        return ResponseEntity.ok(result);
    }

    /**
     * List checkin records of the user in the enterprise within the month of the year.
     * @param userId User ID
     * @param enterpriseId Enterprise ID
     * @param year Year
     * @param month Month
     * @return Success: Checkin record list; Bad request: 400; Not found: 404; Internal: 500
     */
    @GetMapping("/checkin/list")
    public ResponseEntity<List<CheckinRecord>> list(String userId, String enterpriseId, String year, String month) throws SQLException, CheckinServiceException {
        List<CheckinRecord> result = checkinService.list(userId, enterpriseId, year, month);
        return ResponseEntity.ok(result);
    }
}
