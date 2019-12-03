package com.andylai.aad;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {

	class MyBinder extends Binder {
		public void startDownload() {
			testMethod();
			Log.d("Andy", "startDownLoad");
		}
	}

	void testMethod() {
		Log.d("Andy", "testMethod");

	}

	MyBinder mBinder = new MyBinder();

	public MyService() {
	}

	@Override
	public IBinder onBind(Intent intent) {
		Log.d("Andy", "onBind");

		return mBinder;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Log.d("Andy", "onUnbind");

		return super.onUnbind(intent);
	}

	@Override
	public void onCreate() {
		Log.d("Andy", "onCreate");

		super.onCreate();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d("Andy", "onStartCommand");

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		Log.d("Andy", "onDestroy");

		super.onDestroy();
	}
}
