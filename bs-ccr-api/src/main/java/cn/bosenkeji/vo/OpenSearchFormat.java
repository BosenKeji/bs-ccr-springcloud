package cn.bosenkeji.vo;

/**
 * @author CAJR
 * @date 2019/11/5 2:31 下午
 */
public class OpenSearchFormat<T> {

    private T fields;

    private String cmd;

    public T getFields() {
        return fields;
    }

    public void setFields(T fields) {
        this.fields = fields;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }
}
