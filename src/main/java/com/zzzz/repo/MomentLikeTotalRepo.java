package com.zzzz.repo;

public interface MomentLikeTotalRepo {
    /**
     * Save the total number of likes of a moment.
     * Triggered when the total is not cached.
     * @param momentId Moment ID
     */
    void saveTotal(long momentId, long total);

    /**
     * Delete the total number of likes of a moment.
     * Triggered when a moment is deleted.
     * @param momentId Moment ID
     */
    void deleteTotal(long momentId);

    /**
     * Increment the total number of likes of a moment if the number is cached.
     * Triggered when a like of the moment is produced.
     * @param momentId Moment ID
     */
    void incrTotalIfCached(long momentId);

    /**
     * Decrement the total number of likes of a moment if the number is cached.
     * Triggered when a like of the moment is canceled.
     * @param momentId Moment ID
     */
    void decrTotalIfCached(long momentId);

    /**
     * Get the total number of likes of a moment. Return null if it's not cached.
     * @param momentId Moment ID
     * @return The total number of likes of a moment.
     */
    Long getTotal(long momentId);
}
