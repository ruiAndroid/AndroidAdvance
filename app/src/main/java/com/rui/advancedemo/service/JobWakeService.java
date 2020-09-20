package com.rui.advancedemo.service;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Message;
import android.support.annotation.RequiresApi;

/**
 * 5.0以上才有
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class JobWakeService extends JobService {

    private int jobId=1;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        JobInfo.Builder builder=new JobInfo.Builder(jobId,new ComponentName(this,JobWakeService.class));
        JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(builder.build());

        return START_STICKY;
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        //开启定时任务，定时轮询，看messageService有咩有被杀死
        //如果杀死了启动 轮询jobService

        //判断服务有没有在运行
        boolean messageServiceAlive=aliveService();
        if(!messageServiceAlive){
            startService(new Intent(this, MessageService.class));
        }
        return false;
    }


    private boolean aliveService() {

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
