package com.web.demo.elasticjob;

import com.dangdang.ddframe.job.api.AbstractPerpetualElasticJob;
import com.dangdang.ddframe.job.api.JobExecutionMultipleShardingContext;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @CLassName PerpetualElasticDemoJob
 * @Author li.np
 * @Date 2018/8/16 下午11:55
 * @Version 1.0
 * @Description
 **/
@Slf4j
public class PerpetualElasticDemoJob extends AbstractPerpetualElasticJob {
    @Override
    protected List fetchData(JobExecutionMultipleShardingContext jobExecutionMultipleShardingContext) {
        System.out.println("do job2");
        log.debug("elastic-job2 测试2");
        return null;
    }

    @Override
    protected boolean processData(JobExecutionMultipleShardingContext jobExecutionMultipleShardingContext, Object o) {
        return true;
    }
}
