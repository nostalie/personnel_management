package cn.edu.jlu.personnel.management.enums;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by nostalie on 17-5-28.
 */
public enum OperationType {

    ADD(1),DELETE(2),UPDATE(3);

    private int id;
    private static Map<Integer,OperationType> map = Maps.newHashMap();

    static {
        for(OperationType value : OperationType.values()){
            map.put(value.getId(),value);
        }
    }

    public int code() {
        return this.getId();
    }

    public static OperationType codeOf(int id) {
        return map.get(id);
    }

    public int getId() {
        return id;
    }

    OperationType(int id){
        this.id = id;
    }
}
