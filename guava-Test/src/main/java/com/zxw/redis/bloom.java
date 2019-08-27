package com.zxw.redis;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 * @author zxw
 * @date 2019/8/27 20:43
 */
public class bloom {
    public static void main(String[] args) {
        int total = 10000;
        BloomFilter<String> bf = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), total,0.001);
        for (int i = 0; i < total; i++) {
            bf.put("" + i);
        }
        int count = 0;
        for (int i = 0; i < total + 10000; i++) {
            if (bf.mightContain("" + i)) {
                count++;
            }
        }
        System.out.println("匹配数量:" + count);
    }
}
