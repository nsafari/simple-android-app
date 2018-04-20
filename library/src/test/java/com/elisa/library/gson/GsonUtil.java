package com.elisa.library.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by nSafari on 6/29/2017.
 */

public class GsonUtil {

    public static Gson instance() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }
}


