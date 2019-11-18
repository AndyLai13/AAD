package com.andylai.aad;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.net.URI;
import java.util.HashMap;

public class MyContentProvider2 extends ContentProvider {

	final static String AUTHORITY = "com.andylai.aad";
	final static String URL = "content://" + AUTHORITY + "/students";
	final static Uri CONTENT_URI = Uri.parse(URL);

	final static String _ID = "_id";
	final static String NAME = "name";
	final static String GRADE = "grade";

	final static int STUDENTS = 1;
	final static int STUDENT_ID = 2;

	static final UriMatcher uriMatcher;

	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(AUTHORITY, "students", STUDENTS);
		uriMatcher.addURI(AUTHORITY, "students/#", STUDENT_ID);
	}

	private SQLiteDatabase db;
	final static String DATABASE_NAME = "college.db";
	final static String TABLE_NAME = "students";
	final static int DB_VERSION = 1;
	final static String CREATE_DB_TABLE =
			" CREATE TABLE " + TABLE_NAME + " ("
					+ _ID + "_INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ NAME + " TEXT NOT NULL, "
					+ GRADE + " TEXT NOT NULL" + ");";

	final static String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

	private static HashMap<String, String> STUDENTS_PROJECTION_MAP;

	private static class DatabaseHelper extends SQLiteOpenHelper {

		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DB_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_DB_TABLE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL(DROP_TABLE);
			onCreate(db);
		}
	}

	@Override
	public boolean onCreate() {
		DatabaseHelper helper = new DatabaseHelper(getContext());
		db = helper.getWritableDatabase();
		return db != null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		long rowID = db.insert(TABLE_NAME, "", values);
		if (rowID > 0) {
			Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
			getContext().getContentResolver().notifyChange(_uri, null);
			return _uri;
		}

		throw new SQLException("Failed to add record into " + uri);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int count = 0;
		switch (uriMatcher.match(uri)) {
			case STUDENTS:
				count = db.delete(TABLE_NAME, selection, selectionArgs);
				break;
			case STUDENT_ID:
				String id = uri.getPathSegments().get(1);
				count = db.delete(TABLE_NAME, _ID + " = " + id +
						(!TextUtils.isEmpty(selection) ? " AND (" + selection + ")" : ""), selectionArgs);
				break;
			default:
				throw new IllegalArgumentException("Unknown URI " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)) {
			case STUDENTS:
				return "vnd.android.cursor.item/vnd.andylai.students";
			case STUDENT_ID:
				return "vnd.android.cursor.dir/vnd.andylai.students";
			default:
				throw new IllegalArgumentException("Unsupported URI " + uri);
		}
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
						String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
		builder.setTables(TABLE_NAME);

		switch (uriMatcher.match(uri)) {
			case STUDENTS:
				builder.setProjectionMap(STUDENTS_PROJECTION_MAP);
				break;
			case STUDENT_ID:
				String id = uri.getPathSegments().get(1);
				builder.appendWhere(_ID + "=" + id);
				break;
			default:
				throw new IllegalArgumentException("Unknown URI " + uri);
		}

		if (sortOrder == null || sortOrder.equals("")) {
			sortOrder = NAME;
		}
		Cursor c = builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
					  String[] selectionArgs) {
		int count = 0;
		switch (uriMatcher.match(uri)) {
			case STUDENTS:
				count = db.update(TABLE_NAME, values, selection, selectionArgs);
				break;
			case STUDENT_ID:
				String id = uri.getPathSegments().get(1);
				count = db.update(TABLE_NAME, values, _ID + " = " + id +
						(!TextUtils.isEmpty(selection) ? " AND (" + selection + ")" : ""), selectionArgs);
				break;
			default:
				throw new IllegalArgumentException("Unknown uri " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}
}
