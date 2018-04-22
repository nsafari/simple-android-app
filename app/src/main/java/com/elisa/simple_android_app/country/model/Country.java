package com.elisa.simple_android_app.country.model;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.databinding.BaseObservable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;

import com.elisa.simple_android_app.country.adapter.CountryAdapter;
import com.elisa.simple_android_app.infra.data.CountryContract.CountryEntry;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

import static com.elisa.simple_android_app.SimpleApp.getContext;

public class Country extends BaseObservable implements Parcelable {

    private long id;
    @Expose
    private String iso;
    @Expose
    private String name;
    @Expose
    private int phone;

    public Country(){

    }

    protected Country(Parcel in) {
        setId(in.readLong());
        setIso(in.readString());
        setName(in.readString());
        setPhone(in.readInt());
    }

    public static final Creator<Country> CREATOR = new Creator<Country>() {
        @Override
        public Country createFromParcel(Parcel in) {
            return new Country(in);
        }

        @Override
        public Country[] newArray(int size) {
            return new Country[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(getId());
        dest.writeString(getIso());
        dest.writeString(getName());
        dest.writeInt(getPhone());
    }

    /**
     * db manipulation handler
     */
    public static class DBProxy {

        private static final String LOG_TAG = DBProxy.class.getCanonicalName();

        public DBProxy() {
        }

        @NonNull
        public static ContentValues getCountryContentValues(Country country) {
            ContentValues values = new ContentValues();
            values.put(CountryEntry.COLUMN_ISO, country.getIso());
            values.put(CountryEntry.COLUMN_NAME, country.getName());
            values.put(CountryEntry.COLUMN_PHONE, country.getPhone());
            return values;
        }

        public static long deleteCountries(ContentResolver contentResolver) {
            int deletedCount = -1;
            try {
                // delete countries from local db
                deletedCount = contentResolver.delete(CountryEntry.CONTENT_URI, null, null);
            }
            catch (Exception ex){
                Log.e(LOG_TAG, "Some error happened when trying to delete countries", ex);
            }
            return deletedCount;
        }

        public static long insertCountries(ContentResolver contentResolver, List<Country> countries) {
            int insertedCount = -1;
            try {
                if (countries == null) {
                    return -1;
                }

                // Create country content values
                List<ContentValues> contentValuesList = new ArrayList<>();
                for (int i = 0; i < countries.size(); i++) {
                    contentValuesList.add(getCountryContentValues(countries.get(i)));
                }

                // Bulk Insert to local db
                insertedCount = contentResolver.bulkInsert(CountryEntry.CONTENT_URI, contentValuesList.toArray(new ContentValues[0]));
            }
            catch (Exception ex){
                Log.e(LOG_TAG, "Some error happened when trying to insert countries", ex);
            }
            return insertedCount;
        }

        public static List<Country> queryCountries() {
            List<Country> countries = null;
            try {
                // Query local db
                Cursor cursor = getContext().getContentResolver()
                        .query(
                                CountryEntry.CONTENT_URI,
                                CountryAdapter.getCountryProjection(),
                                null,
                                null,
                                null
                        );

                if (cursor != null && cursor.getCount() == 1) {
                    countries = CountryAdapter.getCountries(cursor);
                }
            }
            catch (Exception ex){
                Log.e(LOG_TAG, "Some error happened when trying to query countries", ex);
            }
            return countries;
        }

    }

}
