package com.teamhexcode.smartmap;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by rishad on 21/2/18.
 */

public interface ApiService {


    @GET("get_alldata.php")
    Call<List<LocationPoints>> getLocationPoints(@Query("apiKey")String apiKey);

}
