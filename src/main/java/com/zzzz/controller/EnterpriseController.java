package com.zzzz.controller;

import com.zzzz.dto.EnterpriseDetail;
import com.zzzz.po.Enterprise;
import com.zzzz.service.EnterpriseService;
import com.zzzz.service.EnterpriseServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
public class EnterpriseController {
    @Autowired
    private EnterpriseService enterpriseService;

    /**
     * Get an enterprise
     * @param enterpriseId The target enterprise ID
     * @return Success: Enterprise; Bad request: 400; Not found: 404; Internal: 500
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getById(@PathVariable("id") String enterpriseId) {
        // TODO authentication not implemented yet
        try {
            Enterprise result = enterpriseService.getById(enterpriseId);
            return ResponseEntity.ok(result);
        } catch (EnterpriseServiceException e) {
            return ResponseEntity.status(e.getExceptionTypeEnum().getStatus())
                    .body(e.getExceptionTypeEnum().getMessage());
        }
    }

    /**
     * Get the details of an enterprise (including its branches).
     * @param enterpriseId The target enterprise ID
     * @return Success: Enterprise detail; Bad request: 400; Not found: 404; Internal: 500
     */
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public ResponseEntity getDetailById(@PathVariable("id") String enterpriseId) {
        try {
            EnterpriseDetail result = enterpriseService.getDtoById(enterpriseId);
            return ResponseEntity.ok(result);
        } catch (EnterpriseServiceException e) {
            return ResponseEntity.status(e.getExceptionTypeEnum().getStatus())
                    .body(e.getExceptionTypeEnum().getMessage());
        }
    }

    /**
     * Update an enterprise. Don't pass in fields that are meant to be left unchanged.
     * @param targetId The ID of the enterprise to be modified.
     * @param name New name
     * @param imgUrl New image URL
     * @param introduction New introduction
     * @param videoUrl New video URL
     * @param detail New detail
     * @return Success: 203; Bad request: 400; Internal: 500
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity update(String targetId,
                                 @Nullable String name,
                                 @Nullable String imgUrl,
                                 @Nullable String introduction,
                                 @Nullable String videoUrl,
                                 @Nullable String detail) {
        // TODO authentication not implemented yet

        try {
            enterpriseService.update(targetId, name, imgUrl, introduction, videoUrl, detail);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EnterpriseServiceException e) {
            return ResponseEntity.status(e.getExceptionTypeEnum().getStatus())
                    .body(e.getExceptionTypeEnum().getMessage());
        }
    }
}
