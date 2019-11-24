package com.andylai.aad;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.lang.ref.WeakReference;
import java.net.URL;

public class AsyncTaskActivity extends AppCompatActivity {

	ImageView mImageView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_async_task);
		mImageView = findViewById(R.id.whole_image);



		findViewById(R.id.download).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new DownloadTask(AsyncTaskActivity.this).execute("http://i.imgur.com/Uki7N9T.jpg");
			}
		});
	}


	static class DownloadTask extends AsyncTask<String, Integer, Bitmap> {
		private WeakReference<AsyncTaskActivity> mActivity;

		DownloadTask(AsyncTaskActivity activity) {
			mActivity = new WeakReference<>(activity);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Bitmap doInBackground(String... strings) {
			String urlString = strings[0];
			try {
				URL url = new URL(urlString);
				return BitmapFactory.decodeStream(url.openConnection().getInputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(Bitmap bitmap) {
			super.onPostExecute(bitmap);
			AsyncTaskActivity activity = mActivity.get();
			ImageView mImageView = activity.mImageView;
			mImageView.setImageBitmap(bitmap);
		}
	}
}
