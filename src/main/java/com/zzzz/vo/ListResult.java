package com.zzzz.vo;

import java.util.List;

public class ListResult<E> {
    private int targetPage;
    private int pageSize;
    private int totalNumPages;
    private List<E> list;

    public int getTargetPage() {
        return targetPage;
    }

    public void setTargetPage(int targetPage) {
        this.targetPage = targetPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalNumPages() {
        return totalNumPages;
    }

    public void setTotalNumPages(int totalNumPages) {
        this.totalNumPages = totalNumPages;
    }

    public List<E> getList() {
        return list;
    }

    public void setList(List<E> list) {
        this.list = list;
    }
}
