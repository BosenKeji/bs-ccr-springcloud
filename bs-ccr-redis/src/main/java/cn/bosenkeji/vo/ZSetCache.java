package cn.bosenkeji.vo;

import java.io.Serializable;

public class ZSetCache implements Serializable {
    private String key;
    private String value;
    private String unless;
    private Integer score;

    private boolean notOperate;

    public ZSetCache() {
    }

    public ZSetCache(String key, String value, String unless, boolean notOperate) {
        this.key = key;
        this.value = value;
        this.unless = unless;
        this.notOperate = notOperate;
    }

    public ZSetCache(String key, String value, String unless, Integer score, boolean notOperate) {
        this.key = key;
        this.value = value;
        this.unless = unless;
        this.score = score;
        this.notOperate = notOperate;
    }

    public boolean isNotOperate() {
        return notOperate;
    }

    public void setNotOperate(boolean notOperate) {
        this.notOperate = notOperate;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUnless() {
        return unless;
    }

    public void setUnless(String unless) {
        this.unless = unless;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
