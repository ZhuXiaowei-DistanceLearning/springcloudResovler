package com.zxw.pojo;

public class JWTResult {
    private boolean status;
    private String uid;
    private String msg;
    private int code;

    public JWTResult() {
        super();
    }

    public JWTResult(boolean status, String uid, String msg, int code) {
        super();
        this.status = status;
        this.uid = uid;
        this.msg = msg;
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}