package cn.edu.jlu.personnel.management.dao;

import cn.edu.jlu.personnel.management.vo.model.Log;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by nostalie on 17-5-28.
 */
@Repository
public interface LogDao {

    int insertLog(Log log);

    List<Log> selectLogs();
}
