package cn.bosenkeji.vo.transaction;

public class OpenSearchExecuteResult {

    private String status;
    private String request_id;
    private OpenSearchResult result;

    public OpenSearchResult getResult() {
        return result;
    }

    public void setResult(OpenSearchResult result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }
}
