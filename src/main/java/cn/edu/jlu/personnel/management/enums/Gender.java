package cn.edu.jlu.personnel.management.enums;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by nostalie on 17-5-5.
 */
public enum Gender {

    MEN(0, "男"), WONMEN(1, "女");

    private int id;
    private String description;
    private static final Map<Integer, Gender> map = Maps.newHashMap();

    static {
        for (Gender gender : Gender.values()) {
            map.put(gender.getId(), gender);
        }
    }

    public int code() {
        return this.getId();
    }

    public static Gender codeOf(int id) {
        return map.get(id);
    }

    Gender(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
