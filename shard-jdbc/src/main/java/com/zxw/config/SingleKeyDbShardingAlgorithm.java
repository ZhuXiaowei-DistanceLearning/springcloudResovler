package com.zxw.config;

import io.shardingsphere.core.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.core.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zxw
 * @date 2019/8/30 21:02
 */
public class SingleKeyDbShardingAlgorithm implements PreciseShardingAlgorithm<String> {
    private static Map<String, List<String>> shardingMap =
            new ConcurrentHashMap<>();

    static {
        shardingMap.put("ds_0", Arrays.asList("上海"));
        shardingMap.put("ds_l", Arrays.asList(" 杭州"));
    }


    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<String> preciseShardingValue) {
        Collection<String> result = new LinkedHashSet<>(collection.size());
        for (String s : collection) {
            if (preciseShardingValue.getValue().equals("杭州")) {
                return "ds-master";
            } else {
                return "ds-slave";
            }
        }
        throw new IllegalArgumentException();
    }
}
