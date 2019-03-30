package com.bdilab.sharedcalendar.common.response;

public class MetaData {
    /**
     * 是否成功的标识
     */
    private Boolean success;

    /**
     * 返回码
     */
    private String code;

    /**
     * 返回消息
     */
    private String message;

    public MetaData(){

    }

    public MetaData(boolean success,String code,String message){
        this.success=success;
        this.code=code;
        this.message=message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
