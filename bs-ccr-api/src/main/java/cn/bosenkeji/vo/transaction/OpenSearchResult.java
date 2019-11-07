package cn.bosenkeji.vo.transaction;

import java.util.List;

public class OpenSearchResult {

    private Long total;
    private Long num;
    private Long viewtotal;
    private List<OpenSearchField> items;
    private List<OpenSearchFacet> facet;
    private String error;
    private String status;

    public List<OpenSearchFacet> getFacet() {
        return facet;
    }

    public void setFacet(List<OpenSearchFacet> facet) {
        this.facet = facet;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public Long getViewtotal() {
        return viewtotal;
    }

    public void setViewtotal(Long viewtotal) {
        this.viewtotal = viewtotal;
    }

    public List<OpenSearchField> getItems() {
        return items;
    }

    public void setItems(List<OpenSearchField> items) {
        this.items = items;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "OpenSearchResult{" +
                "total=" + total +
                ", num=" + num +
                ", viewtotal=" + viewtotal +
                ", items=" + items +
                ", error='" + error + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
