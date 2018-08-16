package com.web.demo.elasticjob;

import com.dangdang.ddframe.job.api.AbstractSequencePerpetualElasticJob;
import com.dangdang.ddframe.job.api.JobExecutionSingleShardingContext;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @CLassName SequencePerpetualElasticDemoJob
 * @Author li.np
 * @Date 2018/8/16 下午11:56
 * @Version 1.0
 * @Description
 **/
@Slf4j
public class SequencePerpetualElasticDemoJob extends AbstractSequencePerpetualElasticJob {
    @Override
    protected List fetchData(JobExecutionSingleShardingContext jobExecutionSingleShardingContext) {
        System.out.println("do job3");
        log.debug("elastic-job3  测试3");
        return null;
    }

    @Override
    protected boolean processData(JobExecutionSingleShardingContext jobExecutionSingleShardingContext, Object o) {
        return true;
    }
}
