package cn.edu.jlu.personnel.management.vo;

/**
 * Created by nostalie on 17-5-12.
 */
public interface CodeMessage {
    int OK = 0;
    int SYSTEM_ERROR = -1;

    int getStatus();

    String getMessage();

    Object getData();
}
