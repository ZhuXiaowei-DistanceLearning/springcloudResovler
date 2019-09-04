package com.zxw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author zxw
 * @date 2019/8/29 20:01
 */
//@EnableConfigurationProperties(ShardingMasterSlaveConfig.class)
@MapperScan(basePackages = "com.zxw.service")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
//@ConditionalOnProperty({"sharding.jdbc.data-sources.ds_0.jdbc-url","sharding.jdbc.master-slave-rule.master-data-source-name"})
public class JdbcApplication {
    public static void main(String[] args) {
        SpringApplication.run(JdbcApplication.class);
    }

    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // TODO Auto-generated method stub
        return builder.sources(JdbcApplication.class);
    }

  /*  @Autowired
    private ShardingMasterSlaveConfig shardingMasterSlaveConfig;


    @Bean
    public DataSource masterSlaveDataSource() throws SQLException {
        final Map<String, DataSource> dataSourceMap = Maps.newHashMap();
        dataSourceMap.putAll(shardingMasterSlaveConfig.getDataSources());
        final Map<String, Object> newHashMap = Maps.newHashMap();
        // 创建 MasterSlave数据源
        DataSource dataSource = MasterSlaveDataSourceFactory.createDataSource(dataSourceMap,
                shardingMasterSlaveConfig.getMasterSlaveRule(), newHashMap);
        return dataSource;
    }*/
}
