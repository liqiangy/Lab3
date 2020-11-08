package com.example.lab3;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import static android.provider.Contacts.SettingsColumns.KEY;
import static java.text.Collator.PRIMARY;

public class MyOpener extends SQLiteOpenHelper {
    protected final static String DATABASE_NAME="MessageDB";
    protected final static int VERSION_NUM=2;
    protected final static String TABLE_NAME="MESSAGE";
    protected final static String COL_MSG="message";
    protected final static String COL_TYPE="type";
    protected final static String COL_ID="_id";
    private static final String SQL_CREATE_DB =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COL_TYPE + " INTEGER,"+
                    COL_MSG + " TEXT"+ ")";
    public MyOpener(Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        //CREATE NEW VERSION DATABASE
        onCreate(db);
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
    // Delete Message Details
    public void DeleteMsg(Msg m){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COL_ID+" = ?",new String[]{Long.toString(m.getId())});
        db.close();
    }

}
