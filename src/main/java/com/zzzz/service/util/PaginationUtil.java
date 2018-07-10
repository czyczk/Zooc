package com.zzzz.service.util;

import com.zzzz.vo.ListResult;

public class PaginationUtil {
    /**
     * Check the validity of the target page and return the starting index.
     * If the starting index exceeds the total number of items, -1 will be returned.
     * @param targetPage Target page
     * @param pageSize Page size
     * @param totalNumItems Total number of items
     * @param listResult List result to be updated
     * @return Starting index if valid or -1 if invalid
     */
    public static long getStartingIndex(long targetPage, long pageSize, long totalNumItems, ListResult listResult) {
        long starting = (targetPage - 1) * pageSize;
        listResult.setTotal(totalNumItems);
        listResult.setTargetPage(targetPage);
        listResult.setPageSize(pageSize);

        // If the starting index exceeds the total number of items,
        // return a list result with an empty list
        if (starting >= totalNumItems)
            return -1;
        return starting;
    }
}
