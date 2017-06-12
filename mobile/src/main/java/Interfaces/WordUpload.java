package Interfaces;

import java.util.List;

import okhttp3.ResponseBody;
import pojo.RegisterDataBody;
import pojo.UserWord;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Michał on 5/15/2017.
 */

public interface WordUpload {
    /**
     * sending POST request to API which adds single UserWord
     * @param data UserWord object
     * @return
     */
    @POST("userWord/add")
    Call<ResponseBody> postData(@Body UserWord data);
    /**
     * sending POST request to API which adds List of UserWords
     * @param data List of UserWords
     * @return
     */
    @POST("userWord/add-many")
    Call<ResponseBody> postDataMany(@Body List<UserWord> data);
}
