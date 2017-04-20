package Interfaces;

import okhttp3.ResponseBody;
import pojo.LoginDataBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Michał on 4/19/2017.
 */

public interface Login {
    @POST("login/")
    Call<ResponseBody> postData(@Body LoginDataBody data);
}
