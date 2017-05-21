package Interfaces;

import java.util.List;

import pojo.Tag;
import pojo.UserWord;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by pawel on 2017-05-02.
 */

public interface UserWordService {
    @GET("userWord/tag/{tagId}")
    Call<List<UserWord>> getUserWords(@Path("tagId") String tagId);
}
