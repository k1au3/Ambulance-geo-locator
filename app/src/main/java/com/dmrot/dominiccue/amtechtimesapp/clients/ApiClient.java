package com.dmrot.dominiccue.amtechtimesapp.clients;

/**
 * Created by dominiccue on 10/13/2017.
 */

import com.dmrot.dominiccue.amtechtimesapp.utils.LoggingInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.dmrot.dominiccue.amtechtimesapp.Constants.BASE_URL;


public class ApiClient {

    public static final String URL   = BASE_URL;
    public static Retrofit RETROFIT = null;

    public static Retrofit getClient(){
        if(RETROFIT==null){



            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new LoggingInterceptor())
                    .build();


            RETROFIT = new Retrofit.Builder()
                    .baseUrl(URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return RETROFIT;
    }





}
