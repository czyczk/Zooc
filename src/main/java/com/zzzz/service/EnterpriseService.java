package com.zzzz.service;

import com.zzzz.po.Enterprise;

import java.sql.SQLException;

public interface EnterpriseService {
    /**
     * Get an enterprise by its ID.
     * Redis:
     *   - Fetch it from the cache first.
     *     On missing, fetch it from the DB and cache it.
     * @param enterpriseId Enterprise ID
     * @return Enterprise
     * @throws EnterpriseServiceException An exception is thrown if the query is not successful.
     */
    Enterprise getById(String enterpriseId) throws EnterpriseServiceException, SQLException;

    /**
     * Update an enterprise. A field should be left null if no modification is to be made.
     * Redis:
     *   - Update it from the cache. Related cache will be deleted as well.
     * @param targetEnterpriseId The ID of the enterprise to be modified.
     * @param name New name
     * @param imgUrl New image URL
     * @param introduction New introduction
     * @param videoUrl New video URL
     * @param detail New detail
     * @throws EnterpriseServiceException An exception is thrown if the update is not successful.
     */
    void update(String targetEnterpriseId, String name, String imgUrl, String introduction, String videoUrl, String detail) throws EnterpriseServiceException, SQLException;
}
