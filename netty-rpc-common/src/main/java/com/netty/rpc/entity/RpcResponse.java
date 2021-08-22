package com.netty.rpc.entity;

/**
 * @author hdz
 * @description TODO
 * @create 2021年08月17日 19:43
 */
public class RpcResponse {
    private String requestId;
    private String error;   //考虑换成Exception
    private Object result;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
