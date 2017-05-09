package cn.edu.jlu.personnel.management.vo;

import cn.edu.jlu.personnel.management.vo.model.AuthGroup;
import cn.edu.jlu.personnel.management.vo.model.User;

import java.util.List;

/**
 * Created by nostalie on 17-5-5.
 */
public class UserGroups {
    private User user;
    private List<AuthGroup> authGroups;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<AuthGroup> getAuthGroups() {
        return authGroups;
    }

    public void setAuthGroups(List<AuthGroup> authGroups) {
        this.authGroups = authGroups;
    }

    @Override
    public String toString() {
        return "UserGroups{" +
                "user=" + user +
                ", authGroups=" + authGroups +
                '}';
    }
}
