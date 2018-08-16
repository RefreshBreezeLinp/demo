package com.web.demo.elasticjob;

import com.dangdang.ddframe.job.api.AbstractOneOffElasticJob;
import com.dangdang.ddframe.job.api.JobExecutionMultipleShardingContext;
import lombok.extern.slf4j.Slf4j;

import javax.sound.midi.Soundbank;

/**
 * @CLassName OneOffElasticDemoJob
 * @Author li.np
 * @Date 2018/8/16 下午11:53
 * @Version 1.0
 * @Description
 **/
@Slf4j
public class OneOffElasticDemoJob extends AbstractOneOffElasticJob {
    @Override
    protected void process(JobExecutionMultipleShardingContext jobExecutionMultipleShardingContext) {
        System.out.println("do job1");
        log.debug("elastic-job1 测试1");
    }
}
