package cn.edu.jlu.personnel.management.vo.model;

import java.util.Date;

/**
 * Created by nostalie on 17-5-5.
 */
public class UserAuth {
    private Integer id;
    private Integer userId;
    private Integer authId;
    private Date createTime;
    private Date updateTime;
    private User user;
    private AuthGroup authGroup;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAuthId() {
        return authId;
    }

    public void setAuthId(Integer authId) {
        this.authId = authId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public AuthGroup getAuthGroup() {
        return authGroup;
    }

    public void setAuthGroup(AuthGroup authGroup) {
        this.authGroup = authGroup;
    }

    @Override
    public String toString() {
        return "UserAuth{" +
                "id=" + id +
                ", userId=" + userId +
                ", authId=" + authId +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", user=" + user +
                ", authGroup=" + authGroup +
                '}';
    }
}
