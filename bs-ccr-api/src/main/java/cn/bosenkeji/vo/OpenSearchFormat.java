package cn.bosenkeji.vo;

/**
 * @author CAJR
 * @date 2019/11/5 2:31 下午
 */
public class OpenSearchFormat {

    private Object fields;

    private String cmd;

    public Object getFields() {
        return fields;
    }

    public void setFields(Object fields) {
        this.fields = fields;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }
}
