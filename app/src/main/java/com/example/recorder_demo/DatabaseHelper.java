package com.example.recorder_demo;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "user_db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_FIRST_NAME = "first_name";
    private static final String COLUMN_LAST_NAME = "last_name";
    private static final String COLUMN_ACCESS_TOKEN = "access_token";
    private static final String COLUMN_REFRESH_TOKEN = "refresh_token";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_FIRST_NAME + " TEXT, " +
                COLUMN_LAST_NAME + " TEXT, " +
                COLUMN_ACCESS_TOKEN + " TEXT, " +
                COLUMN_REFRESH_TOKEN + " TEXT" +
                ")";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addUser(LoggedInUser user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_EMAIL, user.getEmail());
        contentValues.put(COLUMN_USERNAME, user.getUsername());
        contentValues.put(COLUMN_FIRST_NAME, user.getFirstName());
        contentValues.put(COLUMN_LAST_NAME, user.getLastName());
        contentValues.put(COLUMN_ACCESS_TOKEN, user.getAccessToken());
        contentValues.put(COLUMN_REFRESH_TOKEN, user.getRefreshToken());
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            Log.d("DatabaseHelper", "Error inserting user data");
        } else {
            Log.d("DatabaseHelper", "User data inserted successfully");
        }
        db.insert(TABLE_NAME, null, contentValues);
        db.close();
    }

    public LoggedInUser getUser() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
          @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL));
          @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME));
          @SuppressLint("Range") String firstName = cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME));
          @SuppressLint("Range") String lastName = cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME));
          @SuppressLint("Range") String accessToken = cursor.getString(cursor.getColumnIndex(COLUMN_ACCESS_TOKEN));
          @SuppressLint("Range") String refreshToken = cursor.getString(cursor.getColumnIndex(COLUMN_REFRESH_TOKEN));

            cursor.close();
            db.close();

           // return new User(email, username, firstName, lastName, accessToken, refreshToken);
        }

        return null;
    }
}

