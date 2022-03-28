package com.example.contact_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHandler extends SQLiteOpenHelper {
    private static final String DB_NAME = "contactDb";

    // below int is our database version
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "contact";

    private static final String ID_COL = "id";

    private static final String NAME_COL = "name";

    private static final String PHONE_COL = "phone";

    private static final String EMAIL_COL = "email";


    public DBHandler(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + PHONE_COL + " TEXT,"
                + EMAIL_COL + " TEXT)";

        sqLiteDatabase.execSQL(query);
    }
    // below is the method for updating our courses
    public void updateCourse(String originalName, String name, String phone,
                             String email) {

        // calling a method to get writable database.
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(NAME_COL, name);
        values.put(PHONE_COL, phone);
        values.put(EMAIL_COL, email);

        // on below line we are calling a update method to update our database and passing our values.
        // and we are comparing it with name of our course which is stored in original name variable.
        db.update(TABLE_NAME, values, "name=?", new String[]{originalName});
        db.close();
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
