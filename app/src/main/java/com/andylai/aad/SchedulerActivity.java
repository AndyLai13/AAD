package com.andylai.aad;

import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class SchedulerActivity extends AppCompatActivity implements View.OnClickListener {

	static final int JOB_ID = 10001;
	JobScheduler mJobScheduler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scheduler);

		findViewById(R.id.start_job).setOnClickListener(this);
		findViewById(R.id.stop_job).setOnClickListener(this);

		mJobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.start_job:
				schedulerJob();
				break;
			case R.id.stop_job:
				cancelJob();
				break;
		}
	}

	void schedulerJob() {
		JobInfo.Builder builder = new JobInfo.Builder(JOB_ID,
				new ComponentName(getPackageName(), JobSchedulerService.class.getName()))
				.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
				.setRequiresCharging(true);
		mJobScheduler.schedule(builder.build());

	}

	void cancelJob() {
		mJobScheduler.cancelAll();
		Toast.makeText(this, "Cancel all job", Toast.LENGTH_SHORT).show();
	}
}
