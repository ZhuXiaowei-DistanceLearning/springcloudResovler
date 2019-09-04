package com.fangjia.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

public class MySimpleJob implements SimpleJob {
    @Override
    public void execute(ShardingContext shardingContext) {
        String parameter = shardingContext.getShardingParameter();
        System.out.println("分片参数:" + shardingContext);
        int value = Integer.parseInt(parameter);
        System.out.println(value);
//        for (int i = 0; i < 1000000; i++) {
//            if (i % 2 == 0) {
//                String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
//                System.out.println(time + ":开始执行简单任务" + i);
//            }
//        }
    }

	/*public void execute(ShardingContext context) {
		String shardParamter = context.getShardingParameter();
		System.out.println("分片参数："+shardParamter);
		int value = Integer.parseInt(shardParamter);
		for (int i = 0; i < 1000000; i++) {
			if (i % 2 == value) {
				String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
				System.out.println(time + ":开始执行简单任务" + i);
			}
		}
	}*/

}
