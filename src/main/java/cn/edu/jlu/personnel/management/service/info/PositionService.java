package cn.edu.jlu.personnel.management.service.info;

import cn.edu.jlu.personnel.management.dao.PositionDao;
import cn.edu.jlu.personnel.management.vo.model.Position;
import cn.edu.jlu.personnel.management.vo.model.User;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by nostalie on 17-5-10.
 */
@Service
public class PositionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PositionService.class);

    @Resource
    private PositionDao positionDao;
    @Resource
    private UserService userService;

    public List<Position> queryPositions(){
        try {
            return positionDao.selectPositions();
        } catch (Exception e) {
            LOGGER.error("查询所有职位失败");
            throw new RuntimeException(e);
        }
    }

    public Optional<Position> queryPositionById(Integer positionId){
        try {
            Preconditions.checkNotNull(positionId);
            Position position = positionDao.selectPositionById(positionId);
            return Optional.fromNullable(position);
        } catch (Exception e) {
            LOGGER.error("查询职位失败，positionId is: {}",positionId);
            throw new RuntimeException(e);
        }
    }

    public int addPosition(Position position){
        try {
            Preconditions.checkNotNull(position);
            return positionDao.insertPosition(position);
        } catch (Exception e) {
            LOGGER.error("添加职位失败，position is: {}",position);
            throw new RuntimeException(e);
        }
    }

    public int updatePosition(Position position){
        try {
            Preconditions.checkNotNull(position);
            return positionDao.updatePosition(position);
        } catch (Exception e) {
            LOGGER.error("更新职位信息失败，position is: {}",position);
            throw new RuntimeException(e);
        }
    }

    public int deletePosition(Integer positionId){
        try {
            Preconditions.checkNotNull(positionId);
            User user = new User();
            user.setPositionId(positionId);
            List<User> users = userService.queryUsers(user, RowBounds.DEFAULT);
            if(!users.isEmpty()){
                throw new Exception("该职位下仍然存在用户，无法删除");
            }
            return positionDao.deletePosition(positionId);
        } catch (Exception e) {
            LOGGER.error("删除职位失败,positionId is: {}",positionId,e);
            throw new RuntimeException(e);
        }
    }
}
