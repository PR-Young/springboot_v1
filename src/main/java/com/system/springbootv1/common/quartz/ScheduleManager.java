package com.system.springbootv1.common.quartz;

import com.system.springbootv1.model.SysQuartzJob;
import com.system.springbootv1.service.SysQuartzJobService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: yy 2020/01/29
 **/
@Component
public class ScheduleManager {

    private static Logger log = LoggerFactory.getLogger(ScheduleManager.class);
    @Autowired
    private Scheduler scheduler;
    @Autowired
    private SysQuartzJobService sysQuartzJobService;

    @PostConstruct
    public void init() {
        List<SysQuartzJob> jobList = sysQuartzJobService.list();
        if (null != jobList && jobList.size() > 0) {
            for (SysQuartzJob job : jobList) {
                try {
                    if (ScheduleConstants.Status.NORMAL.getValue().equals(job.getStatus())) {
                        if (checkJobExist(job)) {
                            delete(createTaskName(job.getJobName()), job.getJobGroup());
                        }
                        addJob(job);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 获取定时任务的具体执行类
     *
     * @param sysJob
     * @return
     */
    private static Class<? extends Job> getQuartzJobClass(SysQuartzJob sysJob) {
        boolean isConcurrent = "0".equals(sysJob.getConCurrent());
        return isConcurrent ? QuartzJobExecution.class : QuartzDisallowConcurrentExecution.class;
    }

    public void addJob(SysQuartzJob job) {
        addJob(createTaskName(job.getJobName()),
                job.getJobGroup(),
                getQuartzJobClass(job),
                false,
                job.getCronExpression(),
                10,
                job);

    }

    public void edit(SysQuartzJob job) {
        modify("1",
                createTaskName(job.getJobName()),
                job.getJobGroup(),
                createTaskName(job.getJobName()) + ScheduleConstants.TRIGGER,
                job.getJobGroup() + ScheduleConstants.TRIGGER_GROUP,
                job.getCronExpression(),
                job);
    }

    /**
     * 使用SimpleScheduleBuilder创建一个定时任务
     *
     * @param jobName      job名称
     * @param jobGroupName job分组名称
     * @param jobClass     job的class名
     * @param durability   是否持久化
     * @param interval     间隔时间，单位秒
     * @param delay        延迟启动时间，单位秒
     * @return
     */
    public Boolean addJob(String jobName, String jobGroupName, Class jobClass, boolean durability, int interval, int delay, SysQuartzJob job) {
        try {
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).storeDurably(durability).build();
            SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(interval).repeatForever();
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(jobName + ScheduleConstants.TRIGGER, jobGroupName + ScheduleConstants.TRIGGER_GROUP)
                    .withSchedule(scheduleBuilder)
                    .startAt(new Date(new Date().getTime() + delay * 1000)).build();
            jobDetail.getJobDataMap().put(ScheduleConstants.TASK_PROPERTIES, job);
            scheduler.scheduleJob(trigger);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 使用CronScheduleBuilder创建一个定时任务
     *
     * @param jobName        job名称
     * @param jobGroupName   job分组名称
     * @param jobClass       job的class名
     * @param durability     是否持久化
     * @param cronExpression 定时表达式
     * @param delay          延迟启动时间，单位秒
     * @return
     */
    public Boolean addJob(String jobName, String jobGroupName, Class jobClass,
                          boolean durability, String cronExpression, int delay, SysQuartzJob job) {
        /*
         * 1.取到任务调度器Scheduler
         * 2.定义jobDetail;
         * 3.定义trigger;
         * 4.使用Scheduler添加任务;
         */
        try {
            JobDetail jobDetail = JobBuilder.newJob(jobClass).
                    withDescription(job.getDescription()).
                    withIdentity(jobName, jobGroupName).
                    storeDurably(durability).build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withDescription(job.getDescription())
                    .withIdentity(jobName + ScheduleConstants.TRIGGER, jobGroupName + ScheduleConstants.TRIGGER_GROUP)
                    .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                    .startAt(new Date(new Date().getTime() + delay * 1000))
                    .build();

            jobDetail.getJobDataMap().put(ScheduleConstants.TASK_PROPERTIES, job);
            scheduler.scheduleJob(jobDetail, trigger);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 动态停止job任务
     *
     * @param jobName      job名称
     * @param jobGroupName job分组名称
     * @return
     */
    public Boolean pause(String jobName, String jobGroupName) {
        try {
            JobKey key = new JobKey(jobName, jobGroupName);
            scheduler.pauseJob(key);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 动态开始job任务
     *
     * @param jobName      job名称
     * @param jobGroupName job分组名称
     * @return
     */
    public Boolean start(String jobName, String jobGroupName) {
        try {
            JobKey key = new JobKey(jobName, jobGroupName);
            scheduler.resumeJob(key);
            return true;
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 移除定时任务
     *
     * @param jobName      job名称
     * @param jobGroupName job分组名称
     * @return
     */
    public Boolean delete(String jobName, String jobGroupName) {
        try {
            Integer status = getJobStatus(jobName + ScheduleConstants.TRIGGER, jobGroupName + ScheduleConstants.TRIGGER_GROUP);
            if (status == 0) {
                //NONE - 0,该job不存在
                log.warn("NONE - 0,该job不存在");
                return null;
            }

            TriggerKey triggerKey = TriggerKey.triggerKey(jobName + ScheduleConstants.TRIGGER, jobGroupName + ScheduleConstants.TRIGGER_GROUP);//通过触发器名和组名获取TriggerKey
            JobKey jobKey = new JobKey(jobName, jobGroupName); //通过任务名和组名获取JobKey
            scheduler.pauseTrigger(triggerKey);    //停止触发器
            scheduler.unscheduleJob(triggerKey);//移除触发器
            scheduler.deleteJob(jobKey);// 删除任务
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 动态修改任务执行的时间
     *
     * @param type             创建类型，1-cron,2-simple
     * @param jobName          job名称
     * @param jobGroupName     job分组名称
     * @param triggerName      触发器名称
     * @param triggerGroupName 触发器分组名称
     * @param time             type为1时-填cron表达式，为2-整数
     * @return
     */
    public Boolean modify(String type, String jobName, String jobGroupName, String triggerName, String triggerGroupName, String time,SysQuartzJob job) {

        try {
            Integer status = getJobStatus(triggerName, triggerGroupName);
            if (status == 0) {
                //NONE - 0,该job不存在
                log.warn("NONE - 0,该job不存在");
                return null;
            }

            //获取任务
            JobKey key = new JobKey(jobName, jobGroupName);
            //获取jobDetail
            JobDetail jobDetail = scheduler.getJobDetail(key);
            //生成trigger
            if ("1".equals(type)) {
                // 1.CronSchedule
                Trigger trigger = TriggerBuilder
                        .newTrigger()
                        .withDescription(job.getDescription())
                        .withIdentity(triggerName, triggerGroupName)
                        .withSchedule(CronScheduleBuilder.cronSchedule(time))
                        .build();
                //删除旧的任务，否则报错
                //scheduler.deleteJob(key);
                delete(jobName, jobGroupName);
                //重新启动任务
                scheduler.scheduleJob(jobDetail, trigger);
                return true;
            }

            if ("2".equals(type)) {
                // 2.SimpleSchedule
                Trigger trigger = TriggerBuilder
                        .newTrigger()
                        .withDescription(job.getDescription())
                        .withIdentity(triggerName, triggerGroupName)
                        .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(Integer.valueOf(time)))
                        .build();
                //删除旧的任务，否则报错
                //scheduler.deleteJob(key);
                delete(jobName, jobGroupName);
                //重新启动任务
                scheduler.scheduleJob(jobDetail, trigger);
                return true;
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 查看job运行状态
     *
     * @param triggerName      触发器名称
     * @param triggerGroupName 触发器分组名称
     * @return TriggerState枚举值： NONE - 0；NORMAL - 1；PAUSED - 2；COMPLETE - 3；ERROR - 4；BLOCKED - 5
     */
    public Integer getJobStatus(String triggerName, String triggerGroupName) {

        /*
            TriggerState枚举值
            NONE - 0,
            NORMAL -1,
            PAUSED - 2,
            COMPLETE - 3,
            ERROR - 4,
            BLOCKED - 5;
        */
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            return scheduler.getTriggerState(triggerKey).ordinal();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 启动所有定时任务
     */
    public Boolean startAll() {
        try {
            scheduler.start();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 关闭所有定时任务
     */
    public Boolean shutdownAll() {
        try {
            if (!scheduler.isShutdown()) {
                scheduler.shutdown();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkJobExist(SysQuartzJob job) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(createTaskName(job.getJobName()) + ScheduleConstants.TRIGGER, job.getJobGroup() + ScheduleConstants.TRIGGER_GROUP);
        return scheduler.checkExists(triggerKey);
    }

    /**
     * 立即执行任务
     */
    public void run(SysQuartzJob job) throws SchedulerException {
        // 参数
        JobDataMap dataMap = new JobDataMap();
        dataMap.put(ScheduleConstants.TASK_PROPERTIES, job);
        scheduler.triggerJob(getJobKey(job), dataMap);
    }

    /**
     * 获取jobKey
     */
    public JobKey getJobKey(SysQuartzJob job) {
        return JobKey.jobKey(createTaskName(job.getJobName()), job.getJobGroup());
    }

    public String createTaskName(String key) {
        return ScheduleConstants.TASK_CLASS_NAME + key;
    }
}
