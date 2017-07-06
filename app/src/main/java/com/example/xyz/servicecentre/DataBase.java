package com.example.xyz.servicecentre;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;



public class DataBase {

    private DatabaseHandler dbHelper;
    private SQLiteDatabase db;

    public DataBase(Context context) {
        dbHelper = new DatabaseHandler(context);
    }
    private static final String TABLE_CONTACTS = "serviceContacts";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_CITY = "city";
    private static final String KEY_STATE = "state";
    private static final String KEY_COUNTARY = "countary";
    private static final String KEY_ADD = "address";
    private static final String KEY_CONT1 = "phone_number_1";
    private static final String KEY_CONT2 = "phone_number_2";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PINCODE = "pincode";
    private static final String KEY_CONTACTP = "person";



    public long createRecords( String name, String city, String state, String countary, String address, String phone_number_1
            , String phone_number_2, String email, String pincode, String person) {
        ContentValues values = new ContentValues();
        db = dbHelper.getWritableDatabase();
      //  db = dbHelper.getReadableDatabase();
        //values.put("KEY_ID", id);
        values.put("KEY_NAME", name);
        values.put("KEY_CITY", city);
        values.put("KEY_STATE", state);
        values.put("KEY_COUNTARY", countary);
        values.put("KEY_ADD", address);
        values.put("KEY_CONT1", phone_number_1);
        values.put("KEY_CONT2", phone_number_2);
        values.put("KEY_EMAIL", email);
        values.put("KEY_PINCODE", pincode);
        values.put("KEY_CONTACTP", person);

       return db.insert(TABLE_CONTACTS, null, values);


    }

    public Cursor selectRecords() {
        db = dbHelper.getReadableDatabase();
        String[] cols = new String[]{KEY_NAME};
        String whereClause = "KEY_NAME = ? ";
        String[] whereArgs = new String[] {
                "Star Communication"
        };
        String orderBy = "KEY_NAME";
        Cursor mCursor = db.query(TABLE_CONTACTS, cols,whereClause, whereArgs, null, null,orderBy);
        if (mCursor != null) {
            mCursor.moveToNext();
        }
       // Log.i("hi", "selectRecords: ");
        return mCursor; // iterate to get each value.

    }

    public void DatabaseClose() {
        db.close();
    }


}