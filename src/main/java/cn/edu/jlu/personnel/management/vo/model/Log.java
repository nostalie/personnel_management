package cn.edu.jlu.personnel.management.vo.model;

import cn.edu.jlu.personnel.management.enums.OperationType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Created by nostalie on 17-5-28.
 */
public class Log {

    private Integer id;
    private String userName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date operationTime;
    private OperationType operationType;
    private String oldContent;
    private String newContent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public String getOldContent() {
        return oldContent;
    }

    public void setOldContent(String oldContent) {
        this.oldContent = oldContent;
    }

    public String getNewContent() {
        return newContent;
    }

    public void setNewContent(String newContent) {
        this.newContent = newContent;
    }

    @Override
    public String toString() {
        return "Log{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", operationTime=" + operationTime +
                ", operationType=" + operationType +
                ", oldContent='" + oldContent + '\'' +
                ", newContent='" + newContent + '\'' +
                '}';
    }
}
