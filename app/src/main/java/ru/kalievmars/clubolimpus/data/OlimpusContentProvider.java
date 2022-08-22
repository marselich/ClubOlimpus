package ru.kalievmars.clubolimpus.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.widget.Toast;

import ru.kalievmars.clubolimpus.data.ClubOlimpusContract.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class OlimpusContentProvider extends ContentProvider {

    private static final int MEMBERS_CODE = 111;
    private static final int MEMBER_ID_CODE = 222;

    OlimpusDBOpenHelper olimpusDBOpenHelper;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(ClubOlimpusContract.AUTHORITY, ClubOlimpusContract.PATH_MEMBERS, MEMBERS_CODE);
        uriMatcher.addURI(ClubOlimpusContract.AUTHORITY, ClubOlimpusContract.PATH_MEMBERS + "/#", MEMBER_ID_CODE);
    }

    @Override
    public boolean onCreate() {
        olimpusDBOpenHelper = new OlimpusDBOpenHelper(getContext());

        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = olimpusDBOpenHelper.getReadableDatabase();

        Cursor cursor;

        int match = uriMatcher.match(uri);

        switch (match) {
            case MEMBERS_CODE:
                cursor = db.query(MemberEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case MEMBER_ID_CODE:
                cursor = db.query(MemberEntry.TABLE_NAME, projection, MemberEntry.KEY_ID + "=?",
                        new String[]{String.valueOf(ContentUris.parseId(uri))}, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Can't query URI " + uri);
        }



        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        assert values != null;
        validateColumns(values);

        SQLiteDatabase db = olimpusDBOpenHelper.getWritableDatabase();

        int match = uriMatcher.match(uri);

        switch (match) {
            case MEMBERS_CODE:
                long id = db.insert(MemberEntry.TABLE_NAME, null, values);

                return ContentUris.withAppendedId(MemberEntry.CONTENT_URI, id);
            default:
                throw new IllegalArgumentException("Can't insert URI " + uri);

        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = olimpusDBOpenHelper.getWritableDatabase();

        int match = uriMatcher.match(uri);

        switch (match) {
            case MEMBERS_CODE:
                return db.delete(MemberEntry.TABLE_NAME, selection, selectionArgs);
            case MEMBER_ID_CODE:
                selection = MemberEntry.KEY_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return db.delete(MemberEntry.TABLE_NAME, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Can't delete URI " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {

        assert values != null;
        validateColumns(values);

        SQLiteDatabase db = olimpusDBOpenHelper.getWritableDatabase();

        int match = uriMatcher.match(uri);

        switch (match) {
            case MEMBERS_CODE:
                return db.update(MemberEntry.TABLE_NAME, values, selection, selectionArgs);
            case MEMBER_ID_CODE:
                selection = MemberEntry.KEY_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return db.update(MemberEntry.TABLE_NAME, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Can't update URI " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        int match = uriMatcher.match(uri);

        switch (match) {
            case MEMBERS_CODE:
                return MemberEntry.TYPE_FOR_MULTIPLE_MEMBERS;
            case MEMBER_ID_CODE:
                return MemberEntry.TYPE_FOR_SINGLE_MEMBER;
            default:
                throw new IllegalArgumentException("Can't update URI " + uri);
        }
    }

    private void validateColumns(ContentValues values) {

        if(values.containsKey(MemberEntry.KEY_FIRSTNAME)) {
            String firstName = values.getAsString(MemberEntry.KEY_FIRSTNAME);
            if (firstName == null) {
                throw new IllegalArgumentException("You have to input first name");
            }
        }

        if(values.containsKey(MemberEntry.KEY_LASTNAME)) {
            String lastName = values.getAsString(MemberEntry.KEY_LASTNAME);
            if (lastName == null) {
                throw new IllegalArgumentException("You have to input last name");
            }
        }

        if(values.containsKey(MemberEntry.KEY_GENDER)) {
            Integer gender = values.getAsInteger(MemberEntry.KEY_GENDER);
            if (gender == null) {
                throw new IllegalArgumentException("You have to input correct gender");
            }
        }

        if(values.containsKey(MemberEntry.KEY_GROUP)) {
            String group = values.getAsString(MemberEntry.KEY_GROUP);
            if (group == null) {
                throw new IllegalArgumentException("You have to input group");
            }
        }
    }

}
