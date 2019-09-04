package com.zxw.config;

import com.zaxxer.hikari.HikariDataSource;
import io.shardingsphere.core.api.config.MasterSlaveRuleConfiguration;

import java.util.HashMap;
import java.util.Map;

public class ShardingMasterSlaveConfig {

    // 存放本地多个数据源   最终放在map集合中   key为yml配置的 ds_slave_0
    private Map<String, HikariDataSource> dataSources = new HashMap<>();

    private MasterSlaveRuleConfiguration masterSlaveRule;

    public Map<String, HikariDataSource> getDataSources() {
        return dataSources;
    }

    public void setDataSources(Map<String, HikariDataSource> dataSources) {
        this.dataSources = dataSources;
    }

    public MasterSlaveRuleConfiguration getMasterSlaveRule() {
        return masterSlaveRule;
    }

    public void setMasterSlaveRule(MasterSlaveRuleConfiguration masterSlaveRule) {
        this.masterSlaveRule = masterSlaveRule;
    }
}