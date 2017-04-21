package Interfaces;

import okhttp3.ResponseBody;
import pojo.LoginDataBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;



/**
 * Created by Michał on 4/19/2017.
 */

public interface Login {
    //@POST("login/")
    //Call<ResponseBody> postData(@Body LoginDataBody data);
    //@Headers({
    //       "Content-Type: application/x-www-form-urlencoded"
    //})
    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody> postData(@Field("password") String password, @Field("username") String username);
    //Call<ResponseBody> postData(@Body LoginDataBody data);
}
