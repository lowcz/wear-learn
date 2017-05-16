package wrappers;

import java.net.CookieManager;
import java.net.CookiePolicy;

import okhttp3.OkHttpClient;
import okhttp3.internal.JavaNetCookieJar;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pawel on 2017-04-23.
 */

public class RetrofitWrapper {
    private Retrofit retrofit;
    private Retrofit.Builder retrofitBuilder;
    private OkHttpClient.Builder httpClient;
    private static String URL = "http://wl-api.herokuapp.com/";
    private static RetrofitWrapper singleton = new RetrofitWrapper( URL, GsonConverterFactory.create()).enableCookies().enableLogging().build();

    private RetrofitWrapper(String URL, Converter.Factory converterFactory){
        httpClient = new OkHttpClient.Builder();

        retrofitBuilder = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(converterFactory);
    }
    private RetrofitWrapper(String URL){
        httpClient = new OkHttpClient.Builder();

        retrofitBuilder = new Retrofit.Builder()
                .baseUrl(URL);
    }

    private RetrofitWrapper enableLogging(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);
        return this;
    }
    private RetrofitWrapper enableCookies(){

        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        httpClient.cookieJar(new JavaNetCookieJar(cookieManager));
        return this;
    }
    private RetrofitWrapper disableRedirects(){
        httpClient.followRedirects(false);
        return this;
    }
    private RetrofitWrapper build(){
        retrofit = retrofitBuilder
                .client(httpClient.build())
                .build();
        return this;
    }


    public Retrofit getRetrofit() {
        return singleton.retrofit;
    }

    public void setRetrofit(Retrofit retrofit) {
        singleton.retrofit = retrofit;
    }

    public static RetrofitWrapper getSingleton() {return singleton;}

}
