package com.zzzz.vo;

import java.util.List;

public class ListResult<E> {
    private Long targetPage;
    private Long pageSize;
    private Long totalNumPages;
    private List<E> list;

    public Long getTargetPage() {
        return targetPage;
    }

    public void setTargetPage(Long targetPage) {
        this.targetPage = targetPage;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalNumPages() {
        return totalNumPages;
    }

    public void setTotalNumPages(Long totalNumPages) {
        this.totalNumPages = totalNumPages;
    }

    public List<E> getList() {
        return list;
    }

    public void setList(List<E> list) {
        this.list = list;
    }
}
