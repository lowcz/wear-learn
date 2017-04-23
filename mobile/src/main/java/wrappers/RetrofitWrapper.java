package wrappers;

import java.net.CookieManager;
import java.net.CookiePolicy;

import okhttp3.OkHttpClient;
import okhttp3.internal.JavaNetCookieJar;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by pawel on 2017-04-23.
 */

public class RetrofitWrapper {

    private Retrofit retrofit;
    private Retrofit.Builder retrofitBuilder;
    private OkHttpClient.Builder httpClient;

    public RetrofitWrapper(String URL, Converter.Factory converterFactory){
        httpClient = new OkHttpClient.Builder();

        retrofitBuilder = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(converterFactory);
    }
    public RetrofitWrapper(String URL){
        httpClient = new OkHttpClient.Builder();

        retrofitBuilder = new Retrofit.Builder()
                .baseUrl(URL);
    }

    public RetrofitWrapper enableLogging(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);
        return this;
    }
    public RetrofitWrapper enableCookies(){

        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        httpClient.cookieJar(new JavaNetCookieJar(cookieManager));
        return this;
    }
    public RetrofitWrapper disableRedirects(){
        httpClient.followRedirects(false);
        return this;
    }
    public RetrofitWrapper build(){
        retrofit = retrofitBuilder
                .client(httpClient.build())
                .build();
        return this;
    }


    public Retrofit getRetrofit() {
        return retrofit;
    }

    public void setRetrofit(Retrofit retrofit) {
        this.retrofit = retrofit;
    }


}
