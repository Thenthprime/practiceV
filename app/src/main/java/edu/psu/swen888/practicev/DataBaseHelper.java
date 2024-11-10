package edu.psu.swen888.practicev;

import static java.security.AccessController.getContext;
import static java.sql.Types.INTEGER;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Address;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String EVENT_TABLE = "EVENT_TABLE";
    public static final String COLUMN_EVENT_DATE = "EVENT_DATE";
    public static final String COLUMN_EVENT_LOCATION = "EVENT_LOCATION";
    public static final String COLUMN_ID = "ID";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "event db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + EVENT_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_EVENT_DATE + " TEXT, " + COLUMN_EVENT_LOCATION + " ADDRESS)";
        db.execSQL(createTableStatement);
    }

    //called if database version changes if design is changed
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne(Event event){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_EVENT_DATE, event.getName());
        cv.put(COLUMN_EVENT_LOCATION, event.getAddress().toString());

        long insert = db.insert(EVENT_TABLE, null , cv);
        if(insert == -1){
            return false;
        }
        else {
            return true;
        }
    }

    public ArrayList<Event> getAllEvents() {
        ArrayList<Event> returnList = new ArrayList();
        //get data from database
        String queryString = "SELECT * FROM " + EVENT_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            //loop through results and create a new customer object for each row
            do {
                int eventID = cursor.getInt(0);
                String eventName = cursor.getString(1);
                String eventAddress = cursor.getString(2);


                Event newEvent = new Event(eventID, eventName, eventAddress);
                returnList.add(newEvent);
            }
            while (cursor.moveToNext());

        }
        else {
            //failure do nothing
        }
        cursor.close();
        db.close();
        return returnList;
    }
}
