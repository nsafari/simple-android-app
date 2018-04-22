package com.elisa.simple_android_app.infra.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by nSafari on 1/10/2017.
 */

/**
 * Database contract, tables and columns definition
 */
public class CountryContract {

    public static final String CONTENT_AUTHORITY = "com.elisa.simple_android_app";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_COUNTRY = "user";

    public static final class CountryEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_COUNTRY).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_COUNTRY;

        public static final String TABLE_NAME = "country";

        public static final String COLUMN_ISO = "iso";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PHONE = "phone";
    }
}
