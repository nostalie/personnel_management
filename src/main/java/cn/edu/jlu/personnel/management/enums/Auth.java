package cn.edu.jlu.personnel.management.enums;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

import java.util.Arrays;

/**
 * 定义基础权限类型
 * Created by nostalie on 17-5-10.
 */
public enum Auth {

    ORDINARY(1,"普通用户权限，仅能查看个人信息"),BU_LEADER(2,"部门leader，可管理部门下的人员信息"),SUPER_LEADER(4,"可以管理所有部门、所有员工"),
    GROUP_AUTH(8,"可以创建权限组"),USER_AUTH(16,"可以为员工分配权限组");

    private int value;
    private String description;

    Auth(int value,String description){
        this.value = value;
        this.description = description;
    }

    public static int blend(Auth... args){
        int result = 0;
        for(Auth auth : args){
            Preconditions.checkNotNull(auth);
            result = result | auth.getValue();
        }
        return result;
    }

    public static boolean isContain(final int value, Auth... args){
        boolean b1 = Iterables.all(Arrays.asList(args), new Predicate<Auth>() {
            public boolean apply(Auth auth) {
                return auth != null && (value & auth.getValue()) != 0;
            }
        });
        boolean b2 = args.length != 0;
        return b1 && b2;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
