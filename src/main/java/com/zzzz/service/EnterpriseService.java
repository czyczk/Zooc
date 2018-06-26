package com.zzzz.service;

import com.zzzz.vo.EnterpriseDetail;
import com.zzzz.po.Enterprise;

public interface EnterpriseService {
    /*
     * Create an enterprise with a default template.
     * @param administratorId The ID of the administrator whom the new enterprise belongs to
     * @throws EnterpriseServiceException An exception is thrown if the insertion is not successful.
     */
//    void createTemplate(String administratorId) throws EnterpriseServiceException;

    void insert(String administratorId, String name, String imgUrl, String introduction, String videoUrl, String detail) throws EnterpriseServiceException;
    Enterprise getById(String enterpriseId) throws EnterpriseServiceException;
    EnterpriseDetail getVoById(String enterpriseId) throws EnterpriseServiceException;

    /**
     * Update an enterprise. A field should be left null if no modification is to be made.
     * @param targetEnterpriseId The ID of the enterprise to be modified.
     * @param name New name
     * @param imgUrl New image URL
     * @param introduction New introduction
     * @param videoUrl New video URL
     * @param detail New detail
     * @throws EnterpriseServiceException An exception is thrown if the update is not successful.
     */
    void update(String targetEnterpriseId, String name, String imgUrl, String introduction, String videoUrl, String detail) throws EnterpriseServiceException;
}
