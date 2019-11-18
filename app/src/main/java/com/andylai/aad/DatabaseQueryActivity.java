package com.andylai.aad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static com.andylai.aad.MyContentProvider.CONTENT_URI;

public class DatabaseQueryActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_database_query);
		setContentView(R.layout.activity_main);

		final EditText name = findViewById(R.id.name);
		final EditText number = findViewById(R.id.number);
		Button btnSave = findViewById(R.id.save);

		btnSave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ContentValues values = new ContentValues();
				values.put(MyContentProvider.NAME, name.getText().toString());
				values.put(MyContentProvider.GRADE, number.getText().toString());

				Uri uri = getContentResolver().insert(CONTENT_URI, values);

			}
		});

	}
}
