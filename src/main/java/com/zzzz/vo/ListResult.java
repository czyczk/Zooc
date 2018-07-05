package com.zzzz.vo;

import java.util.List;

public class ListResult<E> {
    private long targetPage;
    private long pageSize;
    private long totalNumPages;
    private List<E> list;

    public long getTargetPage() {
        return targetPage;
    }

    public void setTargetPage(long targetPage) {
        this.targetPage = targetPage;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalNumPages() {
        return totalNumPages;
    }

    public void setTotalNumPages(long totalNumPages) {
        this.totalNumPages = totalNumPages;
    }

    public List<E> getList() {
        return list;
    }

    public void setList(List<E> list) {
        this.list = list;
    }
}
