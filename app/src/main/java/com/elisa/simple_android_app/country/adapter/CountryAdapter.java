package com.elisa.simple_android_app.country.adapter;

import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.elisa.simple_android_app.R;
import com.elisa.simple_android_app.country.model.Country;
import com.elisa.simple_android_app.infra.adapter.BaseViewHolder;
import com.elisa.simple_android_app.infra.data.CountryContract.CountryEntry;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by nSafari on 11/26/2017.
 */

public class CountryAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private static final String LOG_TAG = CountryAdapter.class.getSimpleName();
    private List<Country> mCountires;

    public CountryAdapter(List<Country> countries) {
        mCountires = countries;
    }

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

    public void setCountries(List<Country> countries) {
        mCountires = countries;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding;
        int lastItemPosition = getItemCount() - 1;
        // If create header view
        if (viewType == 0) {
            binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_header, parent, false);
        } else if (viewType == lastItemPosition) {
            // create footer view
            binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_footer, parent, false);
        } else {
            // create country view
            binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_country, parent, false);
        }
        return new BaseViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        int lastItemPosition = getItemCount() - 1;
        // If this viewHolder does not have binding behaviour
        if (position == 0 || position == lastItemPosition) {
            return;
        }
        // first position used for header
        holder.bind(mCountires.get(position - 1));
    }

    @Override
    public int getItemCount() {
        if (mCountires == null) {
            return 0;
        }
        return mCountires.size() + 2;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
