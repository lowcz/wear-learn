package Interfaces;

import java.util.List;

import pojo.Tag;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by pawel on 2017-05-02.
 */

public interface TagList {
    //@GET("tag/user/{userID}")
    //Call<List<Tag>> getTags(@Path("userID")String userID);

    /**
     * sends GET request which fetch all Tags from API
     * @return
     */
    @GET("tag/all")
    Call<List<Tag>> getTags();
}
