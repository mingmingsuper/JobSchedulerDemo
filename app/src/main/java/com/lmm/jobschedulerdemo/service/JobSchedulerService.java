package com.lmm.jobschedulerdemo.service;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class JobSchedulerService extends JobService {

    @Override
    public boolean onStartJob(JobParameters params) {
        doJob();
        doService();
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    private void doService() {
        JobScheduler jobScheduler = (JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);
        JobInfo.Builder builder = new JobInfo.Builder(1, new ComponentName(this,JobSchedulerService.class));
        builder.setMinimumLatency(TimeUnit.MILLISECONDS.toMillis(10));
        builder.setOverrideDeadline(TimeUnit.MILLISECONDS.toMillis(15));
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_NOT_ROAMING);
        builder.setBackoffCriteria(TimeUnit.MILLISECONDS.toMillis(10),JobInfo.BACKOFF_POLICY_LINEAR);
        builder.setRequiresCharging(true);
        jobScheduler.schedule(builder.build());
    }

    private void doJob() {
        Toast.makeText(getApplicationContext(), "测试", Toast.LENGTH_SHORT).show();
    }

    private Handler mJobHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Toast.makeText(getApplicationContext(), "JobService task Running", Toast.LENGTH_SHORT).show();
            return true;
        }
    });
}