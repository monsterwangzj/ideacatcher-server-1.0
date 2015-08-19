package com.softwisdom.ideacatcher.result;

import java.io.Serializable;

public class CommonJsonResult implements Serializable {

    private static final long serialVersionUID = 1584707676375975649L;

    private boolean success = true;

    private int code;

    private String desc;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
