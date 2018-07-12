package com.zzzz.controller;

import com.zzzz.dto.EnterpriseParam;
import com.zzzz.vo.EnterpriseDetail;
import com.zzzz.po.Enterprise;
import com.zzzz.service.EnterpriseService;
import com.zzzz.service.EnterpriseServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/v1/enterprise")
public class EnterpriseController {
    @Autowired
    private EnterpriseService enterpriseService;

    /**
     * Get an enterprise
     * @param enterpriseId The target enterprise ID
     * @return Success: Enterprise; Bad request: 400; Not found: 404; Internal: 500
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<Enterprise> getById(@PathVariable("id") String enterpriseId) throws EnterpriseServiceException, SQLException {
        // TODO authentication not implemented yet
        Enterprise result = enterpriseService.getById(enterpriseId);
        return ResponseEntity.ok(result);
    }

//    /**
//     * Get the details of an enterprise (including its branches).
//     * @param enterpriseId The target enterprise ID
//     * @return Success: Enterprise detail; Bad request: 400; Not found: 404; Internal: 500
//     */
//    @GetMapping(value = "/detail/{id}")
//    public ResponseEntity<EnterpriseDetail> getDetailById(@PathVariable("id") String enterpriseId) throws EnterpriseServiceException, SQLException {
//        EnterpriseDetail result = enterpriseService.getVoById(enterpriseId);
//        return ResponseEntity.ok(result);
//    }

    /**
     * Update an enterprise. Don't pass in fields that are meant to be left unchanged.
     * @param targetId The ID of the enterprise to be modified.
     * @param enterpriseParam name, imgUrl, introduction, videoUrl, detail
     * @return Success: 204; Bad request: 400; Not found: 404; Internal: 500
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity update(@PathVariable("id") String targetId,
                                 @RequestBody EnterpriseParam enterpriseParam) throws EnterpriseServiceException, SQLException {
        // TODO authentication not implemented yet
        enterpriseService.update(targetId, enterpriseParam.getName(), enterpriseParam.getImgUrl(), enterpriseParam.getIntroduction(), enterpriseParam.getVideoUrl(), enterpriseParam.getDetail());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
