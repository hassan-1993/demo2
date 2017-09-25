package project.demo2.data.service;


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hassan on 9/16/2017.
 */

public class RestClient {

    private static Retrofit retrofit = null;
    private static final String baseUrl="https://examplerest.000webhostapp.com/index.php/";

    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    //in order to use observable
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    //to convert from json
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
