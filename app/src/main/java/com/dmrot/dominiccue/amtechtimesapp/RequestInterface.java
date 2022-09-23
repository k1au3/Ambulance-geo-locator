package com.dmrot.dominiccue.amtechtimesapp;

/**
 * Created by dominiccue on 10/7/2017.
 */

import com.dmrot.dominiccue.amtechtimesapp.model.ServerRequest;
import com.dmrot.dominiccue.amtechtimesapp.model.ServerResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RequestInterface {

   // @POST("amtech_timesheet/")
    @POST("gps/gps/")
    Call<ServerResponse> operation(@Body ServerRequest request);

}
