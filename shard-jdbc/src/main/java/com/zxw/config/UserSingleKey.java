package com.zxw.config;

import io.shardingsphere.core.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.core.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

import java.util.Collection;

/**
 * @author zxw
 * @date 2019/8/30 20:11
 */
public class UserSingleKey implements PreciseShardingAlgorithm<Long> {
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {
        for (String tableName : collection) {
            if (tableName.endsWith(preciseShardingValue.getValue() % 4 + "")) {
                return tableName;
            }
        }
        throw new IllegalArgumentException();
    }
}
