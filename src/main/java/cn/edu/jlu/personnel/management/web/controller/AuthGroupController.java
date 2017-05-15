package cn.edu.jlu.personnel.management.web.controller;

import cn.edu.jlu.personnel.management.service.auth.AuthGroupService;
import cn.edu.jlu.personnel.management.vo.model.AuthGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by nostalie on 17-5-15.
 */
@Controller
public class AuthGroupController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthGroupController.class);

    @Resource
    private AuthGroupService authGroupService;

    @RequestMapping("/super/auth/group/add")
    public void addGroup(AuthGroup authGroup){
        authGroupService.addGroup(authGroup);
    }

    @RequestMapping("/super/auth/group/list")
    public List<AuthGroup> queryAllGroup(){
        return authGroupService.queryAllGroup();
    }

    @RequestMapping("/super/auth/group/query/id")
    public AuthGroup queryGroupById(Integer id){
        return authGroupService.queryGroupById(id);
    }

    @RequestMapping("/super/auth/group/update")
    public void updateGroup(AuthGroup authGroup){
        authGroupService.updateGroup(authGroup);
    }

    @RequestMapping("/super/auth/group/delete")
    public void deleteGroup(AuthGroup authGroup){
        authGroupService.deleteGroup(authGroup);
    }
}
