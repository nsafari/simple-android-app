package com.elisa.simple_android_app.country.loader;

import android.os.AsyncTask;
import android.util.Log;

import com.elisa.simple_android_app.SimpleApp;
import com.elisa.simple_android_app.country.endpoint.CountryEndpointImpl;
import com.elisa.simple_android_app.country.model.Country;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Created by nSafari on 5/21/2017.
 */

public class CountryLoaderTask extends AsyncTask<Void, Void, List<Country>> {

    public static final String LOG_TAG = CountryLoaderTask.class.getSimpleName();
    private final CountryReceivedAware mCountryReceivedAware;

    public CountryLoaderTask(CountryReceivedAware countryReceivedAware) {
        super();
        this.mCountryReceivedAware = countryReceivedAware;
    }


    @Override
    protected List<Country> doInBackground(Void... params) {
        if (SimpleApp.getContext() == null) {
            Log.e(LOG_TAG, "Request asynchronous country loader with invalid context");
            return null;
        }
        List<Country> countries = null;
        try {
            //Try to query country from local db
            countries = Country.DBProxy.queryCountries();

            //Try to get the countries from the remote endpoint
            Response<ResponseBody> countryResponse = CountryEndpointImpl.getInstance().getCountries().execute();
            if (countryResponse == null || !countryResponse.isSuccessful()) {
                Log.e(LOG_TAG, String.format("Some error happened when call getCountries endpoint. The error is %s => %s", countryResponse.raw(), countryResponse.errorBody().string()));
                return countries;
            }
            countries = CountryEndpointImpl.convertToRestResponse(countryResponse.body().string());

            // update local db
            Country.DBProxy.insertCountries(SimpleApp.getContext().getContentResolver(), countries);

            return countries;
        } catch (Exception ex) {
            Log.e(LOG_TAG, "Some error happened when try to call getTutorDarsaa endpoint.", ex);
        }
        return countries;
    }

    @Override
    protected void onPostExecute(List<Country> countries) {
        try {
            if (mCountryReceivedAware == null) {
                return;
            }
            mCountryReceivedAware.OnCountryReceived(countries);
        } catch (Exception ex) {
            Log.e(LOG_TAG, "Some error happened when try to call OnCountryReceived callback.", ex);
        }
    }


    public interface CountryReceivedAware {
        void OnCountryReceived(List<Country> countries);
    }
}
