package de.deletelimits.funnymoney.service;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PostbankAPI {

    @GET("/")
    Call<JSONObject> getApiInfo();
}
