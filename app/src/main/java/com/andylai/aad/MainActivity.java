package com.andylai.aad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static com.andylai.aad.MyContentProvider.CONTENT_URI;
import static com.andylai.aad.MyContentProvider.URL;

public class MainActivity extends AppCompatActivity {

	private String NAME = "name";
	private String NUMBER = "number";



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button button1 = findViewById(R.id.button1);
		Button button2 = findViewById(R.id.button2);
		Button button3 = findViewById(R.id.button3);
		Button button4 = findViewById(R.id.button4);

		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, DatabaseQueryActivity.class));
			}
		});



	}
}
