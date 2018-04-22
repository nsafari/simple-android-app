package com.elisa.simple_android_app.country.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.elisa.simple_android_app.R;
import com.elisa.simple_android_app.country.adapter.CountryAdapter;
import com.elisa.simple_android_app.country.loader.CountryLoaderTask;
import com.elisa.simple_android_app.country.model.Country;

import java.util.List;

import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInRightAnimator;

import static com.elisa.simple_android_app.SimpleApp.getContext;

public class CountryMainActivity extends AppCompatActivity {


    private CountryAdapter mCountriesAdapter;

    private void bindView() {
        // Bind In country recyclerView
        RecyclerView countryList = findViewById(R.id.country_list);
        bindCountryList(countryList);
    }

    private void bindCountryList(RecyclerView countryList) {
        // Create new CountryAdapter if does not exists
        if (mCountriesAdapter == null) {
            mCountriesAdapter = new CountryAdapter(null);

            // Load countries
            new CountryLoaderTask(new CountryLoaderTask.CountryReceivedAware() {
                @Override
                public void OnCountryReceived(List<Country> countries) {
                    if (countries == null) {
                        return;
                    }
                    mCountriesAdapter.setCountries(countries);
                }
            }).execute();

        }

        // Bind recyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        countryList.setLayoutManager(linearLayoutManager);
        countryList.setItemAnimator(new SlideInRightAnimator());
        SlideInBottomAnimationAdapter slideInBottomAnimationAdapter = new SlideInBottomAnimationAdapter(mCountriesAdapter);
        countryList.setAdapter(slideInBottomAnimationAdapter);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindView();
    }
}
