package ru.kalievmars.clubolimpus.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class ClubOlimpusContract {

    public final static String DATABASE_NAME = "ClubOlimpusDB";
    public final static int VERSION = 1;

    public final static String SCHEME = "content://";
    public final static String AUTHORITY = "ru.kalievmars.clubolimpus";
    public final static String PATH_MEMBERS = "members";

    public final static Uri BASE_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY);





    public static final class MemberEntry implements BaseColumns {
        public final static String TABLE_NAME = "members";

        public final static String KEY_ID = BaseColumns._ID;
        public final static String KEY_FIRSTNAME = "firstName";
        public final static String KEY_LASTNAME = "lastName";
        public final static String KEY_GENDER = "gender";
        public final static String KEY_GROUP = "sport";

        public final static Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_MEMBERS);

        public final static String TYPE_FOR_SINGLE_MEMBER = ContentResolver.ANY_CURSOR_ITEM_TYPE + "/" + AUTHORITY + "/" + PATH_MEMBERS;
        public final static String TYPE_FOR_MULTIPLE_MEMBERS = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY + "/" + PATH_MEMBERS;
    }

     public enum Gender {
        NONE,
        MALE,
        FEMALE,
        ANOTHER
    }

}
