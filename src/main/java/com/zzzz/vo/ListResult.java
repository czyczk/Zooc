package com.zzzz.vo;

import java.util.List;

public class ListResult<E> {
    private Long targetPage;
    private Long pageSize;
    private Long total;
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

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<E> getList() {
        return list;
    }

    public void setList(List<E> list) {
        this.list = list;
    }
}
