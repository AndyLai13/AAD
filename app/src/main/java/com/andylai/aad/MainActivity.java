package com.andylai.aad;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

	Button mBtnQueryData;
	Button mBtnRecycler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mBtnQueryData = findViewById(R.id.query_data);
		mBtnRecycler = findViewById(R.id.recycler);
		mBtnQueryData.setOnClickListener(this);
		mBtnRecycler.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
			case R.id.query_data:
				intent.setComponent(new ComponentName(this, QueryDataActivity.class));
				break;
			case R.id.recycler:
				intent.setComponent(new ComponentName(this, RecyclerActivity.class));
				break;
		}
		startActivity(intent);
	}
}