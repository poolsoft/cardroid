package de.jlab.cardroid.storage;

import android.provider.BaseColumns;

public final class CarDroidDataContract {
    private CarDroidDataContract() {}

    public static abstract class RemoteControl implements BaseColumns {
        public static final String TABLE_NAME = "remote_control";
        public static final String COLUMN_NAME_NAME = "name";

        public static final String SQL_CREATE =
                "CREATE TABLE " + TABLE_NAME +
                    " (" +
                        _ID + " INTEGER PRIMARY KEY," +
                        COLUMN_NAME_NAME + " TEXT" +
                    " )";
        public static final String SQL_DELETE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class RemoteButton implements BaseColumns {
        public static final String TABLE_NAME = "remote_button";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_REMOTE_ID = "remote_id";

        public static final String SQL_CREATE =
                "CREATE TABLE " + TABLE_NAME +
                    " (" +
                        _ID + " INTEGER PRIMARY KEY," +
                        COLUMN_NAME_NAME + " TEXT," +
                        COLUMN_NAME_REMOTE_ID + " INTEGER," +
                        " FOREIGN KEY (" + COLUMN_NAME_REMOTE_ID + ") REFERENCES " + RemoteControl.TABLE_NAME + "(" + RemoteControl._ID + ") ON DELETE CASCADE" +
                    " )";
        public static final String SQL_DELETE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
