package com.andylai.aad;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import static com.andylai.aad.MyContentProvider.CONTENT_URI;
import static com.andylai.aad.MyContentProvider.NAME;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

	EditText mName;
	EditText mGrade;
	EditText mId;
	Button mBtnAdd;
	Button mBtnRetrieve;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mName = findViewById(R.id.name);
		mGrade = findViewById(R.id.number);
		mId = findViewById(R.id.identifier);

		mBtnAdd = findViewById(R.id.add);
		mBtnRetrieve = findViewById(R.id.retrieve);
		mBtnAdd.setOnClickListener(this);
		mBtnRetrieve.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.add:
				ContentValues values = new ContentValues();
				values.put(NAME, mName.getText().toString());
				values.put(MyContentProvider.GRADE, mGrade.getText().toString());
				Uri uri = getContentResolver().insert(MyContentProvider.CONTENT_URI, values);
				mName.setText("");
				mGrade.setText("");
				if (uri != null)
					Toast.makeText(this, uri.toString(), Toast.LENGTH_SHORT).show();
				break;
			case R.id.retrieve:
				String selection = null;
				String[] selectionArgs = {""};

				if (TextUtils.isEmpty(mName.getText().toString()) && TextUtils.isEmpty(mId.getText().toString())) {
					selection = null;
					selectionArgs[0] = "";
				} else if (!TextUtils.isEmpty(mName.getText().toString())) {
					selection = MyContentProvider.NAME + "=?";
					selectionArgs[0] = mName.getText().toString();
				} else if (!TextUtils.isEmpty(mId.getText().toString())) {
					selection = MyContentProvider._ID + "=?";
					selectionArgs[0] = mId.getText().toString();
				}

				mName.setText("");
				mId.setText("");

				Cursor c = getContentResolver().query(CONTENT_URI, null, selection, selectionArgs, "name");
				if (c != null) {
					if (c.moveToFirst()) {
						do {
							Toast.makeText(this,
									c.getString(c.getColumnIndex(MyContentProvider._ID)) + ", " +
											c.getString(c.getColumnIndex(NAME)) + ", " +
											c.getString(c.getColumnIndex(MyContentProvider.GRADE)), Toast.LENGTH_SHORT).show();
						} while (c.moveToNext());
					}
					c.close();
				}
				break;
		}
	}
}