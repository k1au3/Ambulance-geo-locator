package com.dmrot.dominiccue.amtechtimesapp.apiservices;

/**
 * Created by dominiccue on 10/13/2017.
 */

import com.dmrot.dominiccue.amtechtimesapp.model.InsertTaskResponseModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface ApiService {
    @FormUrlEncoded
    @POST("gps/gps/task/InsertTask.php")
    Call<InsertTaskResponseModel> insertTask(@Field("email") String email, @Field("name") String name,@Field("idno") String idno,@Field("phone") String phone,@Field("ambulance") String ambulance,@Field("loca") String loca, @Field("patient") String patient, @Field("location") String location);
}
