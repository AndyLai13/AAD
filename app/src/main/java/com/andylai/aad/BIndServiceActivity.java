package com.andylai.aad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;

public class BIndServiceActivity extends AppCompatActivity {

	MyService.MyBinder mService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bind_service);
		bindService(new Intent(this, MyService.class), connection,Context.BIND_AUTO_CREATE);
	}

	ServiceConnection connection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mService = (MyService.MyBinder) service;
			mService.startDownload();
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {

		}
	};
}
