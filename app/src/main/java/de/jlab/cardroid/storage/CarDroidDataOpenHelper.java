package de.jlab.cardroid.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public final class CarDroidDataOpenHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "HombotControl.db";

    public CarDroidDataOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CarDroidDataContract.RemoteButton.SQL_CREATE);
        db.execSQL(CarDroidDataContract.RemoteControl.SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL(CarDroidDataContract.RemoteButton.SQL_DELETE);
            db.execSQL(CarDroidDataContract.RemoteControl.SQL_DELETE);
            onCreate(db);
        }
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    public void createRemoteButton() {

    }
}
