package com.hgw.baseframe.bean;

/**
 * 描述：http请求返回的最外层对象
 * @author hgw
 */
public class BaseResponse<T> {
    /** 响应结果码. */
    private int errorCode;
    /** 提示信息. */
    private String errorMsg;
    /** 具体的内容. */
    private T data;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
