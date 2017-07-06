package com.example.xyz.servicecentre;





import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class DatabaseHandler extends SQLiteOpenHelper {


    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
  //  private final Context myContext;
    // Database Name
    private static final String DATABASE_NAME = "serviceManager";

    // Contacts table name
    private static final String TABLE_CONTACTS = "serviceContacts";
    private SQLiteDatabase db;
   // private DatabaseHandler dbHelper;

    // Contacts Table Columns names
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

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
       // this.myContext = context;
//        this.db = getWritableDatabase();
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_CITY + " TEXT," + KEY_STATE + " TEXT," + KEY_COUNTARY + " TEXT," + KEY_ADD + " TEXT," + KEY_CONT1 + " TEXT," + KEY_CONT2 + " TEXT," +
                KEY_EMAIL + " TEXT," + KEY_PINCODE + " TEXT," + KEY_CONTACTP + " TEXT" + ");";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);


    }


    public long createRecords(int id, String name, String city, String state, String countary, String address, String phone_number_1
            , String phone_number_2, String email, String pincode, String person) {
       db=this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, id);
        values.put(KEY_NAME, name);
        values.put(KEY_CITY, city);
        values.put(KEY_STATE, state);
        values.put(KEY_COUNTARY, countary);
        values.put(KEY_ADD, address);
        values.put(KEY_CONT1, phone_number_1);
        values.put(KEY_CONT2, phone_number_2);
        values.put(KEY_EMAIL, email);
        values.put(KEY_PINCODE, pincode);
        values.put(KEY_CONTACTP, person);

        return db.insert(TABLE_CONTACTS, null, values);


    }

    public List<String> selectRecords(String a) {
        List<String> labels = new ArrayList<String>();
        db = this.getReadableDatabase();
        //  String[] cols = new String[]{KEY_NAME};
        //   String whereClause = "name = ? ";
        //  String[] whereArgs = new String[] {
        //       "Star Communication"
        // };
        //  String orderBy = "name";
        // Cursor mCursor = db.query(TABLE_CONTACTS, cols,whereClause, whereArgs, null, null,orderBy);
        String[] args = {a};
        Log.d("harsh", "selectRecords:"+a);
        Cursor mCursor = db.rawQuery("SELECT DISTINCT city FROM serviceContacts WHERE state = ?", args);
        if (mCursor.moveToFirst()) {
            do {

                labels.add(mCursor.getString(mCursor.getColumnIndex("city")));
            }
                while (mCursor.moveToNext()) ;


        }

        return labels; // iterate to get each value.

    }
    public List<String> serviceService(String b) {
        List<String> labelss = new ArrayList<String>();
        db = this.getReadableDatabase();

        String[] args = {b};
        Log.d("harsh", "selectRecords:"+ b);
        Cursor mCursor = db.rawQuery("SELECT name FROM serviceContacts WHERE city = ? ", args);
        if (mCursor.moveToFirst()) {
            do {
                labelss.add(mCursor.getString(mCursor.getColumnIndex("name")));
            }
            while (mCursor.moveToNext()) ;
        }
        return labelss; // iterate to get each value.
    }

    public List<String> serviceName(String c) {
        List<String> labelssN = new ArrayList<String>();
        db = this.getReadableDatabase();
        String[] args = {c};
        Log.d("harsh", "selectRecords:"+ c);
        Cursor mCursor = db.rawQuery("SELECT person FROM serviceContacts WHERE name = ? ", args);
        if (mCursor.moveToFirst()) {
            do {
                labelssN.add(mCursor.getString(mCursor.getColumnIndex("person")));
            }
            while (mCursor.moveToNext()) ;
        }
        return labelssN; // iterate to get each value.

    }
    public List<String> serviceA(String d) {
        List<String> labelssA = new ArrayList<String>();
        db = this.getReadableDatabase();
        String[] args = {d};
        Log.d("harsh", "selectRecords:"+ d);
        Cursor mCursor = db.rawQuery("SELECT address FROM serviceContacts WHERE name = ? ", args);
        if (mCursor.moveToFirst()) {
            do {
                labelssA.add(mCursor.getString(mCursor.getColumnIndex("address")));
            }
            while (mCursor.moveToNext()) ;
        }
        return labelssA; // iterate to get each value.

    }

    public List<String> serviceE(String e) {
        List<String> labelssE = new ArrayList<String>();
        db = this.getReadableDatabase();
        String[] args = {e};
        Log.d("harsh", "selectRecords:"+ e);
        Cursor mCursor = db.rawQuery("SELECT email FROM serviceContacts WHERE name = ? ", args);
        if (mCursor.moveToFirst()) {
            do {
                labelssE.add(mCursor.getString(mCursor.getColumnIndex("email")));
            }
            while (mCursor.moveToNext()) ;
        }
        return labelssE; // iterate to get each value.

    }

    public List<String> serviceNo(String f) {
        List<String> labelssNo = new ArrayList<String>();
        db = this.getReadableDatabase();
        String[] args = {f};
        Log.d("harsh", "selectRecords:"+ f);
        Cursor mCursor = db.rawQuery("SELECT phone_number_1,phone_number_2 FROM serviceContacts WHERE name = ? ", args);
        if (mCursor.moveToFirst()) {
            do {
                labelssNo.add(mCursor.getString(mCursor.getColumnIndex("phone_number_1")));
            }
            while (mCursor.moveToNext()) ;
        }
        return labelssNo; // iterate to get each value.

    }

    public List<String> serviceNoo(String g) {
        List<String> labelssNoo = new ArrayList<String>();
        db = this.getReadableDatabase();
        String[] args = {g};
        Log.d("harsh", "selectRecords:"+ g);
        Cursor mCursor = db.rawQuery("SELECT phone_number_2 FROM serviceContacts WHERE name = ? ", args);
        if (mCursor.moveToFirst()) {
            do {
                labelssNoo.add(mCursor.getString(mCursor.getColumnIndex("phone_number_2")));
            }
            while (mCursor.moveToNext()) ;
        }
        return labelssNoo; // iterate to get each value.

    }

    public void DatabaseClose() {
        db.close();
    }


}