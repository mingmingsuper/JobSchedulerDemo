package com.lmm.jobschedulerdemo;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lmm.jobschedulerdemo.service.JobSchedulerService;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            doService();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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
}
