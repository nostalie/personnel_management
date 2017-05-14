package cn.edu.jlu.personnel.management.vo;

import java.text.MessageFormat;

/**
 * Created by nostalie on 17-5-12.
 */
public class ResponseResult implements CodeMessage {

    private int status;
    private String message;
    private Object data;

    private ResponseResult(int status,String message,Object data){
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static ResponseResult ok(Object data){
        return new ResponseResult(OK,"success",data);
    }

    public static ResponseResult error(String format,Object... args){
        String message = MessageFormat.format(format,args);
        return new ResponseResult(SYSTEM_ERROR,message,null);
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }
}
