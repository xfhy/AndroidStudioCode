package com.xfhy.retrofittest.model;

/**
 * Created by xfhy on 2017/8/5.
 */

public class AddressResponse {

    private String status;
    private String ok;
    private Result result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOk() {
        return ok;
    }

    public void setOk(String ok) {
        this.ok = ok;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "AddressResponse{" +
                "status='" + status + '\'' +
                ", ok='" + ok + '\'' +
                ", result=" + result +
                '}';
    }
}
