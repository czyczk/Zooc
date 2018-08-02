package com.zzzz.repo;

import com.zzzz.po.Enterprise;

public interface EnterpriseRepo {
    void saveEnterprise(Enterprise enterprise);

    /**
     * Update an enterprise.
     * All related cache will be cleared,
     * including the courses
     * @param enterprise Enterprise
     */
    void updateEnterprise(Enterprise enterprise);

    /**
     * Delete an enterprise.
     * The related cache will be cleared,
     * including the courses
     * @param enterpriseId Enterprise ID
     */
    void deleteEnterprise(long enterpriseId);

    /**
     * Get an enterprise by its ID.
     * @param enterpriseId Enterprise ID
     * @return Enterprise
     */
    Enterprise getEnterpriseById(long enterpriseId);

    /**
     * Check if an enterprise is cached.
     * @param enterpriseId Enterprise ID
     * @return `true` if the enterprise is cached or `false` otherwise
     */
    boolean isCached(long enterpriseId);
}
