package ru.usharik.smsredirector.rest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ExportRest {
    @POST("/")
    Call<ResponseBody> postJson(@Body SmsDto body);
}
