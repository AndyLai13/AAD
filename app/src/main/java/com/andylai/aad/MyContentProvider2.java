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

import org.w3c.dom.Text;

import java.util.Map;

public class MyContentProvider2 extends ContentProvider {


	static final String AUTHORITY = "com.andylai.aad";
	static final String TABLE_NAME = "grade";

	static final String URL = "content://" + AUTHORITY + "/" + TABLE_NAME;
	static final Uri CONTENT_URI = Uri.parse(URL);

	static final String DATABASE_NAME = "grade.db";
	static final int DATABASE_VERSION = 1;

	static final String _ID = "_id";
	static final String NAME = "name";
	static final String MATH = "math";

	static final String CREATE_TABLE = " CREATE TABLE " +
			TABLE_NAME + '(' +
			_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			NAME + " TEXT NOT NULL," +
			MATH + " TEXT NOT NULL" +
			");";

	static final String DROP_TABLE = " DROP TABLE IF EXISTS " + TABLE_NAME;

	private SQLiteDatabase db;

	static final int STUDENTS = 1;
	static final int STUDENTS_ID = 2;

	static Map<String, String> PROJECTION;

	static final UriMatcher uriMatcher;

	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(AUTHORITY, TABLE_NAME, STUDENTS);
		uriMatcher.addURI(AUTHORITY, TABLE_NAME + "/#", STUDENTS_ID);
	}

	public static class DatabaseHelper extends SQLiteOpenHelper {

		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_TABLE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL(DROP_TABLE);
			onCreate(db);
		}
	}

	@Override
	public boolean onCreate() {
		DatabaseHelper dbHelper = new DatabaseHelper(getContext());
		db = dbHelper.getWritableDatabase();
		return db != null;
	}

	@Override
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)) {
			case STUDENTS:
				return "vnd.android.cursor.dir/vnd.andylai.grade";
			case STUDENTS_ID:
				return "vnd.android.cursor.item/vnd.andylai.grade";
			default:
				throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
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
	public Cursor query(Uri uri, String[] projection, String selection,
						String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(TABLE_NAME);
		switch (uriMatcher.match(uri)) {
			case STUDENTS:
				qb.setProjectionMap(PROJECTION);
				break;
			case STUDENTS_ID:
				String id = uri.getPathSegments().get(1);
				qb.appendWhere(_ID + " = " + id);
				break;
			default:
				throw new IllegalArgumentException("Unknown uri " + uri);
		}

		if (sortOrder != null && !sortOrder.equals("")) {
			sortOrder = NAME;
		}

		Cursor c = qb.query(db, projection, selection, selectionArgs, "", "", sortOrder);
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;

	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
					  String[] selectionArgs) {
		int count;
		switch (uriMatcher.match(uri)) {
			case STUDENTS:
				count = db.update(TABLE_NAME, values, selection, selectionArgs);
				break;
			case STUDENTS_ID:
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

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int count;
		switch (uriMatcher.match(uri)) {
			case STUDENTS:
				count = db.delete(TABLE_NAME, selection, selectionArgs);
				break;
			case STUDENTS_ID:
				String id = uri.getPathSegments().get(1);
				count = db.delete(TABLE_NAME, _ID + " = " + id +
						(!TextUtils.isEmpty(selection) ? " AND (" + selection + ")" : ""), selectionArgs);
				break;
			default:
				throw new IllegalArgumentException("Unknown uri " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}
}
