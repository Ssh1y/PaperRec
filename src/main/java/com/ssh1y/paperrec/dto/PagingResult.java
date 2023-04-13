package com.ssh1y.paperrec.dto;

import java.util.List;

/**
 * @author chenweihong
 */
public class PagingResult<T> {
    private int total;
    private List<T> dataList;

    public PagingResult(List<T> dataList, int total) {
        this.dataList = dataList;
        this.total = total;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
