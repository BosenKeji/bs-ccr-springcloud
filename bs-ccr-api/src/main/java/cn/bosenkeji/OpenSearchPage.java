package cn.bosenkeji;

import java.util.List;

public class OpenSearchPage {

    private Long total;
    private Integer pageNum=0;
    private Integer pageSize=10;

    private Integer startRow;
    private Object list;

    private Integer totalPage;

    public void countStartRow() {
        this.startRow = (this.pageNum-1)*pageSize;
    }

    public void countTotalPages() {
        this.totalPage = Math.toIntExact(total / pageSize);
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getStartRow() {
        return startRow;
    }

    public void setStartRow(Integer startRow) {
        this.startRow = startRow;
    }

    public Object getList() {
        return list;
    }

    public void setList(Object list) {
        this.list = list;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }
}
