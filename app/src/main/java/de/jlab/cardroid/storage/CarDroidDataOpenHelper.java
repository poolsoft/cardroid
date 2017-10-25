package de.jlab.cardroid.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

import de.jlab.cardroid.remotecontrol.RemoteButton;

public final class CarDroidDataOpenHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Cardroid.db";

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
        Cursor cursor = db.query(CarDroidDataContract.RemoteControl.TABLE_NAME, new String[]{CarDroidDataContract.RemoteControl._ID}, CarDroidDataContract.RemoteControl._ID + "=" + serialId, new String[0], null, null, CarDroidDataContract.RemoteControl.COLUMN_NAME_NAME);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    public void createOrUpdateRemoteControl(String name, long id) {
        ContentValues values = new ContentValues();
        values.put(CarDroidDataContract.RemoteControl.COLUMN_NAME_NAME, name != null ? name : Long.toString(id));

        boolean remoteControlExists = remoteControlExists(id);

        SQLiteDatabase db = this.getWritableDatabase();
        if (remoteControlExists) {
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

    //////

    public Cursor listRemoteButtons(long remoteControlId) {
        final SQLiteDatabase db = this.getReadableDatabase();
        return db.query(CarDroidDataContract.RemoteButton.TABLE_NAME, new String[]{CarDroidDataContract.RemoteButton._ID, CarDroidDataContract.RemoteButton.COLUMN_NAME_NAME, CarDroidDataContract.RemoteButton.COLUMN_NAME_ACTION, CarDroidDataContract.RemoteButton.COLUMN_NAME_SERIAL_ID, CarDroidDataContract.RemoteButton.COLUMN_NAME_REMOTE_ID}, CarDroidDataContract.RemoteButton.COLUMN_NAME_REMOTE_ID + "=" + remoteControlId, new String[0], null, null, CarDroidDataContract.RemoteButton.COLUMN_NAME_NAME);
    }

    public boolean remoteButtonExists(long serialId, long remoteControlId) {
        final SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(CarDroidDataContract.RemoteButton.TABLE_NAME, new String[]{CarDroidDataContract.RemoteButton._ID}, CarDroidDataContract.RemoteButton.COLUMN_NAME_SERIAL_ID + "=" + serialId + " AND " + CarDroidDataContract.RemoteButton.COLUMN_NAME_REMOTE_ID + "=" + remoteControlId, new String[0], null, null, CarDroidDataContract.RemoteButton.COLUMN_NAME_NAME);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    public void createOrUpdateRemoteButton(String name, String action, long serialId, long remoteControlId) {
        ContentValues values = new ContentValues();
        values.put(CarDroidDataContract.RemoteButton.COLUMN_NAME_NAME, name != null ? name : Long.toString(serialId));
        values.put(CarDroidDataContract.RemoteButton.COLUMN_NAME_ACTION, action);

        boolean remoteButtonExists = remoteButtonExists(serialId, remoteControlId);

        SQLiteDatabase db = this.getWritableDatabase();
        if (remoteButtonExists) {
            db.update(CarDroidDataContract.RemoteButton.TABLE_NAME, values, CarDroidDataContract.RemoteButton.COLUMN_NAME_SERIAL_ID + "=" + serialId + " AND " + CarDroidDataContract.RemoteButton.COLUMN_NAME_REMOTE_ID + "=" + remoteControlId, null);
        }
        else {
            values.put(CarDroidDataContract.RemoteButton.COLUMN_NAME_SERIAL_ID, serialId);
            values.put(CarDroidDataContract.RemoteButton.COLUMN_NAME_REMOTE_ID, remoteControlId);
            db.insertOrThrow(CarDroidDataContract.RemoteButton.TABLE_NAME, null, values);
        }

        db.close();
    }

    public void deleteRemoteButton(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CarDroidDataContract.RemoteButton.TABLE_NAME, CarDroidDataContract.RemoteButton._ID + "=" + id, null);
        db.close();
    }

    public String getButtonActionIdentifer(long serialId, long remoteControlId) {
        final SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(CarDroidDataContract.RemoteButton.TABLE_NAME, new String[]{CarDroidDataContract.RemoteButton.COLUMN_NAME_ACTION}, CarDroidDataContract.RemoteButton.COLUMN_NAME_SERIAL_ID + "=" + serialId + " AND " + CarDroidDataContract.RemoteButton.COLUMN_NAME_REMOTE_ID + "=" + remoteControlId, new String[0], null, null, CarDroidDataContract.RemoteButton.COLUMN_NAME_NAME);
        String identifier = null;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            identifier = cursor.getString(cursor.getColumnIndex(CarDroidDataContract.RemoteButton.COLUMN_NAME_ACTION));
        }
        cursor.close();
        db.close();
        return identifier;
    }

    public RemoteButton getButtonData(long id) {
        final SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(CarDroidDataContract.RemoteButton.TABLE_NAME, new String[]{CarDroidDataContract.RemoteButton._ID, CarDroidDataContract.RemoteButton.COLUMN_NAME_NAME, CarDroidDataContract.RemoteButton.COLUMN_NAME_ACTION, CarDroidDataContract.RemoteButton.COLUMN_NAME_SERIAL_ID, CarDroidDataContract.RemoteButton.COLUMN_NAME_REMOTE_ID}, CarDroidDataContract.RemoteButton._ID + "=" + id, new String[0], null, null, CarDroidDataContract.RemoteButton.COLUMN_NAME_NAME);
        CursorEntityFactory<RemoteButton> entityFactory = new CursorEntityFactory<>(RemoteButton.class);
        cursor.moveToFirst();
        return entityFactory.createEntity(cursor);
    }

    public HashMap<String, String> getButtonActionProperties(long serialId, long remoteControlId) {
        return new HashMap<>(); // TODO FILL HASHMAP FROM DATABASE
    }
}
