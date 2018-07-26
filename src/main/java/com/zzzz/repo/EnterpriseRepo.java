package com.zzzz.repo;

import com.zzzz.vo.EnterpriseDetail;

public interface EnterpriseRepo {
    void saveEnterpriseVO(EnterpriseDetail enterprise);

    void updateEnterpriseVO(EnterpriseDetail enterprise);

    /**
     * Delete an enterprise.
     * The related cache will be cleared.
     * @param enterpriseId Enterprise ID
     */
    void deleteEnterpriseVO(long enterpriseId);

    /**
     * Get the VO of an enterprise.
     * @param enterpriseId Enterprise ID
     * @return Enterprise VO
     */
    EnterpriseDetail getEnterpriseVO(long enterpriseId);

    /**
     * Check if an enterprise is cached.
     * @param enterpriseId Enterprise ID
     * @return `true` if the enterprise is cached or `false` otherwise
     */
    boolean isCached(long enterpriseId);
}
