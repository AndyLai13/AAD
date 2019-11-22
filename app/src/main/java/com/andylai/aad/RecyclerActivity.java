package com.andylai.aad;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static com.andylai.aad.MyContentProvider.CONTENT_URI;
import static com.andylai.aad.MyContentProvider.NAME;

public class RecyclerActivity extends AppCompatActivity {

	RecyclerView mRecycler;
	MyAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recycler);
		mRecycler = findViewById(R.id.recycler);
		mAdapter = new MyAdapter(getStudentInfoList());
		mRecycler.setAdapter(mAdapter);
		LinearLayoutManager manager = new LinearLayoutManager(this);
		manager.setOrientation(RecyclerView.VERTICAL);
		mRecycler.setLayoutManager(manager);
	}

	ArrayList<StudentInfo> getStudentInfoList() {
		ArrayList<StudentInfo> list = new ArrayList<>();
		list.add(new StudentInfo("","",""));
		Cursor c = getContentResolver().query(CONTENT_URI, null, null,
				null, "name");

		if (c != null) {
			if (c.moveToFirst()) {
				do {
					StudentInfo info = new StudentInfo(
							c.getString(c.getColumnIndex(MyContentProvider._ID)),
							c.getString(c.getColumnIndex(NAME)),
							c.getString(c.getColumnIndex(MyContentProvider.GRADE)));
					list.add(info);
				} while (c.moveToNext());
			}
			c.close();
		}
		list.add(new StudentInfo("","",""));

		return list;
	}

	class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
		ArrayList<StudentInfo> mStudentInfoList;

		final int VIEWTYPE_CONTENT = 0;
		final int VIEWTYPE_TOP_BOTTOM = 1;


		MyAdapter(ArrayList<StudentInfo> studentInfoList) {
			mStudentInfoList = studentInfoList;
		}

		// attach xml file to holder
		@NonNull
		@Override
		public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
			switch (viewType) {
				default:
				case VIEWTYPE_TOP_BOTTOM:
					View view1 = LayoutInflater.from(parent.getContext()).inflate(
							R.layout.layout_list_view_top_bottom, parent, false);
					return new TopBottomViewHolder(view1);
				case VIEWTYPE_CONTENT:
					View view = LayoutInflater.from(parent.getContext()).inflate(
							R.layout.layout_list_view, parent, false);
					return new ContentViewHolder(view);
			}
		}

		// just use holder
		@Override
		public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
			if (holder instanceof ContentViewHolder) {
				ContentViewHolder cvholder = (ContentViewHolder) holder;
				StudentInfo info = mStudentInfoList.get(position);
				cvholder.id.setText(info.id);
				cvholder.name.setText(info.name);
				cvholder.grade.setText(info.grade);
			} else if (holder instanceof TopBottomViewHolder) {
				TopBottomViewHolder tbholder = (TopBottomViewHolder) holder;
				tbholder.colorBar.setBackgroundColor(Color.BLUE);
			}
		}

		@Override
		public int getItemCount() {
			return mStudentInfoList.size();
		}

		@Override
		public int getItemViewType(int position) {
			Log.d("Andy", "pos = " + position);
			if (position == 0 || position == mStudentInfoList.size() - 1) {
				return VIEWTYPE_TOP_BOTTOM;
			} else {
				return VIEWTYPE_CONTENT;
			}
		}

		class ContentViewHolder extends RecyclerView.ViewHolder {

			TextView id;
			TextView name;
			TextView grade;

			public ContentViewHolder(@NonNull View itemView) {
				super(itemView);
				id = itemView.findViewById(R.id.id_number);
				name = itemView.findViewById(R.id.name);
				grade = itemView.findViewById(R.id.grade);
			}
		}

		class TopBottomViewHolder extends RecyclerView.ViewHolder {
			View colorBar;

			public TopBottomViewHolder(@NonNull View itemView) {
				super(itemView);
				colorBar = itemView.findViewById(R.id.top_bottom);
			}
		}
	}

	public class StudentInfo {
		String id;
		String name;
		String grade;

		StudentInfo(String id, String name, String grade) {
			this.id = id;
			this.name = name;
			this.grade = grade;
		}
	}
}
