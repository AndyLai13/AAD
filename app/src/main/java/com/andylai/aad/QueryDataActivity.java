package com.andylai.aad;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import static com.andylai.aad.MyContentProvider.CONTENT_URI;
import static com.andylai.aad.MyContentProvider.GRADE;
import static com.andylai.aad.MyContentProvider.NAME;
import static com.andylai.aad.MyContentProvider._ID;

public class QueryDataActivity extends AppCompatActivity implements View.OnClickListener {


	EditText mName;
	EditText mGrade;
	EditText mId;
	Button mBtnAdd;
	Button mBtnRetrieve;
	Button mBtnRefresh;
	ListView listView;
	SimpleCursorAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_query_data);

		mName = findViewById(R.id.name);
		mGrade = findViewById(R.id.grade);
		mId = findViewById(R.id.identifier);
		listView = findViewById(R.id.listView);
		mBtnAdd = findViewById(R.id.add);
		mBtnRetrieve = findViewById(R.id.retrieve);
		mBtnRefresh = findViewById(R.id.refresh);

		mAdapter = new SimpleCursorAdapter(this, R.layout.layout_list_view, null,
				new String[]{_ID, NAME, GRADE},
				new int[]{R.id.id_number, R.id.name, R.id.grade}, 0);
		listView.setAdapter(mAdapter);

		mBtnAdd.setOnClickListener(this);
		mBtnRetrieve.setOnClickListener(this);
		mBtnRefresh.setOnClickListener(this);

		LoaderManager.getInstance(this).initLoader(0, null, mLoaderCallback);
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
			case R.id.refresh:
				break;
		}
	}

	LoaderManager.LoaderCallbacks<Cursor> mLoaderCallback = new LoaderManager.LoaderCallbacks<Cursor>() {
		@NonNull
		@Override
		public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
			return new CursorLoader(QueryDataActivity.this, CONTENT_URI,
					null, null, null, null);
		}

		@Override
		public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
			mAdapter.swapCursor(data);
		}

		@Override
		public void onLoaderReset(@NonNull Loader<Cursor> loader) {
			mAdapter.swapCursor(null);
		}
	};
}
