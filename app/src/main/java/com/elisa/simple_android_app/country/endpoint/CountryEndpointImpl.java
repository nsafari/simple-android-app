package com.elisa.simple_android_app.country.endpoint;

import com.elisa.library.GsonUtil;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nSafari on 5/11/2017.
 */

public class CountryEndpointImpl implements CountryEndpoint {
    public static final String API_URL = "https://api.whichapp.com/v1/";

    private static CountryEndpoint mCountryEndpoint;

    static {
        mCountryEndpoint = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonUtil.instance()))
                .client(new OkHttpClient.Builder()
                        .connectTimeout(2, TimeUnit.SECONDS)
                        .readTimeout(10, TimeUnit.SECONDS)
                        .writeTimeout(10, TimeUnit.SECONDS)
                        .build()
                )
                .build()
                .create(CountryEndpoint.class);
    }

    private CountryEndpointImpl() {

    }

    public static CountryEndpoint getInstance() {
        return mCountryEndpoint;
    }

    public static <T> List<T> convertToRestResponse(String json, Type listType) {
        return new Gson().fromJson(json, listType);
    }

    @Override
    public Call<ResponseBody> getCountries() {
        return mCountryEndpoint.getCountries();
    }
}
