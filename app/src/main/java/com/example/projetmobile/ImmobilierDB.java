package com.example.projetmobile;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ImmobilierDB {

        public static final String KEY_ROWID = "_id";
        public static final String KEY_PRIX = "prix";
        public static final String KEY_NAME = "name";
        public static final String KEY_CATEGORIE = "categorie";

        private static final String LOG_TAG = "ProductsDb";
        public static final String SQLITE_TABLE = "Product";

        private static final String DATABASE_CREATE =
                "CREATE TABLE if not exists " + SQLITE_TABLE + " (" +
                        KEY_ROWID + " integer PRIMARY KEY autoincrement," +
                        KEY_PRIX + "," +
                        KEY_NAME + "," +
                        KEY_CATEGORIE + "," +
                        " UNIQUE (" + KEY_PRIX +"));";

        public static void onCreate(SQLiteDatabase db) {
            Log.w(LOG_TAG, DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE);
        }

        public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(LOG_TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + SQLITE_TABLE);
            onCreate(db);
        }
}
