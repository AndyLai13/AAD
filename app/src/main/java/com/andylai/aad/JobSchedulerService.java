package com.andylai.aad;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.io.InputStream;
import java.net.URL;

public class JobSchedulerService extends JobService {
	public JobSchedulerService() {
	}

	@Override
	public boolean onStartJob(JobParameters params) {
		Log.d("Andy", "onStartJob");
		mJobHandler.sendMessage(Message.obtain(mJobHandler, 1, params));
		return true;
	}

	@Override
	public boolean onStopJob(JobParameters params) {
		Log.d("Andy", "onStopJob");

		mJobHandler.removeMessages(1);
		return false;
	}

	private Handler mJobHandler = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(@NonNull Message msg) {
			Log.d("Andy", "handleMessage");

			Toast.makeText(getApplicationContext(), "JobService task is running",
					Toast.LENGTH_SHORT).show();
			jobFinished((JobParameters) msg.obj, false);
			return false;
		}
	});

}
