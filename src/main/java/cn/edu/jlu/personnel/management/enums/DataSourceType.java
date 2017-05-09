package cn.edu.jlu.personnel.management.enums;

/**
 * Created by nostalie on 17-5-6.
 */
public enum DataSourceType {
    MASTER("master"),SLAVE("slave");

    private String name;

    DataSourceType(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
