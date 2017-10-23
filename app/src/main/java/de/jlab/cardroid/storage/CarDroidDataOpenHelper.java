package de.jlab.cardroid.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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

    public Cursor listRemoteControls() {
        final SQLiteDatabase db = this.getReadableDatabase();
        return db.query(CarDroidDataContract.RemoteControl.TABLE_NAME, new String[]{CarDroidDataContract.RemoteControl._ID, CarDroidDataContract.RemoteControl.COLUMN_NAME_NAME}, null, new String[0], null, null, CarDroidDataContract.RemoteControl.COLUMN_NAME_NAME);
    }

    public boolean remoteControlExists(long serialId) {
        final SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(CarDroidDataContract.RemoteControl.TABLE_NAME, new String[]{CarDroidDataContract.RemoteControl._ID}, null, new String[0], null, null, CarDroidDataContract.RemoteControl.COLUMN_NAME_NAME);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    public void createOrUpdateRemoteControl(String name, long id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CarDroidDataContract.RemoteControl.COLUMN_NAME_NAME, name != null ? name : Long.toString(id));

        if (remoteControlExists(id)) {
            db.update(CarDroidDataContract.RemoteControl.TABLE_NAME, values, CarDroidDataContract.RemoteControl._ID + "=" + id, null);
        }
        else {
            values.put(CarDroidDataContract.RemoteControl._ID, id);
            db.insertOrThrow(CarDroidDataContract.RemoteControl.TABLE_NAME, null, values);
        }

        db.close();
    }

    public void deleteRemoteControl(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CarDroidDataContract.RemoteControl.TABLE_NAME, CarDroidDataContract.RemoteControl._ID + "=" + id, null);
        db.close();
    }
}
