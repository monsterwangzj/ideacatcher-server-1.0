package com.softwisdom.ideacatcher.result;

public class LoginJsonResult extends CommonJsonResult {

    private static final long serialVersionUID = -6228053756052529463L;

    private String loginToken;

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }
}
