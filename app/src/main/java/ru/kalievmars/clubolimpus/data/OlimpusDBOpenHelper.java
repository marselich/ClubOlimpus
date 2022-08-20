package ru.kalievmars.clubolimpus.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import ru.kalievmars.clubolimpus.data.ClubOlimpusContract.MemberEntry;
import ru.kalievmars.clubolimpus.data.ClubOlimpusContract;

public class OlimpusDBOpenHelper extends SQLiteOpenHelper {
    public OlimpusDBOpenHelper(@Nullable Context context) {
        super(context, ClubOlimpusContract.DATABASE_NAME, null, ClubOlimpusContract.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + MemberEntry.TABLE_NAME + " (" +
                MemberEntry.KEY_ID + " INTEGER PRIMARY KEY, " +
                MemberEntry.KEY_FIRSTNAME + " TEXT, " +
                MemberEntry.KEY_LASTNAME + " TEXT, " +
                MemberEntry.KEY_GENDER + " INTEGER NOT NULL, " +
                MemberEntry.KEY_GROUP + " TEXT" +
                ")";

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + MemberEntry.TABLE_NAME);
        onCreate(db);
    }
}
