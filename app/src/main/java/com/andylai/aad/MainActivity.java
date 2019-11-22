package com.andylai.aad;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewById(R.id.query_data).setOnClickListener(this);
		findViewById(R.id.recycler).setOnClickListener(this);
		findViewById(R.id.my_action_bar).setOnClickListener(this);
		findViewById(R.id.my_action_bar).performClick();
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
			case R.id.my_action_bar:
				intent.setComponent(new ComponentName(this, MyActionBarActivity.class));
				break;
		}
		startActivity(intent);
	}
}