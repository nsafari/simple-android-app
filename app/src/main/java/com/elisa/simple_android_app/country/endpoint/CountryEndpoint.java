package com.elisa.simple_android_app.country.endpoint;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by nSafari on 3/29/2017.
 */

public interface CountryEndpoint {

    @GET("countries")
    Call<ResponseBody> getCountries();

}
