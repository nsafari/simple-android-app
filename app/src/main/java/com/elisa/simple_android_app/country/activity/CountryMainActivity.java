package com.elisa.simple_android_app.country.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.elisa.simple_android_app.R;
import com.elisa.simple_android_app.country.loader.CountryLoaderTask;
import com.elisa.simple_android_app.country.model.Country;

import java.util.List;

public class CountryMainActivity extends AppCompatActivity {


    private void bindView() {
        new CountryLoaderTask(new CountryLoaderTask.CountryReceivedAware() {
            @Override
            public void OnCountryReceived(List<Country> countries) {
                if (countries == null) {
                    return;
                }
            }
        }).execute();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindView();
    }
}
