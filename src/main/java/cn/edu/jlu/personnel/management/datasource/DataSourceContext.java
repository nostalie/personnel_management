package cn.edu.jlu.personnel.management.datasource;

import cn.edu.jlu.personnel.management.enums.DataSourceType;

/**
 * 保存数据库的上下文信息
 * Created by nostalie on 17-5-6.
 */
public class DataSourceContext {

    private static final ThreadLocal<String> dataSourceContext = new ThreadLocal<String>();

    public static void setDataSourceContext(String context){
        dataSourceContext.set(context);
    }

    static String getDataSourceContext(){
        return dataSourceContext.get();
    }
}
