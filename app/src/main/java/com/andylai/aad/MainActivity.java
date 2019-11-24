package com.andylai.aad;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewById(R.id.query_data).setOnClickListener(this);
		findViewById(R.id.recycler).setOnClickListener(this);
		findViewById(R.id.job_scheduler).setOnClickListener(this);
		findViewById(R.id.async_task).setOnClickListener(this);

		findViewById(R.id.async_task).performClick();
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		ComponentName comp;
		switch (v.getId()) {
			case R.id.query_data:
				comp = new ComponentName(this, QueryDataActivity.class);
				break;
			case R.id.recycler:
				comp = new ComponentName(this, RecyclerActivity.class);
				break;
			case R.id.job_scheduler:
				comp = new ComponentName(this, SchedulerActivity.class);
				break;
			case R.id.async_task:
				comp = new ComponentName(this, AsyncTaskActivity.class);
				break;
			default:
				comp = null;
		}

		if (comp != null) {
			intent.setComponent(comp);
			startActivity(intent);
		}
	}
}