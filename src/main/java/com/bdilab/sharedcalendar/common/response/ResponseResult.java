package com.bdilab.sharedcalendar.common.response;

public class ResponseResult {
    /**
     * 后端返回给前段的数据
     */
    private Object data;

    /**
     * 后端返回给前段的元数据，包括成功标识、状态码和消息
     */
    private MetaData meta;

    public ResponseResult(){

    }

    public ResponseResult(boolean success,String code,String message){

        this.meta=new MetaData(success,code,message);
    }

    public ResponseResult(boolean success,String code,String message,Object data){

        this(success, code, message);
        this.data=data;
    }

    public void setCode(String code){
        if(this.meta==null){
            this.meta = new MetaData();
        }
        this.meta.setCode(code);
    }

    public void setMessage(String message){
        if(this.meta == null){
            this.meta = new MetaData();
        }
        this.meta.setMessage(message);
    }

    public void setSuccess(Boolean success){
        if(this.meta == null){
            this.meta = new MetaData();
        }
        this.meta.setSuccess(success);
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public MetaData getMeta() {
        return meta;
    }

    public void setMeta(MetaData meta) {
        this.meta = meta;
    }
}
