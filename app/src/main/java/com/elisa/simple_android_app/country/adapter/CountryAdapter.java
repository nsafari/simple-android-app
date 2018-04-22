package com.elisa.simple_android_app.country.adapter;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.util.Log;

import com.elisa.simple_android_app.country.model.Country;
import com.elisa.simple_android_app.infra.data.CountryContract.CountryEntry;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by nSafari on 11/26/2017.
 */

public class CountryAdapter  {
    private static final String LOG_TAG = CountryAdapter.class.getSimpleName();

    // These indices are tied to COUNTRY_COLUMNS.  If COUNTRY_COLUMNS changes, these
    // must change.
    public static final int COL_COUNTRY_ID = 0;
    public static final int COL_COUNTRY_ISO = 1;
    public static final int COL_COUNTRY_NAME = 2;
    public static final int COL_COUNTRY_PHONE = 3;

    /*
     * Read the country, it's tutor and it's steps from the cursor
     */
    public static List<Country> getCountries(Cursor cursor) {
        try {
            if (cursor == null || cursor.getCount() == 0) {
                return null;
            }

            List<Country> countries = new LinkedList<>();
            cursor.moveToFirst();
            do {
                Country country = getCountry(cursor);
                countries.add(country);
            } while (cursor.moveToNext());
            return countries;
        } catch (Exception e) {
            Log.e(LOG_TAG, "Some error happened when trying to fetch darsaas from cursor", e);
        }
        return null;
    }

    public static Country getCountry(Cursor cursor) {
        if (cursor == null || cursor.getCount() == 0) {
            return null;
        }

        if (cursor.isBeforeFirst()) {
            cursor.moveToFirst();
        }

        /*
         * Create new Country from it's entry
         */
        Country country = new Country();

        country.setId(cursor.getLong(COL_COUNTRY_ID));
        country.setIso(cursor.getString(COL_COUNTRY_ISO));
        country.setName(cursor.getString(COL_COUNTRY_NAME));
        country.setPhone(cursor.getInt(COL_COUNTRY_PHONE));
        return country;
    }


    @NonNull
    public static String[] getCountryProjection() {
        return new String[]{
                CountryEntry.TABLE_NAME + "." + CountryEntry._ID,
                CountryEntry.TABLE_NAME + "." + CountryEntry.COLUMN_ISO,
                CountryEntry.TABLE_NAME + "." + CountryEntry.COLUMN_NAME,
                CountryEntry.TABLE_NAME + "." + CountryEntry.COLUMN_PHONE,
        };
    }
}
