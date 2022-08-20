package ru.kalievmars.clubolimpus.data;

import android.provider.BaseColumns;

public final class ClubOlimpusContract {

    public final static String DATABASE_NAME = "ClubOlimpusDB";
    public final static int VERSION = 1;



    public static final class MemberEntry implements BaseColumns {


        public final static String TABLE_NAME = "Members";

        public final static String KEY_ID = BaseColumns._ID;
        public final static String KEY_FIRSTNAME = "firstName";
        public final static String KEY_LASTNAME = "lastName";
        public final static String KEY_GENDER = "gender";
        public final static String KEY_GROUP = "sport";

    }

     public static enum Gender {
        NONE,
        MALE,
        FEMALE,
        ANOTHER
    }

}
