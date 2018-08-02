package com.zzzz.repo;

public interface PointRepo {
    void save(long userId, long enterpriseId, long point);

    /**
     * Get the number of points of the user in the enterprise.
     * @param userId User ID
     * @param enterpriseId Enterprise ID
     * @return The number of points
     */
    Long getByPk(long userId, long enterpriseId);
    void incrByIfExisting(long userId, long enterpriseId, int numPoints);
    void decrByIfExisting(long userId, long enterpriseId, int numPoints);
}
