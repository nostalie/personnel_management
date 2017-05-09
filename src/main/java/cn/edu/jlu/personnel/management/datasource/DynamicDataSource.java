package cn.edu.jlu.personnel.management.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 根据数据库的上下文信息，动态切换主从数据库的数据源
 * Created by nostalie on 17-5-6.
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContext.getDataSourceContext();
    }
}
