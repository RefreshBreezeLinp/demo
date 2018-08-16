package com.web.demo.elasticjob;

import com.dangdang.ddframe.job.api.JobConfiguration;
import com.dangdang.ddframe.job.schedule.JobController;
import com.dangdang.ddframe.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.reg.zookeeper.ZookeeperRegistryCenter;

/**
 * @CLassName JobDemo
 * @Author li.np
 * @Date 2018/8/16 下午11:33
 * @Version 1.0
 * @Description
 **/
public class JobDemo {

    /**
     * 定义注册中心配置对象
     */
    private ZookeeperConfiguration zkConfiguration = new ZookeeperConfiguration("localhost:2181","elastic-job-example",1000,3000,3);

    /**
     * 定义zookeeper注册中心
     */
    private CoordinatorRegistryCenter registryCenter = new ZookeeperRegistryCenter(zkConfiguration);

    /**
     * 任务1
     */
    private JobConfiguration job1 = new JobConfiguration("job1",OneOffElasticDemoJob.class,10,"0/5 * * * * ?");

    /**
     * 任务2
     */
    private JobConfiguration job2 = new JobConfiguration("job2",PerpetualElasticDemoJob.class,10,"0/5 * * * * ?");

    /**
     * 任务3
     */
    private JobConfiguration job3 = new JobConfiguration("job3",SequencePerpetualElasticDemoJob.class,10,"0/5 * * * * ?");

    private void init(){
        //初始化注册中心
        registryCenter.init();
        //启动作业1
        new JobController(registryCenter,job1).init();
        //启动作业2
        new JobController(registryCenter,job2).init();
        //启动作业3
        new JobController(registryCenter,job3).init();

    }

    public static void main(String[] args) {
        new JobDemo().init();
    }
}
