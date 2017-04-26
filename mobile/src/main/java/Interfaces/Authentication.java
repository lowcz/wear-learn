package Interfaces;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import pojo.RegisterDataBody;
/**
 * Created by Michał on 4/19/2017.
 */

public interface Authentication {
    @POST("user/register")
    Call<ResponseBody> postData(@Body RegisterDataBody data);
    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody> postData(@Field("username") String password, @Field("password") String username);
}
